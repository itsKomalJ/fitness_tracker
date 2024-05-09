package com.example.fitness_tracker.service;

import com.example.fitness_tracker.data.constants.Constants;
import com.example.fitness_tracker.data.entity.Activity;
import com.example.fitness_tracker.data.request.ActivityRequest;
import com.example.fitness_tracker.data.response.ActivityResponse;
import com.example.fitness_tracker.data.response.ListActivityResponse;
import com.example.fitness_tracker.data.response.mapper.ActivityResponseMapper;
import com.example.fitness_tracker.exceptions.ApiException;
import com.example.fitness_tracker.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    @Qualifier("activityResponseMapper")
    private ActivityResponseMapper activityResponseMapper;

    public ActivityResponse createActivity(ActivityRequest activityRequest, long userId){
        Activity activity = new Activity();
        activity.setUserId(userId);
        activity.setActivity(activityRequest.getActivity());
        activity.setDuration(activityRequest.getDuration());
        activity.setCaloriesBurned(activityRequest.getCaloriesBurned());
        if(activityRequest.getDistance()!=0){
            activity.setDistance(activityRequest.getDistance());
        }

        activityRepository.save(activity);

        return activityResponseMapper.toPojo(activity);
    }

    public ActivityResponse updateActivity(ActivityRequest request, long activityId, long userId) throws ApiException {
        Activity activity = activityRepository.findById(activityId).get();
        if(activity.getUserId()!=userId){
            throw new ApiException(Constants.ACCESS_DENIED, HttpStatus.BAD_REQUEST);
        }
        if(request.getActivity()!=null){
            activity.setActivity(request.getActivity());
        }
        if(request.getDuration()!=0){
            activity.setDuration(request.getDuration());
        }
        if(request.getDistance()!=0){
            activity.setDistance(request.getDistance());
        }
        if(request.getCaloriesBurned()!=0){
            activity.setCaloriesBurned(request.getCaloriesBurned());
        }
        if(request.getIntensity()!=null){
            activity.setIntensity(request.getIntensity());
        }

        activityRepository.save(activity);

        return activityResponseMapper.toPojo(activity);
    }

    public ListActivityResponse getAllActivities(long userId, int pageSize,int pageNumber) throws ApiException {
        if(pageNumber <= 0)
        {
            log.error(Constants.PAGE_NUMBER_MUST_NOT_BE_LESS_THAN_1);
            throw new ApiException(Constants.PAGE_NUMBER_MUST_NOT_BE_LESS_THAN_1, HttpStatus.BAD_REQUEST);
        }
        Pageable pageRequest = new PageRequest(pageNumber - 1, pageSize);
        List<Activity> activityList = activityRepository.findAllByUserId(userId, pageRequest).getContent();
        long count = activityRepository.findAllActivityCountByUserId(userId);
        ListActivityResponse response = new ListActivityResponse();
        response.setData(activityResponseMapper.toPojos(activityList));
        response.setCount(count);

        return response;
    }

    public Boolean deleteActivity(long userId, long activityId) throws ApiException {
        Activity activity = activityRepository.findById(activityId).get();
        if(activity.getUserId()!=userId){
            throw new ApiException(Constants.ACCESS_DENIED, HttpStatus.BAD_REQUEST);
        }

        activityRepository.delete(activity);
        return true;
    }

}
