package com.proje.dao;

import com.proje.model.Roles;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Roles> findAllRoles(){
        return entityManager.createNamedQuery("Role.findAll",Roles.class).getResultList();
    }

}
