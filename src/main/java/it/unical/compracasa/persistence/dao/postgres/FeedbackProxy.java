package it.unical.compracasa.persistence.dao.postgres;

import it.unical.compracasa.persistence.model.Feedback;
import it.unical.compracasa.persistence.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackProxy extends Feedback {
    private final Connection con;

    public FeedbackProxy(Connection con) {
        this.con = con;
    }

    @Override
    public User getUser() {
        User user = super.getUser();
        if (user == null) {
            final String sql = "SELECT u.* FROM Feedback f, Users u WHERE f.property_id=? AND f.user_username = u.username;";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, getProperty().getId());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstname"),
                        rs.getString("secondname"),
                        rs.getBoolean("isadmin")
                    );
                }
                super.setUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }
}
