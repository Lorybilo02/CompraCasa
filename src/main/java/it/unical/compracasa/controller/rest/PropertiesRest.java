package it.unical.compracasa.controller.rest;

import it.unical.compracasa.persistence.dao.PropertyDao;
import it.unical.compracasa.persistence.model.Property;
import it.unical.compracasa.persistence.DBManager;
import it.unical.compracasa.persistence.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class PropertiesRest {
    PropertyDao dao = DBManager.getInstance().getPropertyDao();

    @GetMapping("/getPropertyById")
    public Property getPropertyById(int id){
        Property property = dao.findById(id);
        System.out.println(property.getDescription());
        return property;
    }

    @GetMapping("/getPropertyByOwner")
    public List<Property> getPropertyByOwner(@RequestParam String username) {
        User owner = new User();
        owner.setUsername(username);
        return dao.findByOwner(owner);
    }

    @GetMapping("/getPropertyByLocation")
    public List<Property> getPropertyByLocation(String location){
        List<Property> properties = dao.findByLocation(location);
        Property firstProperty = properties.get(0);
        System.out.println(firstProperty.getLocation());
        return properties;
    }

    @GetMapping("/getPropertyByPrice")
    public List<Property> getPropertyByPrice(int minPrice, int maxPrice){
        List<Property> properties = dao.findByPrice(minPrice, maxPrice);
        Property firstProperty = properties.get(0);
        System.out.println(firstProperty.getPrice());
        return properties;
    }

    @GetMapping("/getPropertyBySqm")
    public List<Property> getPropertyBySqm(int minSqm, int maxSqm){
        List<Property> properties = dao.findBySqm(minSqm, maxSqm);
        Property firstProperty = properties.get(0);
        System.out.println(firstProperty.getSqm());
        return properties;
    }

    @GetMapping(value = "/getPropertyByResearch", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Property>> getPropertyByResearch(
        @RequestParam(required = false) String type,
        @RequestParam(required = false, name = "location") String town,
        @RequestParam(required = false) Integer priceMin,
        @RequestParam(required = false) Integer priceMax,
        @RequestParam(required = false) Integer minSqm,
        @RequestParam(required = false) Integer maxSqm,
        @RequestParam(required = false) Float lat,
        @RequestParam(required = false) Float lng) {
        List<Property> properties = dao.findByCrossChecking(type, town, priceMin, priceMax, minSqm, maxSqm, lat, lng);

        if (properties.isEmpty()) { // If the list is empty, return HttpStatus.NO_CONTENT
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(properties);
    }

    @PutMapping("/saveProperty")
    public int saveProperty(Property property){
        return dao.save(property);
    }

    @DeleteMapping("/deleteProperty")
    public ResponseEntity<Void> deleteProperty(@RequestParam int id) {
        try {
            dao.deleteProperty(id);
            return ResponseEntity.ok().build(); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


