package com.stormlord.mindfulee.view;

import com.stormlord.mindfulee.bean.UserBn;
import com.stormlord.mindfulee.enums.ActivityCategory;
import com.stormlord.mindfulee.enums.ActivityStatus;
import com.stormlord.mindfulee.enums.IntentType;
import com.stormlord.mindfulee.model.ActivityDto;
import com.stormlord.mindfulee.model.ActivityTypeDto;
import com.stormlord.mindfulee.service.ActivityService;
import com.stormlord.mindfulee.service.ActivityTypeService;
import com.stormlord.mindfulee.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.PieChartModel;

import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

@Named("eventView")
@ViewScoped
public class EventView implements Serializable {
    private static final Logger log = LogManager.getLogger(EventView.class);

    @Inject
    private UserBn userBn;

    @Inject
    private UserService userService;

    @Inject
    private ActivityService activityService;

    @Inject
    private ActivityTypeService activityTypeService;

    private int totalActivities;
    private int totalDailyActivities;

    private int dailyActivitiesCompleted;
    private int dailyActivitiesPending;
    private int dailyActivitiesCancelled;

    private int totalActivitiesCompleted;
    private int totalActivitiesPending;
    private int totalActivitiesCancelled;

    private double dailyPendingPercent;
    private double dailyCompletedPercent;
    private double dailyCancelledPercent;
    private double totalPendingPercent;
    private double totalCompletedPercent;
    private double totalCancelledPercent;

    private List<ActivityDto> userActivities;

    private ActivityDto currentActivity;

    private ActivityTypeDto currentActivityType;

    private HashMap<ActivityCategory, Integer> dailyUserActivityiesByCategory;
    private HashMap<ActivityCategory, Integer> totalUserActivityiesByCategory;

    private PieChartModel statusPieChartModel;
    private PieChartModel categoryPieChartModel;

    private long dailyWorkHours;
    private long dailyGamingHours;
    private long dailyRestHours;
    private boolean doneChores;
    private boolean introspecting;
    private boolean socializing;


    @PostConstruct
    public void init() {
        clearCurrentActivity();
        clearCurrentActivityType();

        //Just to init the Converter List
        getActivityTypesLst();
        userActivities = userService.findUpcomingUserActivities(userBn.getAppUser().getId(), userBn.getSelectedDate());
        reloadStatistics();
    }

    public ActivityStatus[] getActivityStatusLst() {
        return ActivityStatus.values();
    }

    public IntentType[] getIntentTypeLst() {
        return IntentType.values();
    }

    public ActivityTypeDto[] getActivityTypesLst() {
        return activityTypeService.findAll().toArray(new ActivityTypeDto[0]);
    }

    public ActivityCategory[] getActivityCategoryLst() {
        return ActivityCategory.values();
    }

    private void clearCurrentActivityType() {
        currentActivityType = new ActivityTypeDto();
    }

    private void clearCurrentActivity() {
        currentActivity = new ActivityDto();
    }

    public List<ActivityDto> getUserDailyActivities() {
        return userService.findUserActivities(userBn.getAppUser().getId(), userBn.getSelectedDate());
    }

    public void onRowEdit(RowEditEvent<ActivityDto> event) {
        FacesMessage msg;
        if (event.getObject().getDateStarted().isAfter(event.getObject().getDateEnded())) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong Dates", "Starting Date of an event cannot be later than the Ending Date of that event.");
        } else {
            ActivityDto updatedActivity = event.getObject();
            updatedActivity.setAppUserDto(userBn.getAppUser());
            updatedActivity.setDateUpdated(LocalDateTime.now());

            activityService.updateActivity(updatedActivity);

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity Edited", "Activity has been edited successfully.");
            reload();
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        userActivities = userService.findUpcomingUserActivities(userBn.getAppUser().getId(), LocalDateTime.now());
    }

