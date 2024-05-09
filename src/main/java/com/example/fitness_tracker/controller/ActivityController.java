package com.example.fitness_tracker.controller;

import com.example.fitness_tracker.data.request.ActivityRequest;
import com.example.fitness_tracker.data.request.UpdateUserRequest;
import com.example.fitness_tracker.data.response.ActivityResponse;
import com.example.fitness_tracker.data.response.BaseResponse;
import com.example.fitness_tracker.data.response.ListActivityResponse;
import com.example.fitness_tracker.data.response.UserResponse;
import com.example.fitness_tracker.service.ActivityService;
import com.example.fitness_tracker.utils.ResponseUtil;
import com.example.fitness_tracker.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/activity")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PreAuthorize("hasRole('CONSUMER')")
    @RequestMapping(value = "/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<ActivityResponse>> createActivity(@RequestBody @Valid ActivityRequest activityRequest, HttpServletRequest request){
        ResponseUtil<ActivityResponse> responseUtil = new ResponseUtil<>();
        long id = Utilities.getUserDetailsFromRequest(request).getId();
        return responseUtil.getResponse(()-> activityService.createActivity(activityRequest,id));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @RequestMapping(value = "/activity", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<ActivityResponse>> updateActivity(@RequestBody @Valid ActivityRequest activityRequest,@RequestParam("activityId") long activityId, HttpServletRequest request){
        ResponseUtil<ActivityResponse> responseUtil = new ResponseUtil<>();
        long id = Utilities.getUserDetailsFromRequest(request).getId();
        return responseUtil.getResponse(()-> activityService.updateActivity(activityRequest,activityId,id));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @RequestMapping(value = "/listActivities", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<ListActivityResponse>> listAllActivities(HttpServletRequest request,
                                                                         @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        ResponseUtil<ListActivityResponse> responseUtil = new ResponseUtil<>();
        long id = Utilities.getUserDetailsFromRequest(request).getId();
        return responseUtil.getResponse(()-> activityService.getAllActivities(id,pageSize,pageNumber));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @RequestMapping(value = "/delete", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> deleteActivity(@RequestParam("activityId") long activityId, HttpServletRequest request){
        ResponseUtil<Boolean> responseUtil = new ResponseUtil<>();
        long id = Utilities.getUserDetailsFromRequest(request).getId();
        return responseUtil.getResponse(()-> activityService.deleteActivity(id,activityId));
    }


}
