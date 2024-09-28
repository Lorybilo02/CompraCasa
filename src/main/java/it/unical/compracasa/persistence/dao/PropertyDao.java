package it.unical.compracasa.persistence.dao;

import it.unical.compracasa.persistence.model.Property;
import it.unical.compracasa.persistence.model.User;

import java.util.List;

public interface PropertyDao {
    Property findById(int id);
    List<Property> findByOwner(User owner);
    List<Property> findByCrossChecking(String type, String location, Integer minPrice, Integer maxPrice, Integer minSqm, Integer maxSqm, Float latitude, Float longitude);
    List<Property> findByLocation(String location);
    List<Property> findByPrice(int minPrice, int maxPrice);
    List<Property> findBySqm(int minSqm, int maxSqm);
    int  save(Property property);
    void deleteProperty(int id);
}
