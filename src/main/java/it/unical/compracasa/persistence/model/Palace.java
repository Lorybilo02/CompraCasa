package it.unical.compracasa.persistence.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Palace extends Property{
    private String purpose;
    private int numElevators;
    private int numBathrooms;
    private int numInternalParkingSpaces;
    private int numExternalParkingSpaces;
    private boolean isFurnished;

    public Palace(int id, User owner, String name, String description, String location, float latitude, float longitude, String condition,
                  String energeticClass, String heatingSystem,  String imageLink, int floor, int sqm, int price,
                  String purpose, int numElevators, int numBathrooms, int numInternalParkingSpaces,
                  int numExternalParkingSpaces, boolean isFurnished) {
        super(id, owner, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, "P", imageLink, sqm, price);
        this.purpose = purpose;
        this.numElevators = numElevators;
        this.numBathrooms = numBathrooms;
        this.numInternalParkingSpaces = numInternalParkingSpaces;
        this.numExternalParkingSpaces = numExternalParkingSpaces;
        this.isFurnished = isFurnished;
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
        st.setString(++i, purpose);
        st.setInt(++i, numElevators);
        st.setInt(++i, numBathrooms);
        st.setInt(++i, numInternalParkingSpaces);
        st.setInt(++i, numExternalParkingSpaces);
        st.setBoolean(++i, isFurnished);
        return st;
    }

    // Special Getters
    @Override
    public PreparedStatement getInsertQuery(Connection con) throws SQLException {
        String query = "INSERT INTO Property(owner_username, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, type, image, sqm, price, " +
            "purpose, numElevators, numBathrooms, numInternalParkingSpaces, numExternalParkingSpaces, isFurnished) "  +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        return setParameters(con.prepareStatement(query));
    }

    @Override
    public PreparedStatement getUpdateQuery(Connection con) throws SQLException {
        String query = "UPDATE Property SET owner_username=?, name=?, description=?, location=?, latitude=?, longitude=?, condition=?, energeticClass=?, heatingSystem=?, floor=?, type=?, image=?, sqm=?, price=?, " +
            "purpose=?, numElevators=?, numBathrooms=?, numInternalParkingSpaces=?, numExternalParkingSpaces=?, isFurnished=? WHERE id=? ";
        PreparedStatement st = setParameters(con.prepareStatement(query));
        st.setInt(1, getId());
        return st;
    }

    // Getters & Setters
    @Override
    public String getType(){
        return "Palazzo";
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getNumElevators() {
        return numElevators;
    }

    public void setNumElevators(int numElevators) {
        this.numElevators = numElevators;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public int getNumInternalParkingSpaces() {
        return numInternalParkingSpaces;
    }

    public void setNumInternalParkingSpaces(int numInternalParkingSpaces) {
        this.numInternalParkingSpaces = numInternalParkingSpaces;
    }

    public int getNumExternalParkingSpaces() {
        return numExternalParkingSpaces;
    }

    public void setNumExternalParkingSpaces(int numExternalParkingSpaces) {
        this.numExternalParkingSpaces = numExternalParkingSpaces;
    }

    public boolean isFurnished() {
        return isFurnished;
    }

    public void setFurnished(boolean furnished) {
        isFurnished = furnished;
    }

}
