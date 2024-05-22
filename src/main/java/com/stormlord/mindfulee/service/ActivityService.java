package com.stormlord.mindfulee.service;

import com.stormlord.mindfulee.converter.ActivityTypeConverter;
import com.stormlord.mindfulee.entity.Activity;
import com.stormlord.mindfulee.entity.ActivityType;
import com.stormlord.mindfulee.entity.AppUser;
import com.stormlord.mindfulee.model.ActivityDto;
import com.stormlord.mindfulee.repository.ActivityRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ActivityService {

    @Inject
    private ActivityRepository activityRepository;

    @Inject
    ActivityTypeService activityTypeService;

    @Inject
    private UserService appUserService;

    public Activity createActivity(ActivityDto activityDto) {
        Activity activity = new Activity();
        AppUser user = appUserService.findByUsername(activityDto.getAppUserDto().getUsername());

        activity.setActivityType(ActivityTypeConverter.activityTypeHashMap.get(activityDto.getActivityType().getActivityTypeName()).getId());
        activity.setActivityStatus(activityDto.getActivityStatus());
        activity.setActivityName(activityDto.getActivityName());
        activity.setDateAdded(activityDto.getDateAdded());
        activity.setDateStarted(activityDto.getDateStarted());
        activity.setDateUpdated(activityDto.getDateUpdated());
        activity.setDateEnded(activityDto.getDateEnded());
        activity.setAppUser(user);
        activity.setIntentType(activityDto.getIntentType());

        return activityRepository.createUserActivity(activity);
    }

    public Activity updateActivity(ActivityDto activityDto) {
        Activity activity = new Activity();
        activity.setActivityName(activityDto.getActivityName());
        activity.setId(activityDto.getId());
        activity.setVersion(activityDto.getVersion());
        activity.setActivityType(activityDto.getActivityType().getId());
        activity.setActivityStatus(activityDto.getActivityStatus());
        activity.setDateUpdated(activityDto.getDateUpdated());
        activity.setDateAdded(activityDto.getDateAdded());
        activity.setDateStarted(activityDto.getDateStarted());
        activity.setDateEnded(activityDto.getDateEnded());
        activity.setAppUser(appUserService.findByUsername(activityDto.getAppUserDto().getUsername()));
        activity.setIntentType(activityDto.getIntentType());
        return activityRepository.updateUserActivity(activity);
    }

    public Activity updateActivity(Activity activity) {
        return activityRepository.updateUserActivity(activity);
    }

    public List<Activity> getActivitiesByUserId(Integer id) {
        return activityRepository.getActivitiesByUserId(id);
    }

    public List<Activity> getActivitiesByUserIdAndDate(Integer id, LocalDateTime currentDate) {
        return activityRepository.getActivitiesByUserIdAndDate(id, currentDate);
    }

    public List<Activity> getAvailableActivitiesByUserIdAndDate(Integer id, LocalDateTime currentDate) {
        return activityRepository.getAvailableActivitiesByUserIdAndDate(id, currentDate);
    }
}
