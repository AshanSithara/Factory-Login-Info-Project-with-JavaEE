package com.proje.dao;

import com.proje.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(User user){
        entityManager.persist(user);
    }

    public List<User> findAllUsers(){
        return entityManager.createNamedQuery("User.findAll",User.class).getResultList();
    }

    public User findUserWithUsername(String username){
        return  entityManager.createNamedQuery("User.findUsername",User.class).setParameter("username",username).getSingleResult();
    }

    public void merge(User user) {
        entityManager.merge(user);
    }

    public User find(int id) {
        return entityManager.find(User.class, id);
    }

}
