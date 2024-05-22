package com.stormlord.mindfulee.service;

import com.stormlord.mindfulee.converter.ActivityTypeConverter;
import com.stormlord.mindfulee.entity.ActivityType;
import com.stormlord.mindfulee.model.ActivityTypeDto;
import com.stormlord.mindfulee.repository.ActivityTypeRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class ActivityTypeService {

    @Inject
    private ActivityTypeRepository activityTypeRepository;

    public List<ActivityTypeDto> findAll() {
        List<ActivityType> activityTypeList = activityTypeRepository.findAllActivityTypes();

        activityTypeList.forEach(at -> ActivityTypeConverter.activityTypeHashMap.put(at.getActivityTypeName(), at));

        List<ActivityTypeDto> dtoLst = activityTypeList.stream()
                .map(ActivityTypeDto::new)
                .toList();

        dtoLst.forEach(at -> {
                    ActivityTypeConverter.activityTypeDtoHashMap.put(at.getActivityTypeName(), at);
                    ActivityTypeConverter.activityNameToId.put(at.getActivityTypeName(), at.getId());
                    ActivityTypeConverter.activityIdToName.put(at.getId(), at.getActivityTypeName());
                });

        return dtoLst;
    }

    public ActivityType findById(int id) {
        return activityTypeRepository.findActivityType(id);
    }

    public ActivityType findByName(String name) {
        return activityTypeRepository.findyName(name);
    }

    public void createActivityType(ActivityTypeDto activityTypeDto) {
        ActivityType activityType = new ActivityType();
        activityType.setActivityTypeName(activityTypeDto.getActivityTypeName());
        activityType.setActivityCategory(activityTypeDto.getActivityCategory());
        activityTypeRepository.createActivityType(activityType);
    }

    public ActivityType updateActivityType(ActivityTypeDto activityTypeDto) {
        ActivityType activityType = activityTypeRepository.findActivityType(activityTypeDto.getId());
        activityType.setActivityTypeName(activityTypeDto.getActivityTypeName());
        return activityTypeRepository.updateActivityType(activityType);
    }
}
