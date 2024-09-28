package it.unical.compracasa.persistence.dao;

import it.unical.compracasa.persistence.model.Feedback;
import it.unical.compracasa.persistence.model.Property;

import java.util.List;

public interface FeedbackDao {

    List<Feedback> findByProperty(Property property);

    int findStarsOfProperty(Property property);

    boolean save(Feedback feedback);

    void delete(Feedback feedback);
}
