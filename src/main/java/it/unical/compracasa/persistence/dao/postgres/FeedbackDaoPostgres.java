package it.unical.compracasa.persistence.dao.postgres;

import it.unical.compracasa.persistence.DBManager;
import it.unical.compracasa.persistence.dao.FeedbackDao;
import it.unical.compracasa.persistence.dao.PropertyDao;
import it.unical.compracasa.persistence.dao.UserDao;
import it.unical.compracasa.persistence.model.Feedback;
import it.unical.compracasa.persistence.model.Property;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDaoPostgres implements FeedbackDao {

    Connection con;
    PropertyDao propertyDao;
    UserDao userDao;

    public FeedbackDaoPostgres(Connection con){
        this.con = con;
        this.propertyDao = DBManager.getInstance().getPropertyDao();
        this.userDao = DBManager.getInstance().getUserDao();
    }

    @Override
    public List<Feedback> findByProperty(Property property){
        List<Feedback> result = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement("SELECT * FROM Feedback WHERE property_id = ?")) {
            st.setInt(1, property.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Feedback feedback = new FeedbackProxy(con);
                //feedback.setId(rs.getInt("id"));
                feedback.setProperty(property);
                feedback.setContent(rs.getString("content"));
                feedback.setStars(rs.getInt("stars"));
                result.add(feedback);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int findStarsOfProperty(Property property){
        try (PreparedStatement st = con.prepareStatement("SELECT stars FROM Feedback WHERE property_id = ?")) {
            st.setInt(1, property.getId());
            ResultSet rs = st.executeQuery();
            int sumStars = 0, numStars = 0;
            while (rs.next()){
                sumStars += rs.getInt("stars");
                ++numStars;
            }
            if (numStars > 0)
                return sumStars / numStars;
            else return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean doesFeedbackExist(String user_username, int property_id){
        try (PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM Feedback WHERE user_username = ? AND property_id = ?")) {
            st.setString(1, user_username);
            st.setInt(2, property_id);
            ResultSet rs = st.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public boolean save(Feedback feedback){
        String user_username = feedback.getUsername();
        int property_id = feedback.getPropertyId();
        if (!doesFeedbackExist(user_username, property_id)) {
            try(PreparedStatement st = con.prepareStatement("INSERT INTO Feedback (user_username, property_id, content, stars) VALUES (?, ?, ?, ?)")) {
                st.setString(1, user_username);
                st.setInt(2, property_id);
                st.setString(3, feedback.getContent());
                st.setInt(4, feedback.getStars());
                st.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try(PreparedStatement st = con.prepareStatement("UPDATE Feedback SET content=?, stars=? WHERE user_username = ? AND property_id = ?")) {
                st.setString(1, feedback.getContent());
                st.setInt(2, feedback.getStars());
                st.setString(3, user_username);
                st.setInt(4, property_id);
                st.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(Feedback feedback) {
        final String query = "DELETE FROM Feedback WHERE user_username = ? AND property_id = ?";
        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, feedback.getUser().getUsername());
            st.setInt(2, feedback.getProperty().getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
