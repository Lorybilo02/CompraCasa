package it.unical.compracasa.persistence.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Garage extends Property{
    private boolean isIndependent;
    private int entranceWidth;
    private int numEVChargingStations;
    private boolean isAccessible24hADay;
    private boolean isAccessibleToDisabled;
    private boolean hasSurveillance;
    private boolean hasAlarmSystem;

    public Garage(int id, User owner, String name, String description, String location, float latitude, float longitude,
                  String condition, String energeticClass, String heatingSystem, String imageLink, int floor, int sqm, int price,
                  boolean isIndependent, int entranceWidth, int numEVChargingStations, boolean isAvailable,
                  boolean isAccessibleToDisabled, boolean hasSurveillance, boolean hasAlarmSystem) {
        super(id, owner, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, "G", imageLink, sqm, price);
        this.isIndependent = isIndependent;
        this.entranceWidth = entranceWidth;
        this.numEVChargingStations = numEVChargingStations;
        this.isAccessible24hADay = isAvailable;
        this.isAccessibleToDisabled = isAccessibleToDisabled;
        this.hasSurveillance = hasSurveillance;
        this.hasAlarmSystem = hasAlarmSystem;
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
        st.setBoolean(++i, isIndependent);
        st.setInt(++i, entranceWidth);
        st.setInt(++i, numEVChargingStations);
        st.setBoolean(++i, isAccessible24hADay);
        st.setBoolean(++i, isAccessibleToDisabled);
        st.setBoolean(++i, hasSurveillance);
        st.setBoolean(++i, hasAlarmSystem);
        return st;
    }

    // Special Getters
    @Override
    public PreparedStatement getInsertQuery(Connection con) throws SQLException {
        String query = "INSERT INTO Property(owner_username, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, type, image, sqm, price, isindependent, entranceWidth, numEVChargingStations, isAvailable, isAccessibleToDisabled, hasSurveillance, hasAlarmSystem) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        return setParameters(con.prepareStatement(query));
    }

    @Override
    public PreparedStatement getUpdateQuery(Connection con) throws SQLException {
        String query = "UPDATE Property SET owner_username=?, name=?, description=?, location=?, latitude=?, longitude=?, condition=?, energeticClass=?, heatingSystem=?, floor=?, type=?, image=?, sqm=?, price=?, isindependent=?, entranceWidth=?, numEVChargingStations=?, isAvailable=?, isAccessibleToDisabled=?, hasSurveillance=?, hasAlarmSystem=? WHERE id=?";
        PreparedStatement st = setParameters(con.prepareStatement(query));;
        st.setInt(19, getId());
        return st;
    }

    // Getters
    @Override
    public String getType(){
        return "Garage";
    }

    public boolean isIndependent() {
        return isIndependent;
    }

    public int getEntranceWidth() {
        return entranceWidth;
    }

    public int getNumEVChargingStations() {
        return numEVChargingStations;
    }

    public boolean isAccessible24hADay() {
        return isAccessible24hADay;
    }

    public boolean isAccessibleToDisabled() {
        return isAccessibleToDisabled;
    }

    public boolean isHasSurveillance() {
        return hasSurveillance;
    }

    public boolean isHasAlarmSystem() {
        return hasAlarmSystem;
    }

    // Setters
    public void setIndependent(boolean independent) {
        isIndependent = independent;
    }

    public void setEntranceWidth(int entranceWidth) {
        this.entranceWidth = entranceWidth;
    }

    public void setNumEVChargingStations(int numEVChargingStations) {
        this.numEVChargingStations = numEVChargingStations;
    }

    public void setAccessible24hADay(boolean accessible24hADay) {
        isAccessible24hADay = accessible24hADay;
    }

    public void setAccessibleToDisabled(boolean accessibleToDisabled) {
        isAccessibleToDisabled = accessibleToDisabled;
    }

    public void setHasSurveillance(boolean hasSurveillance) {
        this.hasSurveillance = hasSurveillance;
    }

    public void setHasAlarmSystem(boolean hasAlarmSystem) {
        this.hasAlarmSystem = hasAlarmSystem;
    }
}
