package it.unical.compracasa.persistence.model;

public class Purchase {
    private int id;
    private Property property;
    private User buyer;
    private String date;
    private int payment;

    public Purchase(int id, Property propertyId, User offerUsername, String date, int payment) {
        this.id = id;
        this.property = propertyId;
        this.buyer = offerUsername;
        this.date = date;
        this.payment = payment;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

}
