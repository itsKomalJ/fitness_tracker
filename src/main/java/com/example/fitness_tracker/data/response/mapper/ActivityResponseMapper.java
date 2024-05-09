package com.example.fitness_tracker.data.response.mapper;

import com.example.fitness_tracker.data.entity.Activity;
import com.example.fitness_tracker.data.response.ActivityResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("activityResponseMapper")
public class ActivityResponseMapper implements Mapper<Activity, ActivityResponse>{

    @Override
    public Activity toEntity(ActivityResponse response){return null;}

    @Override
    public ActivityResponse toPojo(Activity activity){
        ActivityResponse response = ActivityResponse.builder()
                .activity(activity.getActivity().toString())
                .id(activity.getId())
                .userId(activity.getUserId())
                .caloriesBurned(activity.getCaloriesBurned())
                .distance(activity.getDistance())
                .duration(activity.getDuration())
                .intensity(activity.getIntensity())
                .build();

        return response;
    }

}
