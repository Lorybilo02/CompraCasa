package it.unical.compracasa.persistence.dao;

import it.unical.compracasa.persistence.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User find(String username);

    boolean doesUsernameExist(String username);

    boolean save(User user);

    void delete(String username);
}


