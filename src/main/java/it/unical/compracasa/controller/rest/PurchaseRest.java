package it.unical.compracasa.controller.rest;

import it.unical.compracasa.persistence.DBManager;
import it.unical.compracasa.persistence.dao.PurchaseDao;
import it.unical.compracasa.persistence.model.Purchase;
import it.unical.compracasa.persistence.model.User;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class PurchaseRest {
    PurchaseDao dao = DBManager.getInstance().getPurchaseDao();

    @GetMapping("/getPurchaseById")
    public Purchase getPurchaseById(int id) {
        return dao.findById(id);
    }

    @GetMapping("/getPurchaseByUser")
    public List<Purchase> getPurchaseByUser(User user) {
        return dao.findByUser(user);
    }

    @PutMapping("/savePurchase")
    public boolean savePurchase(Purchase purchase) {
        return dao.save(purchase);
    }

    @PutMapping("/buy")
    public boolean buy(String buyer_username, int property_id) {
        return dao.buy(buyer_username, property_id);
    }

    @DeleteMapping("/deletePurchase")
    public void deletePurchase(Purchase purchase) {
        dao.delete(purchase);
    }
}
