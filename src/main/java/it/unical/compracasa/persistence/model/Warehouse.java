package it.unical.compracasa.persistence.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Warehouse extends Property {
    private boolean hasForecourt;
    private boolean hasOffice;
    private boolean hasAlarmSystem;
    private boolean hasGarage;
    private int numBathrooms;
    private boolean hasDisabledBathroom;

    public Warehouse(int id, User owner, String name, String description, String location, float latitude, float longitude, String condition,
                     String energeticClass, String heatingSystem, String imageLink, int floor, int sqm, int price,
                     boolean hasForecourt, boolean hasOffice, boolean hasAlarmSystem, int numInternalParkingSpaces,
                     int numBathrooms, boolean isAccessibleToDisabled) {
        super(id, owner, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, "W", imageLink, sqm, price);
        this.hasForecourt = hasForecourt;
        this.hasOffice = hasOffice;
        this.hasAlarmSystem = hasAlarmSystem;
        this.hasGarage = numInternalParkingSpaces >= 1;
        this.numBathrooms = numBathrooms;
        this.hasDisabledBathroom = isAccessibleToDisabled;
    }

    private PreparedStatement setParameters(PreparedStatement st) throws SQLException {
        int i = 0;
        st.setString(++i, getOwner().getUsername());
        st.setString(++i, getName());
        st.setString(++i, getDescription());
        st.setString(++i, getLocation());
        st.setString(++i, getCondition());
        st.setString(++i, getEnergeticClass());
        st.setString(++i, getHeatingSystem());
        st.setInt(++i, getFloor());
        st.setString(++i, type);
        st.setInt(++i, getSqm());
        st.setInt(++i, getPrice());
        st.setBoolean(++i, hasForecourt);
        st.setBoolean(++i, hasOffice);
        st.setBoolean(++i, hasAlarmSystem);
        st.setInt(++i, hasGarage ? 1 : 0);
        st.setInt(++i, numBathrooms);
        st.setBoolean(++i, hasDisabledBathroom);
        return st;
    }

    // Special Getters
    @Override
    public PreparedStatement getInsertQuery(Connection con) throws SQLException {
        String query = "INSERT INTO Property(owner_username, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, type, sqm, price, " +
            "hasForecourt, hasOffice, hasAlarmSystem, numInternalParkingSpaces, numBathrooms, isAccessibleToDisabled)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        return setParameters(con.prepareStatement(query));
    }

    @Override
    public PreparedStatement getUpdateQuery(Connection con) throws SQLException {
        String query = "UPDATE Property SET owner_username=?, name=?, description=?, location=?, latitude=?, longitude=?, condition=?, energeticClass=?, heatingSystem=?, floor=?, type=?, image=?, sqm=?, price=?, " +
            "hasForecourt=?, hasOffice=?, hasAlarmSystem=?, numInternalParkingSpaces=?, numBathrooms=?, isAccessibleToDisabled=? WHERE id=?";
        PreparedStatement st = setParameters(con.prepareStatement(query));
        st.setInt(1, getId());
        return st;
    }

    // Getters & Setters
    @Override
    public String getType(){
        return "Deposito";
    }

    public boolean isHasForecourt() {
        return hasForecourt;
    }

    public void setHasForecourt(boolean hasForecourt) {
        this.hasForecourt = hasForecourt;
    }

    public boolean isHasOffice() {
        return hasOffice;
    }

    public void setHasOffice(boolean hasOffice) {
        this.hasOffice = hasOffice;
    }

    public boolean isHasAlarmSystem() {
        return hasAlarmSystem;
    }

    public void setHasAlarmSystem(boolean hasAlarmSystem) {
        this.hasAlarmSystem = hasAlarmSystem;
    }

    public boolean isHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public boolean isHasDisabledBathroom() {
        return hasDisabledBathroom;
    }

    public void setHasDisabledBathroom(boolean hasDisabledBathroom) {
        this.hasDisabledBathroom = hasDisabledBathroom;
    }
}
