package com.example.fitness_tracker.data.request;

import com.example.fitness_tracker.data.enums.Activity;
import lombok.Data;

@Data
public class ActivityRequest {

    private Activity activity;
    private long caloriesBurned;
    private long duration;
    private long distance;
    private String intensity;

}
