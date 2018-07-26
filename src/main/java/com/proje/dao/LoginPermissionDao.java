package com.proje.dao;

import com.proje.model.LoginPermission;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LoginPermissionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(LoginPermission loginPermission){
        entityManager.persist(loginPermission);
    }

    public List<LoginPermission> findAllLoginPermission(){
        return entityManager.createNamedQuery("LoginPermission.findAll",LoginPermission.class).getResultList();
    }

    public void merge(LoginPermission loginPermission) {
        entityManager.merge(loginPermission);
    }

    public LoginPermission find(int id) {
        return entityManager.find(LoginPermission.class, id);
    }

}
