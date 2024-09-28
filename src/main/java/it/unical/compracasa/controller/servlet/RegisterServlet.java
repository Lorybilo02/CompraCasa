package it.unical.compracasa.controller.servlet;

import it.unical.compracasa.persistence.dao.UserDao;
import it.unical.compracasa.persistence.dao.postgres.UserDaoPostgres;
import it.unical.compracasa.persistence.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/api/register")
public class RegisterServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    // Database connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/wadb";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Enables CORS
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Check if the method is OPTIONS for preflight requests
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Extract data from the request
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String firstname = req.getParameter("firstname");
        String secondname = req.getParameter("secondname");

        // Data validation
        if (username == null || password == null || firstname == null || secondname == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Tutti i campi sono obbligatori.");
            return;
        }

        // Create a User object
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Password hash
        user.setFirstname(firstname);
        user.setSecondname(secondname);
        user.setIsadmin(false); // Set administrator to false by default

        // Save user to database
        try (Connection con = getConnection()) {
            UserDao userDao = new UserDaoPostgres(con);
            boolean success = userDao.save(user);

            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Registrazione completata con successo.");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Errore durante la registrazione.");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Errore del database.");
        }
    }
}
