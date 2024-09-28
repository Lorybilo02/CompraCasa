package it.unical.compracasa.persistence.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Shop extends Property{
    private boolean hasWarehouse;
    private boolean hasForecourt;
    private boolean hasOffice;
    private boolean hasGarage;
    private boolean hasAlarmSystem;
    private int numRooms;
    private int numWindows;
    private int numBathrooms;
    private boolean hasDisabledBathroom;
    private boolean isFurnished;
    private String lastUse;

    public Shop(int id, User owner, String name, String description, String location, float latitude, float longitude, String condition,
                String energeticClass, String heatingSystem,  String imageLink, int floor, int sqm, int price,
                boolean hasWarehouse, boolean hasForecourt, boolean hasOffice, int numInternalParkingSpaces,
                boolean hasAlarmSystem, int numRooms, int numWindows, int numBathrooms,
                boolean isAccessibleToDisabled, boolean isFurnished, String purpose) {
        super(id, owner, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, "S", imageLink, sqm, price);
        this.hasWarehouse = hasWarehouse;
        this.hasForecourt = hasForecourt;
        this.hasOffice = hasOffice;
        this.hasGarage = numInternalParkingSpaces >= 1;
        this.hasAlarmSystem = hasAlarmSystem;
        this.numRooms = numRooms;
        this.numWindows = numWindows;
        this.numBathrooms = numBathrooms;
        this.hasDisabledBathroom = isAccessibleToDisabled;
        this.isFurnished = isFurnished;
        this.lastUse = purpose;
    }

    private PreparedStatement setParameters(PreparedStatement st) throws SQLException {
        int i = 0;
        st.setString(++i, getOwner().getUsername());
        st.setString(++i, getName());
        st.setString(++i, getDescription());
        st.setString(++i, getLocation());
        st.setFloat(++i, getLatitude());
        st.setFloat(++i, getLongitude());
        st.setString(++i, getCondition());
        st.setString(++i, getEnergeticClass());
        st.setString(++i, getHeatingSystem());
        st.setInt(++i, getFloor());
        st.setString(++i, type);
        st.setString(++i, getImageLink());
        st.setInt(++i, getSqm());
        st.setInt(++i, getPrice());
        st.setBoolean(++i, hasWarehouse);
        st.setBoolean(++i, hasForecourt);
        st.setBoolean(++i, hasOffice);
        st.setInt(++i, hasGarage ? 1 : 0); //
        st.setBoolean(++i, hasAlarmSystem);
        st.setInt(++i, numRooms);
        st.setInt(++i, numBathrooms);
        st.setBoolean(++i, hasDisabledBathroom);
        st.setBoolean(++i, isFurnished);
        st.setString(++i, lastUse);
        return st;
    }

    // Special Getters
    @Override
    public PreparedStatement getInsertQuery(Connection con) throws SQLException {
        String query = "INSERT INTO Property(owner_username, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, type, image, sqm, price," +
            " hasWarehouse, hasForecourt, hasOffice, numInternalParkingSpaces, hasAlarmSystem, numRooms, numBathrooms, isAccessibleToDisabled, isFurnished, purpose) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        return setParameters(con.prepareStatement(query));
    }

    @Override
    public PreparedStatement getUpdateQuery(Connection con) throws SQLException {
        String query = "UPDATE Property SET owner_username=?, name=?, description=?, location=?, latitude=?, longitude=?, condition=?, energeticClass=?, heatingSystem=?, floor=?, type=?, image=?, sqm=?, price=?, " +
            "hasWarehouse=?, hasForecourt=?, hasOffice=?, numInternalParkingSpaces=?, hasAlarmSystem=?, numRooms=?, numBathrooms=?, isAccessibleToDisabled=?, isFurnished=?, purpose=? " +
            "WHERE id=?";
        PreparedStatement st = setParameters(con.prepareStatement(query));
        st.setInt(1, getId());
        return st;
    }

    // Getters
    @Override
    public String getType(){
        return "Negozio";
    }

    public boolean isHasWarehouse() {
        return hasWarehouse;
    }

    public boolean isHasForecourt() {
        return hasForecourt;
    }

    public boolean isHasOffice() {
        return hasOffice;
    }

    public boolean isHasGarage() {
        return hasGarage;
    }

    public boolean isHasAlarmSystem() {
        return hasAlarmSystem;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public int getNumWindows() {
        return numWindows;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public boolean isHasDisabledBathroom() {
        return hasDisabledBathroom;
    }

    public boolean isFurnished() {
        return isFurnished;
    }

    public String getLastUse() {
        return lastUse;
    }

    // Setters
    public void setHasWarehouse(boolean hasWarehouse) {
        this.hasWarehouse = hasWarehouse;
    }

    public void setHasForecourt(boolean hasForecourt) {
        this.hasForecourt = hasForecourt;
    }

    public void setHasOffice(boolean hasOffice) {
        this.hasOffice = hasOffice;
    }

    public void setHasGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public void setHasAlarmSystem(boolean hasAlarmSystem) {
        this.hasAlarmSystem = hasAlarmSystem;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public void setNumWindows(int numWindows) {
        this.numWindows = numWindows;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public void setHasDisabledBathroom(boolean hasDisabledBathroom) {
        this.hasDisabledBathroom = hasDisabledBathroom;
    }

    public void setFurnished(boolean furnished) {
        isFurnished = furnished;
    }

    public void setLastUse(String lastUse) {
        this.lastUse = lastUse;
    }

}
