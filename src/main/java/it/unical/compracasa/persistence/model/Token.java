package it.unical.compracasa.persistence.model;

public class Token{
    private String token;
    private User user;
    private boolean isAdmin;

    public Token(String token, User user){
        this.token = token;
        this.user = user;
        this.isAdmin = user.isIsadmin();
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public boolean isIsAdmin() {
        return isAdmin;
    }
}
