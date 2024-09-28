package it.unical.compracasa.persistence.dao;

import it.unical.compracasa.persistence.model.Purchase;
import it.unical.compracasa.persistence.model.User;

import java.util.List;

public interface PurchaseDao {
    Purchase findById(int id);
    List<Purchase> findByUser(User user);
    boolean buy(String buyer_username, int property_id);
    boolean save(Purchase purchase);
    void delete(Purchase purchase);
}
