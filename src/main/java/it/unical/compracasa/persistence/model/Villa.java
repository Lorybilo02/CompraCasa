package it.unical.compracasa.persistence.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Villa extends Property {
    private int numBedrooms;
    private int numBathrooms;
    private int numKitchens;
    private boolean hasLivingRoom;
    private boolean hasDisabledBathroom;
    private int numInternalParkingSpaces;
    private int numExternalParkingSpaces;
    private boolean hasSwimmingPool;
    private int swimmingPoolExtension;
    private boolean hasYard;
    private int yardExtension;
    private boolean isAvailable;
    private boolean isFurnished;

    public Villa(int id, User owner, String name, String description, String location, float latitude, float longitude, String condition,
                 String energeticClass, String heatingSystem, String imageLink, int floor, int sqm, int price,
                 int numRooms, int numBathrooms, int numKitchens, boolean hasLivingRoom, boolean isAccessibleToDisabled,
                 int numInternalParkingSpaces, int numExternalParkingSpaces,
                 int swimmingPoolExtension, int yardExtension, boolean isAvailable, boolean isFurnished) {
        super(id, owner, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, "V", imageLink, sqm, price);
        this.numBedrooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.numKitchens = numKitchens;
        this.hasLivingRoom = hasLivingRoom;
        this.hasDisabledBathroom = isAccessibleToDisabled;
        this.numInternalParkingSpaces = numInternalParkingSpaces;
        this.numExternalParkingSpaces = numExternalParkingSpaces;
        this.hasSwimmingPool = swimmingPoolExtension >= 1;
        this.swimmingPoolExtension = swimmingPoolExtension;
        this.hasYard = yardExtension >= 1;
        this.yardExtension = yardExtension;
        this.isAvailable = isAvailable;
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
        st.setInt(++i, numBedrooms);
        st.setInt(++i, numBathrooms);
        st.setInt(++i, numKitchens);
        st.setBoolean(++i, hasLivingRoom);
        st.setBoolean(++i, hasDisabledBathroom);
        st.setInt(++i, numInternalParkingSpaces);
        st.setInt(++i, numExternalParkingSpaces);
        st.setInt(++i, swimmingPoolExtension);
        st.setInt(++i, yardExtension);
        st.setBoolean(++i, isAvailable);
        st.setBoolean(++i, isFurnished);
        return st;
    }

    // Special Getters
    @Override
    public PreparedStatement getInsertQuery(Connection con) throws SQLException {
        String query = "INSERT INTO Property(owner_username, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, type, image, sqm, price, " +
            "numRooms, numBathrooms, numKitchens, hasLivingRoom, isAccessibleToDisabled, numInternalParkingSpaces, numExternalParkingSpaces, swimmingPoolExtension, yardExtension, isAvailable, isFurnished) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";;
        return setParameters(con.prepareStatement(query));
    }

    @Override
    public PreparedStatement getUpdateQuery(Connection con) throws SQLException {
        String query = "UPDATE Property SET owner_username=?, name=?, description=?, location=?, latitude=?, longitude=?, condition=?, energeticClass=?, heatingSystem=?, floor=?, type=?, image=?, sqm=?, price=?, " +
            "numRooms=?, numBathrooms=?, numKitchens=?, hasLivingRoom=?, isAccessibleToDisabled=?, numInternalParkingSpaces=?, numExternalParkingSpaces=?, swimmingPoolExtension=?, yardExtension=?, isAvailable=?, isFurnished=? " +
            "WHERE id=?";
        PreparedStatement st = setParameters(con.prepareStatement(query));
        st.setInt(1, getId());
        return st;
    }

    // Getters & Setters
    @Override
    public String getType(){
        return "Villa";
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public int getNumKitchens() {
        return numKitchens;
    }

    public void setNumKitchens(int numKitchens) {
        this.numKitchens = numKitchens;
    }

    public boolean isHasLivingRoom() {
        return hasLivingRoom;
    }

    public void setHasLivingRoom(boolean hasLivingRoom) {
        this.hasLivingRoom = hasLivingRoom;
    }

    public boolean isHasDisabledBathroom() {
        return hasDisabledBathroom;
    }

    public void setHasDisabledBathroom(boolean hasDisabledBathroom) {
        this.hasDisabledBathroom = hasDisabledBathroom;
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

    public boolean isHasSwimmingPool() {
        return hasSwimmingPool;
    }

    public void setHasSwimmingPool(boolean hasSwimmingPool) {
        this.hasSwimmingPool = hasSwimmingPool;
    }

    public int getSwimmingPoolExtension() {
        return swimmingPoolExtension;
    }

    public void setSwimmingPoolExtension(int swimmingPoolExtension) {
        this.swimmingPoolExtension = swimmingPoolExtension;
    }

    public boolean isHasYard() {
        return hasYard;
    }

    public void setHasYard(boolean hasYard) {
        this.hasYard = hasYard;
    }

    public int getYardExtension() {
        return yardExtension;
    }

    public void setYardExtension(int yardExtension) {
        this.yardExtension = yardExtension;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isFurnished() {
        return isFurnished;
    }

    public void setFurnished(boolean furnished) {
        isFurnished = furnished;
    }
}
