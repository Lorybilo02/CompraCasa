package it.unical.compracasa.controller.rest;

import it.unical.compracasa.persistence.DBManager;
import it.unical.compracasa.persistence.dao.FeedbackDao;
import it.unical.compracasa.persistence.model.Feedback;
import it.unical.compracasa.persistence.model.Property;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class FeedbackRest {
    FeedbackDao dao = DBManager.getInstance().getFeedbackDao();

    @GetMapping("/getStarsOfProperty")
    public int getStarsOfProperty(Property property){
        return dao.findStarsOfProperty(property);
    }

    @GetMapping("/getFeedbackByProperty")
    public List<Feedback> getFeedbackByProperty(Property property){
        return dao.findByProperty(property);
    }

    @PutMapping("/saveFeedback")
    public boolean saveFeedback( @RequestBody Feedback feedback){
        System.out.println(feedback.getUsername() + " "  + feedback.getPropertyId() + " " + feedback.getContent() + " " + feedback.getStars());
        return dao.save(feedback);
    }

    @DeleteMapping("/deleteFeedback")
    public void deleteFeedback(Feedback feedback){
        dao.delete(feedback);
    }
}
