package com.example.fitness_tracker.data.response;

import lombok.Data;

import java.util.List;

@Data
public class ListActivityResponse {

    List<ActivityResponse> data;
    long count;
}
