package com.stormlord.mindfulee.repository;

import com.stormlord.mindfulee.entity.Activity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Stateless
public class ActivityRepository {

    @PersistenceContext
    private EntityManager em;

    public Activity createUserActivity(Activity activity) {
        em.persist(activity);
        return activity;
    }

    public Activity updateUserActivity(Activity activity) {
        return em.merge(activity);
    }

    public List<Activity> getActivitiesByUserId(Integer userId) {
        Query query = em.createNativeQuery(" SELECT * from activity WHERE user_id = ?1 ", Activity.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    public List<Activity> getActivitiesByUserIdAndDate(Integer userId, LocalDateTime currentDate) {
        Query query = em.createNativeQuery(" SELECT * from activity WHERE user_id = ?1 AND date_started > ?2 AND date_started < ?3 ORDER BY date_started ASC", Activity.class);
        query.setParameter(1, userId);
        query.setParameter(2, currentDate.truncatedTo(ChronoUnit.DAYS));
        query.setParameter(3, currentDate.plusDays(1).truncatedTo(ChronoUnit.DAYS));
        return query.getResultList();
    }

    public List<Activity> getAvailableActivitiesByUserIdAndDate(Integer userId, LocalDateTime currentDate) {
        Query query = em.createNativeQuery(" SELECT * from activity WHERE user_id = ?1 AND date_started > ?2 AND date_started < ?3 AND activity_status != 4 AND activity_status != 2 ORDER BY date_started ASC", Activity.class);
        query.setParameter(1, userId);
        query.setParameter(2, currentDate.truncatedTo(ChronoUnit.DAYS));
        query.setParameter(3, currentDate.plusDays(1).truncatedTo(ChronoUnit.DAYS));
        return query.getResultList();
    }
}
