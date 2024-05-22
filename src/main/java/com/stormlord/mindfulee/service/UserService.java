package com.stormlord.mindfulee.service;

import com.stormlord.mindfulee.entity.Activity;
import com.stormlord.mindfulee.entity.AppUser;
import com.stormlord.mindfulee.model.ActivityDto;
import com.stormlord.mindfulee.repository.UserRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Stateless
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private ActivityService activityService;

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUserName(username).orElse(null);
    }

    public AppUser createAppUser(AppUser appUser) {
        return userRepository.createOrupdate(appUser);
    }

    public List<ActivityDto> findUserActivities(Integer id, LocalDateTime currentDate) {
        List<Activity> userActivitiesLst = activityService.getActivitiesByUserIdAndDate(id, currentDate);
        return userActivitiesLst.stream()
                .map(ActivityDto::new)
                .toList();
    }

    public List<ActivityDto> findUpcomingUserActivities(Integer id, LocalDateTime currentDate) {
        List<Activity> userActivitiesLst = activityService.getAvailableActivitiesByUserIdAndDate(id, currentDate);
        return userActivitiesLst.stream()
                .map(ActivityDto::new)
                .toList();
    }

    public HashMap<Integer, Integer> getUserActivityCategoryStatistics(Integer userId) {
        return userRepository.getUserActivityTypeStatistics(userId);
    }

    public HashMap<Integer, Integer> getDailyUserActivityCategoryStatistics(Integer userId, LocalDateTime localDateTime) {
        return userRepository.getDailyUserActivityTypeStatistics(userId,localDateTime);
    }

    public HashMap<Integer, Integer> getUserActivityStatusStatistics(Integer userId) {
        return userRepository.getUserActivityStatusStatistics(userId);
    }

    public HashMap<Integer, Integer> getDailyUserActivityStatusStatistics(Integer userId, LocalDateTime localDateTime) {
        return userRepository.getDailyUserActivityStatusStatistics(userId,localDateTime);
    }

    public Integer getTotalActivitiesByUserId(Integer userId) {
        return userRepository.getTotalActivitiesByUserId(userId);
    }

    public Integer getTotalDailyActivitiesByUserId(Integer userId, LocalDateTime localDateTime) {
        return userRepository.getTotalDailyActivitiesByUserId(userId, localDateTime);
    }
}
