package com.example.fitness_tracker.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityResponse {

    private long id;
    private long userId;
    private String activity;
    private long caloriesBurned;
    private long distance;
    private String intensity;
    private long duration;

}
