package it.unical.compracasa.persistence.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Office extends Property{
    private int numBedrooms;
    private int numBathrooms;
    private int numKitchens;
    private boolean hasLivingRoom;
    private boolean hasElevator;
    private boolean hasParkingSpace;
    private boolean hasDisabledBathroom;
    private boolean isFurnished;

    public Office(int id, User owner, String name, String description, String location, float latitude, float longitude,
                  String condition, String energeticClass, String heatingSystem, String imageLink, int floor, int sqm, int price,
                  int numRooms, int numBathrooms, int numKitchens, boolean hasLivingRoom,
                  int numElevators, int numInternalParkingSpaces, boolean isAccessibleToDisabled, boolean isFurnished) {
        super(id, owner, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, "O", imageLink, sqm, price);
        this.numBedrooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.numKitchens = numKitchens;
        this.hasLivingRoom = hasLivingRoom;
        this.hasElevator = numElevators >= 1;
        this.hasParkingSpace = numInternalParkingSpaces >= 1;
        this.hasDisabledBathroom = isAccessibleToDisabled;
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
        st.setInt(++i, hasElevator ? 1 : 0);
        st.setInt(++i, hasParkingSpace ? 1 : 0);
        st.setBoolean(++i, hasDisabledBathroom);
        st.setBoolean(++i, isFurnished);
        return st;
    }

    // Special Getters
    @Override
    public PreparedStatement getInsertQuery(Connection con) throws SQLException {
        String query = "INSERT INTO Property(owner_username, name, description, location, latitude, longitude, condition, energeticClass, heatingSystem, floor, type, image, sqm, price, numRooms, numBathrooms, numKitchens, hasLivingRoom, numElevators, numInternalParkingSpaces, isAccessibleToDisabled, isFurnished) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        return setParameters(con.prepareStatement(query));
    }

    @Override
    public PreparedStatement getUpdateQuery(Connection con) throws SQLException {
        String query = "UPDATE Property SET owner_username=?, name=?, description=?, location=?, latitude=?, longitude=?, condition=?, energeticClass=?, heatingSystem=?, floor=?, type=?, image=?, sqm=?, price=?, numRooms=?, numBathrooms=?, numKitchens=?, hasLivingRoom=?, numElevators=?, numInternalParkingSpaces=?, isAccessibleToDisabled=?, isFurnished=? WHERE id=?";
        PreparedStatement st = setParameters(con.prepareStatement(query));
        st.setInt(1, getId());
        return st;
    }

    // Getters
    @Override
    public String getType(){
        return "Ufficio";
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public int getNumKitchens() {
        return numKitchens;
    }

    public boolean isHasLivingRoom() {
        return hasLivingRoom;
    }

    public boolean isHasElevator() {
        return hasElevator;
    }

    public boolean isHasParkingSpace() {
        return hasParkingSpace;
    }

    public boolean isHasDisabledBathroom() {
        return hasDisabledBathroom;
    }

    public boolean isFurnished() {
        return isFurnished;
    }

    // Setters
    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public void setNumKitchens(int numKitchens) {
        this.numKitchens = numKitchens;
    }

    public void setHasLivingRoom(boolean hasLivingRoom) {
        this.hasLivingRoom = hasLivingRoom;
    }

    public void setHasElevator(boolean hasElevator) {
        this.hasElevator = hasElevator;
    }

    public void setHasParkingSpace(boolean hasParkingSpace) {
        this.hasParkingSpace = hasParkingSpace;
    }

    public void setHasDisabledBathroom(boolean hasDisabledBathroom) {
        this.hasDisabledBathroom = hasDisabledBathroom;
    }

    public void setFurnished(boolean furnished) {
        isFurnished = furnished;
    }

}
