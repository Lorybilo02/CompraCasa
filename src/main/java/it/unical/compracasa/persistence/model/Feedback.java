package it.unical.compracasa.persistence.model;

import it.unical.compracasa.persistence.DBManager;

public class Feedback {
    private User user;
    private String username;
    private Property property;
    private int propertyId;
    private String content;
    private int stars;

    public Feedback() {}
    public Feedback (String username, int propertyId, String content, int stars) {
        this.user = DBManager.getInstance().getUserDao().find(username);
        this.username = username;
        this.property = DBManager.getInstance().getPropertyDao().findById(propertyId);
        this.propertyId = propertyId;
        this.content = content;
        this.stars = stars;
    }
    public Feedback (String username, Property property, String content, int stars) {
        this.user = DBManager.getInstance().getUserDao().find(username);
        this.username = username;
        this.property = property;
        this.content = content;
        this.stars = stars;
    }
    public Feedback(User user, Property property, String content, int stars) {
        this.user = user;
        this.username = user.getUsername();
        this.property = property;
        this.content = content;
        this.stars = stars;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