    public void onRowCancel(RowEditEvent<ActivityDto> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addCurrentActivityType() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        if (currentActivityType.getActivityTypeName() == null || currentActivityType.getActivityTypeName().isEmpty()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Missing Activity Type Name", "Please Add Activity Name.");
        } else {
            activityTypeService.createActivityType(currentActivityType);
            clearCurrentActivityType();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity Type Added", "Activity Type has been added successfully");
        }
        reload();
        context.addMessage(null, msg);
    }

    public void addCurrentActivity() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        if (currentActivity.getActivityName() == null || currentActivity.getActivityName().isEmpty()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Missing Activity Name", "Please Add Activity Name.");
        } else if (currentActivity.getDateStarted().isAfter(currentActivity.getDateEnded())) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong Dates", "Activity Starting Date cannot be After Activity Ending Date.");
        } else {
            currentActivity.setDateAdded(LocalDateTime.now());
            currentActivity.setDateUpdated(LocalDateTime.now());
            currentActivity.setAppUserDto(userBn.getAppUser());
            currentActivity.setActivityStatus(ActivityStatus.ADDED);
            activityService.createActivity(currentActivity);
            clearCurrentActivity();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity Added", "Activity has been added successfully");
        }
        userActivities = userService.findUpcomingUserActivities(userBn.getAppUser().getId(), userBn.getSelectedDate());
        context.addMessage(null, msg);
        reload();
    }

    public LocalDateTime getGetNow() {
        return LocalDateTime.now();
    }

    public void onDateSelect(SelectEvent<LocalDateTime> event) {
        userActivities = userService.findUpcomingUserActivities(userBn.getAppUser().getId(), userBn.getSelectedDate());
        reloadStatistics();
    }

    // Statistics
    private void reloadStatistics() {
        int userId = userBn.getAppUser().getId();
        totalUserActivityiesByCategory = new HashMap<>();
        dailyUserActivityiesByCategory = new HashMap<>();

        HashMap<Integer, Integer> dailyActivityStatusStatistics = userService.getDailyUserActivityStatusStatistics(userId, userBn.getSelectedDate());
        HashMap<Integer, Integer> activityStatusStatistics = userService.getUserActivityStatusStatistics(userId);

        totalActivities = userService.getTotalActivitiesByUserId(userId);
        totalDailyActivities = userService.getTotalDailyActivitiesByUserId(userId, userBn.getSelectedDate());

        dailyActivitiesCompleted = dailyActivityStatusStatistics.get(2) == null ? 0 : dailyActivityStatusStatistics.get(2);
        dailyActivitiesPending = (dailyActivityStatusStatistics.get(0) == null ? 0 : dailyActivityStatusStatistics.get(0)) +
                (dailyActivityStatusStatistics.get(1) == null ? 0 : dailyActivityStatusStatistics.get(1)) +
                (dailyActivityStatusStatistics.get(3) == null ? 0 : dailyActivityStatusStatistics.get(3));
        dailyActivitiesCancelled = (dailyActivityStatusStatistics.get(4) == null ? 0 : dailyActivityStatusStatistics.get(4));

        if (totalDailyActivities == 0) {
            dailyCompletedPercent = 0;
            dailyPendingPercent = 0;
            dailyCancelledPercent = 0;
        } else {
            dailyCompletedPercent = Math.floor(((double) dailyActivitiesCompleted / (double) totalDailyActivities) * 100);
            dailyPendingPercent = Math.floor(((double) dailyActivitiesPending / (double) totalDailyActivities) * 100);
            dailyCancelledPercent = Math.floor(((double) dailyActivitiesCancelled / (double) totalDailyActivities) * 100);
        }

        totalActivitiesCompleted = (activityStatusStatistics.get(2) == null ? 0 : activityStatusStatistics.get(2));
        totalActivitiesPending = (activityStatusStatistics.get(0) == null ? 0 : activityStatusStatistics.get(0)) +
                (activityStatusStatistics.get(1) == null ? 0 : activityStatusStatistics.get(1)) +
                (activityStatusStatistics.get(3) == null ? 0 : activityStatusStatistics.get(3));
        totalActivitiesCancelled = (activityStatusStatistics.get(4) == null ? 0 : activityStatusStatistics.get(4));

        if (totalActivities == 0) {
            totalCompletedPercent = 0;
            totalPendingPercent = 0;
            totalCancelledPercent = 0;
        } else {
            totalCompletedPercent = Math.floor(((double) totalActivitiesCompleted / (double) totalActivities) * 100);
            totalPendingPercent = Math.floor(((double) totalActivitiesPending / (double) totalActivities) * 100);
            totalCancelledPercent = Math.floor(((double) totalActivitiesCancelled / (double) totalActivities) * 100);
        }

        HashMap<Integer, Integer> userActivityTypeStatistics = userService.getUserActivityCategoryStatistics(userId);
        HashMap<Integer, Integer> dailyUserActivityTypeStatistics = userService.getDailyUserActivityCategoryStatistics(userId, userBn.getSelectedDate());

        userActivityTypeStatistics.keySet().forEach(k -> {
            totalUserActivityiesByCategory.put(ActivityCategory.findByValue(k), userActivityTypeStatistics.get(k));
        });

        dailyUserActivityTypeStatistics.keySet().forEach(k -> {
            dailyUserActivityiesByCategory.put(ActivityCategory.findByValue(k), userActivityTypeStatistics.get(k));
        });

        createCategoriesPieChartModel(dailyUserActivityTypeStatistics);
        createStatusPieChartModel(dailyActivityStatusStatistics);

        //For the advice section
        dailyWorkHours = 0;
        dailyGamingHours = 0;
        dailyRestHours = 0;
        doneChores = false;
        introspecting = false;
        socializing = false;

        userActivities.forEach(activityDto -> {
            switch (activityDto.getActivityType().getActivityCategory()) {
                case WORK -> {
                    dailyWorkHours += getHoursBetwenDateTimes(activityDto.getDateStarted(), activityDto.getDateEnded());
                }
                case REST -> {
                    dailyRestHours += getHoursBetwenDateTimes(activityDto.getDateStarted(), activityDto.getDateEnded());
                }
                case GAMING -> {
                    dailyGamingHours += getHoursBetwenDateTimes(activityDto.getDateStarted(), activityDto.getDateEnded());
                }
                case CHORES -> {
                    doneChores = true;
                }
                case INTROSPECTION -> {
                    introspecting = true;
                }
                case SOCIAL, FRIENDS, FAMILY -> {
                    socializing = true;
                }
            }
        });
    }

    private Long getHoursBetwenDateTimes(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return duration.toHours();
    }

    private void createStatusPieChartModel(HashMap<Integer, Integer> statsMap) {
        statusPieChartModel = new PieChartModel();

        for (Integer key : statsMap.keySet()) {
            ActivityStatus status = ActivityStatus.findByValue(key);
            statusPieChartModel.set(status.name(), statsMap.get(key));
        }

        statusPieChartModel.setTitle("Daily Activity Status");
        statusPieChartModel.setLegendPosition("sw");
        statusPieChartModel.setShadow(true);
    }

    private void createCategoriesPieChartModel(HashMap<Integer, Integer> categoriesMap) {
        categoryPieChartModel = new PieChartModel();

        for (Integer key : categoriesMap.keySet()) {
            ActivityCategory category = ActivityCategory.findByValue(key);
            categoryPieChartModel.set(category.name(), categoriesMap.get(key));
        }

        categoryPieChartModel.setTitle("Daily Activity Categories");
        categoryPieChartModel.setLegendPosition("sw");
        categoryPieChartModel.setShadow(true);
    }

    public String getUpcomingHeaderTitle() {
        if (userBn.getSelectedDate().truncatedTo(ChronoUnit.DAYS).isEqual(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))) {
            return "Upcoming Daily Activities";
        } else if (userBn.getSelectedDate().truncatedTo(ChronoUnit.DAYS).isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))) {
            return "Incomplete Past Activities";
        } else {
            return "Upcoming Future Activities";
        }
    }

    public void reload() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPastEvent() {
        return userBn.getSelectedDate().truncatedTo(ChronoUnit.DAYS).isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
    }

    // Getter - Setter

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public int getTotalActivities() {
        return totalActivities;
    }

    public void setTotalActivities(int totalActivities) {
        this.totalActivities = totalActivities;
    }

    public int getTotalDailyActivities() {
        return totalDailyActivities;
    }

    public void setTotalDailyActivities(int totalDailyActivities) {
        this.totalDailyActivities = totalDailyActivities;
    }

    public int getDailyActivitiesCompleted() {
        return dailyActivitiesCompleted;
    }

    public void setDailyActivitiesCompleted(int dailyActivitiesCompleted) {
        this.dailyActivitiesCompleted = dailyActivitiesCompleted;
    }

    public int getDailyActivitiesPending() {
        return dailyActivitiesPending;
    }

    public void setDailyActivitiesPending(int dailyActivitiesPending) {
        this.dailyActivitiesPending = dailyActivitiesPending;
    }

    public int getDailyActivitiesCancelled() {
        return dailyActivitiesCancelled;
    }

    public void setDailyActivitiesCancelled(int dailyActivitiesCancelled) {
        this.dailyActivitiesCancelled = dailyActivitiesCancelled;
    }

    public int getTotalActivitiesCompleted() {
        return totalActivitiesCompleted;
    }

    public void setTotalActivitiesCompleted(int totalActivitiesCompleted) {
        this.totalActivitiesCompleted = totalActivitiesCompleted;
    }

    public int getTotalActivitiesPending() {
        return totalActivitiesPending;
    }

    public void setTotalActivitiesPending(int totalActivitiesPending) {
        this.totalActivitiesPending = totalActivitiesPending;
    }

    public int getTotalActivitiesCancelled() {
        return totalActivitiesCancelled;
    }

    public void setTotalActivitiesCancelled(int totalActivitiesCancelled) {
        this.totalActivitiesCancelled = totalActivitiesCancelled;
    }

    public double getDailyPendingPercent() {
        return dailyPendingPercent;
    }

    public void setDailyPendingPercent(double dailyPendingPercent) {
        this.dailyPendingPercent = dailyPendingPercent;
    }

    public double getDailyCompletedPercent() {
        return dailyCompletedPercent;
    }

    public void setDailyCompletedPercent(double dailyCompletedPercent) {
        this.dailyCompletedPercent = dailyCompletedPercent;
    }

    public double getDailyCancelledPercent() {
        return dailyCancelledPercent;
    }

    public void setDailyCancelledPercent(double dailyCancelledPercent) {
        this.dailyCancelledPercent = dailyCancelledPercent;
    }

    public double getTotalPendingPercent() {
        return totalPendingPercent;
    }

    public void setTotalPendingPercent(double totalPendingPercent) {
        this.totalPendingPercent = totalPendingPercent;
    }

    public double getTotalCompletedPercent() {
        return totalCompletedPercent;
    }

    public void setTotalCompletedPercent(double totalCompletedPercent) {
        this.totalCompletedPercent = totalCompletedPercent;
    }

    public double getTotalCancelledPercent() {
        return totalCancelledPercent;
    }

    public void setTotalCancelledPercent(double totalCancelledPercent) {
        this.totalCancelledPercent = totalCancelledPercent;
    }

    public ActivityDto getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(ActivityDto currentActivity) {
        this.currentActivity = currentActivity;
    }

    public ActivityTypeDto getCurrentActivityType() {
        return currentActivityType;
    }

    public void setCurrentActivityType(ActivityTypeDto currentActivityType) {
        this.currentActivityType = currentActivityType;
    }

    public LocalDateTime getCurrentDate() {
        return userBn.getSelectedDate();
    }

    public void setCurrentDate(LocalDateTime currentDate) {
        userBn.setSelectedDate(currentDate);
    }

    public UserBn getUserBn() {
        return userBn;
    }

    public void setUserBn(UserBn userBn) {
        this.userBn = userBn;
    }

    public List<ActivityDto> getUserActivities() {
        return userActivities;
    }

    public void setUserActivities(List<ActivityDto> userActivities) {
        this.userActivities = userActivities;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    public ActivityTypeService getActivityTypeService() {
        return activityTypeService;
    }

    public void setActivityTypeService(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    public HashMap<ActivityCategory, Integer> getDailyUserActivityiesByCategory() {
        return dailyUserActivityiesByCategory;
    }

    public void setDailyUserActivityiesByCategory(HashMap<ActivityCategory, Integer> dailyUserActivityiesByCategory) {
        this.dailyUserActivityiesByCategory = dailyUserActivityiesByCategory;
    }

    public HashMap<ActivityCategory, Integer> getTotalUserActivityiesByCategory() {
        return totalUserActivityiesByCategory;
    }

    public void setTotalUserActivityiesByCategory(HashMap<ActivityCategory, Integer> totalUserActivityiesByCategory) {
        this.totalUserActivityiesByCategory = totalUserActivityiesByCategory;
    }

    public PieChartModel getStatusPieChartModel() {
        return statusPieChartModel;
    }

    public void setStatusPieChartModel(PieChartModel statusPieChartModel) {
        this.statusPieChartModel = statusPieChartModel;
    }

    public PieChartModel getCategoryPieChartModel() {
        return categoryPieChartModel;
    }

    public void setCategoryPieChartModel(PieChartModel categoryPieChartModel) {
        this.categoryPieChartModel = categoryPieChartModel;
    }

    public void setDailyWorkHours(int dailyWorkHours) {
        this.dailyWorkHours = dailyWorkHours;
    }

    public void setDailyGamingHours(int dailyGamingHours) {
        this.dailyGamingHours = dailyGamingHours;
    }

    public void setDailyRestHours(int dailyRestHours) {
        this.dailyRestHours = dailyRestHours;
    }

    public boolean isIntrospecting() {
        return introspecting;
    }

    public void setIntrospecting(boolean introspecting) {
        this.introspecting = introspecting;
    }

    public void setDailyWorkHours(long dailyWorkHours) {
        this.dailyWorkHours = dailyWorkHours;
    }

    public void setDailyGamingHours(long dailyGamingHours) {
        this.dailyGamingHours = dailyGamingHours;
    }

    public void setDailyRestHours(long dailyRestHours) {
        this.dailyRestHours = dailyRestHours;
    }

    public boolean isPlanActivities() {
        return totalDailyActivities > 0;
    }

    public boolean isGoodDay() {
        return !(isMuchWork() || isMuchGaming() || isLittleSleep()) && (isPlanActivities() && socializing && introspecting && doneChores);
    }

    public boolean isMuchWork() {
        return dailyWorkHours > 8;
    }

    public boolean isMuchGaming() {
        return dailyGamingHours > 4;
    }

    public boolean isLittleSleep() {
        return dailyRestHours < 7;
    }

    public boolean isDoneChores() {
        return doneChores;
    }

    public LocalDateTime getMinDate() {
        return userBn.getSelectedDate().toLocalDate().atStartOfDay();
    }

    public LocalDateTime getMaxDate() {
        return userBn.getSelectedDate().plusDays(1).toLocalDate().atStartOfDay().minusMinutes(1);
    }

    public boolean isSocializing() {
        return socializing;
    }

    public void setSocializing(boolean socializing) {
        this.socializing = socializing;
    }
}
