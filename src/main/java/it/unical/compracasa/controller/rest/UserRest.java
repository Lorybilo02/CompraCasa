package it.unical.compracasa.controller.rest;

import it.unical.compracasa.persistence.DBManager;
import it.unical.compracasa.persistence.dao.UserDao;
import it.unical.compracasa.persistence.model.Token;
import it.unical.compracasa.persistence.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class UserRest {
    UserDao dao = DBManager.getInstance().getUserDao();

    @PostMapping("/signin/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public Token addUser(@RequestBody User user) {
        try {
            if (!dao.doesUsernameExist(user.getUsername())) {
                boolean isSaved = dao.save(user);
                if (!isSaved) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving user");
                }
                return new Token(Base64.getEncoder().encodeToString((user.getUsername() + ":" + user.getPassword()).getBytes()), user);
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already in use");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PutMapping("/doesUserExist")
    public boolean doesUserExist(@RequestBody Map<String, String> formData) {
        String username = formData.get("username");
        return dao.doesUsernameExist(username);
    }

    @PutMapping("/saveUser")
    public void saveUser(@RequestBody User user) {
        if (dao.doesUsernameExist(user.getUsername())) {
            boolean isSaved = dao.save(user);
            if (!isSaved) {
                throw new RuntimeException("Error updating user");
            }
        } else {
            throw new RuntimeException("Username does not exist");
        }
    }

    @PutMapping("/promoteToAdmin")
    public void promoteToAdmin(@RequestBody String username) {
        if (dao.doesUsernameExist(username)) {
            User user = dao.find(username);
            user.setIsadmin(true);
            boolean isSaved = dao.save(user);
            if (!isSaved) {
                throw new RuntimeException("Error updating user");
            }
        } else {
            throw new RuntimeException("Username does not exist");
        }
    }

    @DeleteMapping("/deleteUser")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestBody String username) {
        try {
            dao.delete(username);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }

    @GetMapping("/findUser")
    public User findUser(@RequestParam String username) {
        try {
            return dao.find(username);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }

    @GetMapping("/findAll")
    public List<User> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error");
        }
    }

    @GetMapping("/checkPassword")
    public Token findUserCheckPassword(@RequestParam String username, @RequestParam String password,  HttpServletRequest req) {
        try {
            User user = dao.find(username);
            if (user != null) {
                if (!BCrypt.checkpw(password, user.getPassword())) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Wrong password");
                } else { // Generate a token if the user and password are correct
                    req.getSession().setAttribute("user", user);
                    return new Token(Base64.getEncoder().encodeToString((user.getUsername() + ":" + user.getPassword()).getBytes()), user);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication failed");
        }
    }

    @PostMapping("/isAuthenticated")
    public boolean isAuthenticated(HttpServletRequest req){
        String auth = req.getHeader("Authorization");
        if (auth != null){
            String token = auth.substring("Basic ".length());
            return getUserByToken(token) != null;
        }else{
            return false;
        }
    }

    public static User getUserByToken(String token){
        if (token != null) {
            String decoded = new String(Base64.getDecoder().decode(token.getBytes()));
            String username = decoded.split(":")[0];
            String password = decoded.split(":")[1];
            User user = DBManager.getInstance().getUserDao().find(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @PostMapping("/logout")
    public boolean logout(@RequestBody User user, HttpServletRequest req) {
        return true;
    }
    @PostMapping("/verifyToken")
    public boolean verifyToken(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Basic ")) {
            String token = auth.substring("Basic ".length());
            User user = getUserByToken(token);
            return user != null;
        } else {
            return false; // No token provided or incorrect format
        }
    }



}
