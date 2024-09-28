package it.unical.compracasa.persistence;

import it.unical.compracasa.persistence.dao.*;
import it.unical.compracasa.persistence.dao.postgres.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance = null;

    public static void main(String[] args) {
        System.out.printf("Testing. DB connection = %b%n", getInstance().getConnection() != null);
    }

    private DBManager(){}

    public static DBManager getInstance(){
        if (instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    Connection con = null;

    public Connection getConnection(){
        if (con == null){
            try {
                con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wadb", "postgres", "postgres");
            } catch (SQLException e) {
                try {
                    System.out.println("Failed Access with postgres, trying with myuser");
                    con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wadb", "myuser", "mypassword");
                } catch (SQLException f) {
                    throw new RuntimeException();
                }
            }
        }
        return con;
    }

    public UserDao getUserDao(){return new UserDaoPostgres(getConnection());}

    public PropertyDao getPropertyDao(){return new PropertyDaoPostgres(getConnection());}

    public FeedbackDao getFeedbackDao(){return new FeedbackDaoPostgres(getConnection());}

    public PurchaseDao getPurchaseDao(){return new PurchaseDaoPostgres(getConnection());}

}

