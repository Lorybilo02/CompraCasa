package it.unical.compracasa.persistence.dao.postgres;

import it.unical.compracasa.persistence.dao.PropertyDao;
import it.unical.compracasa.persistence.dao.PurchaseDao;
import it.unical.compracasa.persistence.dao.UserDao;
import it.unical.compracasa.persistence.model.Purchase;
import it.unical.compracasa.persistence.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDaoPostgres implements PurchaseDao {
    Connection con;
    PropertyDao propertyDao;
    UserDao userDao;

    public PurchaseDaoPostgres(Connection con) {
        this.con = con;
        this.propertyDao = new PropertyDaoPostgres(con);
        this.userDao = new UserDaoPostgres(con);
    }

    private List<Purchase> find(String query){
        List<Purchase> result = new ArrayList<>();
        try(Statement st = con.createStatement()){
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Purchase p = new Purchase(
                    rs.getInt("id"),
                    propertyDao.findById(rs.getInt("property_id")),
                    userDao.find(rs.getString("offer_username")),
                    rs.getString("date"),
                    rs.getInt("payment")
                );
                result.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Purchase findById(int id){
        try {
            return find("SELECT * FROM Purchase WHERE id = %d".formatted(id)).get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public List<Purchase> findByUser(User user){
        return find("SELECT * FROM Purchase WHERE buyer_username = %s".formatted(user.getUsername()));
    }

    private boolean doesPurchaseExist(int id){
        try (PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM Purchase WHERE id = ?")) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (SQLException ignored) {}
        return false;
    }

    private boolean isBoughtAlready(int property_id){
        try (PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM Purchase WHERE property_id = ?")) {
            st.setInt(1, property_id);
            ResultSet rs = st.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (SQLException ignored) {}
        return false;
    }

    @Override
    public boolean buy(String buyer_username, int property_id){
        if (isBoughtAlready(property_id)) return false;
        try(PreparedStatement st = con.prepareStatement("INSERT INTO Purchase (property_id, buyer_username, date) VALUES (?, ?, ?)")){
            st.setInt(1, property_id);
            st.setString(2, buyer_username);
            st.setObject(3, LocalDateTime.now());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    };

    @Override
    public boolean save(Purchase purchase){
        String query;
        boolean update = false;
        if (!doesPurchaseExist(purchase.getId())) {
            query = "INSERT INTO Purchase (property_id, buyer_username, date, payment) VALUES (?, ?, ?, ?)";
        } else {
            update = true;
            query = "UPDATE Purchase SET property_id=?, buyer_username=?, date=?, payment=? WHERE id=?";
        }
        try(PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, purchase.getProperty().getId());
            st.setString(2, purchase.getBuyer().getUsername());
            st.setString(3, purchase.getDate());
            st.setInt(4, purchase.getPayment());
            if (update) st.setInt(5, purchase.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void delete(Purchase purchase) {
        final String query = "DELETE FROM Purchase WHERE id=?;";
        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, purchase.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
