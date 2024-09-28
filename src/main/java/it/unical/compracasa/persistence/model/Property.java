package it.unical.compracasa.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unical.compracasa.persistence.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Property {
    private int id;
    private User owner;
    private String name; // "sellMode,address,civicNumber,email,phoneNumber"
    private String sellMode;
    private String address;
    private String civicNumber;
    private String email;
    private String phoneNumber;
    private String description;
    private String location;
    private float latitude;
    private float longitude;
    private String condition;
    private String energeticClass; // not in Garage
    private String heatingSystem; // not in Garage and Villa
    private int floor; // in Palace and Villa it indicates how many floor they have
    protected String type;
    private String imageLink;
    private int sqm;
    private int price; // float? nah vendiamo case non goleador

    public Property(int id, User owner, String name, String description, String location, float latitude, float longitude,
                    String condition, String energeticClass, String heatingSystem, int floor, String type, String imageLink, int sqm, int price){
        this.id = id;
        this.owner = owner;
        this.name = name;
        String[] s = name.split(",");
        this.sellMode = (s.length > 0) ? s[0] : "";
        this.address = (s.length > 1) ? s[1] : "";
        this.civicNumber = (s.length > 2) ? s[2] : "";
        this.email = (s.length > 3) ? s[3] : "";
        this.phoneNumber = (s.length > 4) ? s[4] : "";
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.condition = condition;
        this.energeticClass = energeticClass;
        this.heatingSystem = heatingSystem;
        this.floor = floor;
        this.type = type;
        this.imageLink = imageLink;
        this.sqm = sqm;
        this.price = price;
    }

    public abstract PreparedStatement getInsertQuery(Connection con) throws SQLException;
    public abstract PreparedStatement getUpdateQuery(Connection con) throws SQLException;

    // Getters & Setters
    public int getStarsRating(){
        return DBManager.getInstance().getFeedbackDao().findStarsOfProperty(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    protected String getName() {
        return name;
    }

    public String getSellMode() {
        return sellMode;
    }

    public String getAddress() {
        return address;
    }

    public String getCivicNumber() {
        return civicNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getEnergeticClass() {
        return energeticClass;
    }

    public void setEnergeticClass(String energeticClass) {
        this.energeticClass = energeticClass;
    }

    public String getHeatingSystem() {
        return heatingSystem;
    }

    public void setHeatingSystem(String heatingSystem) {
        this.heatingSystem = heatingSystem;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getSqm() {
        return sqm;
    }

    public void setSqm(int sqm) {
        this.sqm = sqm;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStarRating() {
        return DBManager.getInstance().getFeedbackDao().findStarsOfProperty(this);
    }


}

// QUERY TABLE
/*
CREATE TABLE Property (
id SERIAL PRIMARY KEY,
owner_username VARCHAR(255) NOT NULL,
name VARCHAR(255) NOT NULL,
description TEXT,
location VARCHAR(255),
condition VARCHAR(50),
energeticClass VARCHAR(2),
heatingSystem VARCHAR(15),
floor INT,
type VARCHAR(1),
sqm INT,
price INT,
numBathrooms INT,
numKitchens INT,
hasLivingRoom BOOLEAN,
isFurnished BOOLEAN,
isIndependent BOOLEAN,
entranceWidth INT,
numEVChargingStations INT,
isAccessibleToDisabled BOOLEAN,
hasSurveillance BOOLEAN,
hasAlarmSystem BOOLEAN,
purpose VARCHAR(255),
numElevators INT,
numInternalParkingSpaces INT,
numExternalParkingSpaces INT,
hasWarehouse BOOLEAN,
hasForecourt BOOLEAN,
hasOffice BOOLEAN,
numRooms INT,
numWindows INT,
swimmingPoolExtension INT,
yardExtension INT,
isAvailable BOOLEAN
);
*/
