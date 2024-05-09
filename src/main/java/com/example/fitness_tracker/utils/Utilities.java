package com.example.fitness_tracker.utils;

import com.example.fitness_tracker.data.constants.Constants;
import com.example.fitness_tracker.data.pojo.UserDetails;

import javax.servlet.http.HttpServletRequest;

public class Utilities {

    public static UserDetails getUserDetailsFromRequest(HttpServletRequest request){
        return (UserDetails) request.getAttribute(Constants.USER_OBJECT);
    }

}
