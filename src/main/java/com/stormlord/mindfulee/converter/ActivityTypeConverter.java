package com.stormlord.mindfulee.converter;

import com.stormlord.mindfulee.entity.ActivityType;
import com.stormlord.mindfulee.model.ActivityTypeDto;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Named
@FacesConverter(value = "activityTypeConverter", managed = true)
public class ActivityTypeConverter implements Converter<ActivityTypeDto> {

    // Since jakarta ejbs no longer have a proper @Startup annotation, I will have to use those.
    // I miss Spring.
    public static ConcurrentMap<String, ActivityTypeDto> activityTypeDtoHashMap = new ConcurrentHashMap<>();
    public static ConcurrentMap<String, ActivityType> activityTypeHashMap = new ConcurrentHashMap<>();
    public static ConcurrentMap<String, Integer> activityNameToId = new ConcurrentHashMap<>();
    public static ConcurrentMap<Integer, String> activityIdToName = new ConcurrentHashMap<>();

    @Override
    public ActivityTypeDto getAsObject(FacesContext facesContext, UIComponent uiComponent, String activityName) {
        return activityTypeDtoHashMap.get(activityName);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, ActivityTypeDto activityType) {
        return activityType.getActivityTypeName();
    }

}
