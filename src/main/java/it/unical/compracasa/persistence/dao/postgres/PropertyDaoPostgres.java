package it.unical.compracasa.persistence.dao.postgres;

import it.unical.compracasa.persistence.DBManager;
import it.unical.compracasa.persistence.dao.PropertyDao;
import it.unical.compracasa.persistence.dao.UserDao;
import it.unical.compracasa.persistence.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PropertyDaoPostgres implements PropertyDao {
    Connection con;
    UserDao userDao;

    public PropertyDaoPostgres(Connection con) {
        this.con = con;
        this.userDao = DBManager.getInstance().getUserDao();
    }

    private boolean doesPropertyExist(int id) {
        try (PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM Property WHERE id = ?")) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (SQLException ignored) {
        }
        return false;
    }

    private List<Property> find(PreparedStatement st) {
        List<Property> result = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Property p = switch (rs.getString("type")) {
                    case "A" -> new Apartment(
                        rs.getInt("id"),
                        userDao.find(rs.getString("owner_username")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("condition"),
                        rs.getString("energeticClass"),
                        rs.getString("heatingSystem"),
                        rs.getString("image"),
                        rs.getInt("floor"),
                        rs.getInt("sqm"),
                        rs.getInt("price"),
                        rs.getInt("numRooms"),
                        rs.getInt("numBathrooms"),
                        rs.getInt("numKitchens"),
                        rs.getBoolean("hasLivingRoom"),
                        rs.getInt("numElevators"),
                        rs.getInt("numInternalParkingSpaces"),
                        rs.getBoolean("isAccessibleToDisabled"),
                        rs.getBoolean("isFurnished")
                    );
                    case "G" -> new Garage(
                        rs.getInt("id"),
                        userDao.find(rs.getString("owner_username")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("condition"),
                        rs.getString("energeticClass"),
                        rs.getString("heatingSystem"),
                        rs.getString("image"),
                        rs.getInt("floor"),
                        rs.getInt("sqm"),
                        rs.getInt("price"),
                        rs.getBoolean("isIndependent"),
                        rs.getInt("entranceWidth"),
                        rs.getInt("numEVChargingStations"),
                        rs.getBoolean("isAvailable"),
                        rs.getBoolean("isAccessibleToDisabled"),
                        rs.getBoolean("hasSurveillance"),
                        rs.getBoolean("hasAlarmSystem")
                    );
                    case "O" -> new Office(
                        rs.getInt("id"),
                        userDao.find(rs.getString("owner_username")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("condition"),
                        rs.getString("energeticClass"),
                        rs.getString("heatingSystem"),
                        rs.getString("image"),
                        rs.getInt("floor"),
                        rs.getInt("sqm"),
                        rs.getInt("price"),
                        rs.getInt("numRooms"),
                        rs.getInt("numBathrooms"),
                        rs.getInt("numKitchens"),
                        rs.getBoolean("hasLivingRoom"),
                        rs.getInt("numElevators"),
                        rs.getInt("numInternalParkingSpaces"),
                        rs.getBoolean("isAccessibleToDisabled"),
                        rs.getBoolean("isFurnished")
                    );
                    case "P" -> new Palace(
                        rs.getInt("id"),
                        userDao.find(rs.getString("owner_username")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("condition"),
                        rs.getString("energeticClass"),
                        rs.getString("heatingSystem"),
                        rs.getString("image"),
                        rs.getInt("floor"),
                        rs.getInt("sqm"),
                        rs.getInt("price"),
                        rs.getString("purpose"),
                        rs.getInt("numElevators"),
                        rs.getInt("numBathrooms"),
                        rs.getInt("numInternalParkingSpaces"),
                        rs.getInt("numExternalParkingSpaces"),
                        rs.getBoolean("isFurnished")
                    );
                    case "S" -> new Shop(
                        rs.getInt("id"),
                        userDao.find(rs.getString("owner_username")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("condition"),
                        rs.getString("energeticClass"),
                        rs.getString("heatingSystem"),
                        rs.getString("image"),
                        rs.getInt("floor"),
                        rs.getInt("sqm"),
                        rs.getInt("price"),
                        rs.getBoolean("hasWarehouse"),
                        rs.getBoolean("hasForecourt"),
                        rs.getBoolean("hasOffice"),
                        rs.getInt("numInternalParkingSpaces"),
                        rs.getBoolean("hasAlarmSystem"),
                        rs.getInt("numRooms"),
                        rs.getInt("numWindows"),
                        rs.getInt("numBathrooms"),
                        rs.getBoolean("isAccessibleToDisabled"),
                        rs.getBoolean("isFurnished"),
                        rs.getString("purpose")
                    );
                    case "V" -> new Villa(
                        rs.getInt("id"),
                        userDao.find(rs.getString("owner_username")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("condition"),
                        rs.getString("energeticClass"),
                        rs.getString("heatingSystem"),
                        rs.getString("image"),
                        rs.getInt("floor"),
                        rs.getInt("sqm"),
                        rs.getInt("price"),
                        rs.getInt("numRooms"),
                        rs.getInt("numBathrooms"),
                        rs.getInt("numKitchens"),
                        rs.getBoolean("hasLivingRoom"),
                        rs.getBoolean("isAccessibleToDisabled"),
                        rs.getInt("numInternalParkingSpaces"),
                        rs.getInt("numExternalParkingSpaces"),
                        rs.getInt("swimmingPoolExtension"),
                        rs.getInt("yardExtension"),
                        rs.getBoolean("isAvailable"),
                        rs.getBoolean("isFurnished")
                    );
                    case "W" -> new Warehouse(
                        rs.getInt("id"),
                        userDao.find(rs.getString("owner_username")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("condition"),
                        rs.getString("energeticClass"),
                        rs.getString("heatingSystem"),
                        rs.getString("image"),
                        rs.getInt("floor"),
                        rs.getInt("sqm"),
                        rs.getInt("price"),
                        rs.getBoolean("hasForecourt"),
                        rs.getBoolean("hasOffice"),
                        rs.getBoolean("hasAlarmSystem"),
                        rs.getInt("numInternalParkingSpaces"),
                        rs.getInt("numBathrooms"),
                        rs.getBoolean("isAccessibleToDisabled")
                    );
                    default -> throw new IllegalStateException("Unexpected value: " + rs.getString("type"));
                };
                result.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Property findById(int id) {
        try (PreparedStatement st = con.prepareStatement("SELECT * FROM Property WHERE id = ?")) {
            st.setInt(1, id);
            return find(st).get(0);
        } catch (IndexOutOfBoundsException | SQLException e) {
            return null;
        }
    }

    @Override
    public List<Property> findByOwner(User owner) {
        try (PreparedStatement st = con.prepareStatement("SELECT * FROM Property WHERE owner_username = ?")) {
            st.setString(1, owner.getUsername());
            return find(st);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Property> findByCrossChecking(String type, String location, Integer minPrice, Integer maxPrice, Integer minSqm, Integer maxSqm, Float latitude, Float longitude) {

        String query = "SELECT * FROM Property WHERE price BETWEEN ? AND ? AND sqm BETWEEN ? AND ?";

        boolean isThereGeolocation = false;
        if (latitude != 0F && longitude != 0F) {
            isThereGeolocation = true;
            query += " AND latitude BETWEEN ? AND ? AND longitude BETWEEN ? AND ?";
        }

        boolean isThereType = false;
        if (type != null && !type.isEmpty()) {
            type = String.valueOf(type.charAt(0));
            if (type.equals("D")) type = "W";
            isThereType = true;
            query += " AND type = ?";
        }

        boolean isThereLocation = false;
        if (location != null && !location.isEmpty()) {
            isThereLocation = true;
            query += " AND location = ?";
        }

        if (maxPrice == 0 ) maxPrice = Integer.MAX_VALUE;
        if (maxSqm == 0 ) maxSqm = Integer.MAX_VALUE;
        try (PreparedStatement st = con.prepareStatement(query)) {
            int i = 0;
            st.setInt(++i, minPrice);
            st.setInt(++i, maxPrice);
            st.setInt(++i, minSqm);
            st.setInt(++i, maxSqm);
            if (isThereGeolocation)st.setFloat(++i, latitude - 0.5F);
            if (isThereGeolocation)st.setFloat(++i, latitude + 0.5F);
            if (isThereGeolocation)st.setFloat(++i, longitude - 0.5F);
            if (isThereGeolocation) st.setFloat(++i, longitude + 0.5F);
            if (isThereType) st.setString(++i, type);
            if (isThereLocation) st.setString(++i, location);
            return find(st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Property> findByLocation(String location) {
        try (PreparedStatement st = con.prepareStatement("SELECT * FROM Property WHERE location = ?")) {
            st.setString(1, location);
            return find(st);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Property> findByPrice(int minPrice, int maxPrice) {
        try (PreparedStatement st = con.prepareStatement("SELECT * FROM Property WHERE price BETWEEN ? AND ?")) {
            st.setInt(1, minPrice);
            st.setInt(2, maxPrice);
            return find(st);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Property> findBySqm(int minSqm, int maxSqm) {
        try (PreparedStatement st = con.prepareStatement("SELECT * FROM Property WHERE sqm BETWEEN ? AND ?")) {
            st.setInt(1, minSqm);
            st.setInt(2, maxSqm);
            return find(st);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int save(Property property){

        PreparedStatement st;
        try {
            if (!doesPropertyExist(property.getId())) {
                st = property.getInsertQuery(con);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    if (property.getId() == 0) {
                        property.setId(id);
                    }
                    return id;
                } else return 0;
            } else {
                st = property.getUpdateQuery(con);
                st.executeUpdate();
                return 1;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public void deleteProperty(int id) { // git infame
        final String query = "DELETE FROM Property WHERE id=?";
        try (PreparedStatement st = con.prepareStatement(query)) { // git infame
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
