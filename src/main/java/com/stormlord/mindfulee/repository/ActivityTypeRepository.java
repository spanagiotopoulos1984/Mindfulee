package com.stormlord.mindfulee.repository;

import com.stormlord.mindfulee.entity.ActivityType;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Stateless
public class ActivityTypeRepository {

    @PersistenceContext
    private EntityManager em;


    public ActivityType createActivityType(ActivityType activityType) {
        em.persist(activityType);
        return activityType;
    }

    public ActivityType findActivityType(Integer id) {
        return em.find(ActivityType.class, id);
    }

    public ActivityType updateActivityType(ActivityType activityType) {
        return em.merge(activityType);
    }

    public List findAllActivityTypes() {
        return em.createNativeQuery(" SELECT * from activity_type a", ActivityType.class).getResultList();
    }

    public ActivityType findyName(String name) {
        Query query = em.createNativeQuery(" SELECT * from activity_type a WHERE a.activity_type_name = ?1", ActivityType.class);
        query.setParameter(1, name);
        return (ActivityType) query.getSingleResult();
    }
}
