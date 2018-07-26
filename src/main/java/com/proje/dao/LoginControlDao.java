package com.proje.dao;

import com.proje.model.LoginControl;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LoginControlDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(LoginControl loginControl){
        entityManager.persist(loginControl);
    }

    public List<LoginControl> findLoginControlWithUsername(String companyName){
        return  entityManager.createNamedQuery("LoginControl.findAllWithCompanyName",LoginControl.class).setParameter("companyName",companyName).getResultList();
    }

    public void merge(LoginControl loginControl) {
        entityManager.merge(loginControl);
    }

    public LoginControl find(int id) {
        return entityManager.find(LoginControl.class, id);
    }

    public LoginControl findWithLoginPermissionId(int id,int userId){
        return entityManager.createNamedQuery("LoginControl.findAllWithPermissionId",LoginControl.class).setParameter("loginPermissionId",id).setParameter("userId",userId).getSingleResult();
    }
}
