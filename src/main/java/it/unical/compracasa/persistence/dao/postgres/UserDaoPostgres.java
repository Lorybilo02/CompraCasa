package it.unical.compracasa.persistence.dao.postgres;

import it.unical.compracasa.persistence.dao.UserDao;
import it.unical.compracasa.persistence.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoPostgres implements UserDao {
    private final Connection con;

    public UserDaoPostgres(Connection con) {
        this.con = con;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(
                    new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstname"),
                        rs.getString("secondname"),
                        rs.getBoolean("isadmin")
                    )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User find(String username) {
        try (PreparedStatement st = con.prepareStatement("SELECT * FROM users WHERE username=?")) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("firstname"),
                    rs.getString("secondname"),
                    rs.getBoolean("isadmin")
                );
            }
            throw new RuntimeException("User Not Found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean doesUsernameExist(String username) {
        try (PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?")) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(User user) {
        String query = "INSERT INTO users (username, password, firstname, secondname, isadmin) VALUES (?, ?, ?, ?, ?)";

        if (doesUsernameExist(user.getUsername())) {
            query = "UPDATE users SET password = ?, firstname = ?, secondname = ?, isadmin = ? WHERE username = ?";
        }

        try (PreparedStatement st = con.prepareStatement(query)) {
            if (query.startsWith("UPDATE")) {
                st.setString(1, user.getPassword());
                st.setString(2, user.getFirstname());
                st.setString(3, user.getSecondname());
                st.setBoolean(4, user.isIsadmin());
                st.setString(5, user.getUsername());
            } else {
                st.setString(1, user.getUsername());
                st.setString(2, user.getPassword());
                st.setString(3, user.getFirstname());
                st.setString(4, user.getSecondname());
                st.setBoolean(5, user.isIsadmin());
            }
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Errore durante il salvataggio dell'utente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public void delete(String username) {
        final String query = "DELETE FROM users WHERE username=?";
        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, username);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
