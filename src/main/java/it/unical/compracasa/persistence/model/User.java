package it.unical.compracasa.persistence.model;

import org.mindrot.jbcrypt.BCrypt;


public class User {
    private String username;
    private String password;        // encrypted
    private String firstname;
    private String secondname;
    private boolean isadmin;

    public User() {}
    public User(String username, String password, String firstname, String secondname, boolean isadmin) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.secondname = secondname;
        this.isadmin = isadmin;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {   // encrypted
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public boolean isIsadmin() {
        return isadmin;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }
     public void setIsadmin(boolean isadmin) {
            this.isadmin = isadmin;
     }

}
