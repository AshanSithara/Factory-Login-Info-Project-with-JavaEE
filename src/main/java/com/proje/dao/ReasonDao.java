package com.proje.dao;

import com.proje.model.IntroductionReason;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReasonDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(IntroductionReason reason){
        entityManager.persist(reason);
    }

    public void merge(IntroductionReason reason){ entityManager.merge(reason); }

    public void remove(IntroductionReason reason) { entityManager.remove(reason);}

    public IntroductionReason find(int id){ return  entityManager.find(IntroductionReason.class,id); }

    public List<IntroductionReason> findAllReasons(){
        return entityManager.createNamedQuery("Reason.findAll",IntroductionReason.class).getResultList();
    }
}
