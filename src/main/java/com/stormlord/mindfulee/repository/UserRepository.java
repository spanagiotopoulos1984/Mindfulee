package com.stormlord.mindfulee.repository;

import com.stormlord.mindfulee.entity.AppUser;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public AppUser createOrupdate(AppUser user) {
        String hashedPwd = hashPassword(user.getPassword());
        user.setPassword(hashedPwd);
        user = em.merge(user);
        em.flush();
        return user;
    }

    public void delete(AppUser user) {
        em.remove(user);
    }

    public List findAll() {
        return em.createNativeQuery(" SELECT * from app_user", AppUser.class).getResultList();
    }

    public Optional<AppUser> findByUserName(String username) {
        Query query = em.createNativeQuery(" SELECT * from app_user u where u.username like ?1 ", AppUser.class);
        query.setParameter(1, username);
        try {
            return Optional.of((AppUser) query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public HashMap<Integer, Integer> getUserActivityTypeStatistics(Integer userId) {
        Query q = em.createNativeQuery("SELECT ACTIVITY_TYPE_ID,COUNT(*) FROM ACTIVITY  WHERE USER_ID = ?1 GROUP BY ACTIVITY_TYPE_ID ");
        q.setParameter(1, userId);
        return convertResultListToMap(q.getResultList());
    }

    public HashMap<Integer, Integer> getDailyUserActivityTypeStatistics(Integer userId, LocalDateTime localDateTime) {
        Query q = em.createNativeQuery("SELECT ACTIVITY_TYPE_ID,COUNT(*) FROM ACTIVITY  WHERE user_id = ?1 AND date_started > ?2 AND date_started < ?3 GROUP BY ACTIVITY_TYPE_ID ");
        q.setParameter(1, userId);
        q.setParameter(2, localDateTime.truncatedTo(ChronoUnit.DAYS));
        q.setParameter(3, localDateTime.plusDays(1).truncatedTo(ChronoUnit.DAYS));
        return convertResultListToMap(q.getResultList());
    }

    public HashMap<Integer, Integer> getUserActivityStatusStatistics(Integer userId) {
        Query q = em.createNativeQuery("SELECT ACTIVITY_STATUS,COUNT(*) FROM ACTIVITY  WHERE USER_ID = ?1 GROUP BY ACTIVITY_STATUS ");
        q.setParameter(1, userId);
        return convertResultListToMap(q.getResultList());
    }

    public HashMap<Integer, Integer> getDailyUserActivityStatusStatistics(Integer userId, LocalDateTime localDateTime) {
        Query q = em.createNativeQuery("SELECT ACTIVITY_STATUS,COUNT(*) FROM ACTIVITY  WHERE user_id = ?1 AND date_started > ?2 AND date_started < ?3 GROUP BY ACTIVITY_STATUS ");
        q.setParameter(1, userId);
        q.setParameter(2, localDateTime.truncatedTo(ChronoUnit.DAYS));
        q.setParameter(3, localDateTime.plusDays(1).truncatedTo(ChronoUnit.DAYS));
        return convertResultListToMap(q.getResultList());
    }

    public Integer getTotalActivitiesByUserId(Integer userId) {
        Query query = em.createNativeQuery(" SELECT count(*) from activity WHERE user_id = ?1");
        query.setParameter(1, userId);
        return ((Long) query.getSingleResult()).intValue();
    }

    public Integer getTotalDailyActivitiesByUserId(Integer userId, LocalDateTime localDateTime) {
        Query query = em.createNativeQuery(" SELECT count(*) from activity WHERE user_id = ?1 AND date_started > ?2 AND date_started < ?3");
        query.setParameter(1, userId);
        query.setParameter(2, localDateTime.truncatedTo(ChronoUnit.DAYS));
        query.setParameter(3, localDateTime.plusDays(1).truncatedTo(ChronoUnit.DAYS));
        return ((Long) query.getSingleResult()).intValue();
    }

    public AppUser findById(int id) {
        return em.find(AppUser.class, id);
    }

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    private HashMap<Integer, Integer> convertResultListToMap(List<Object[]> rows) {
        HashMap<Integer, Integer> statsMap = new HashMap<>();
        rows.forEach(row -> {
            Integer key = (Integer) row[0];
            Integer value = ((Long) row[1]).intValue();
            statsMap.put(key, value);
        });
        return statsMap;
    }
}
