package com.example.fitness_tracker.data.request;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String firstName;
    private String lastName;
    private String sex;
    private long height;
    private long weight;

}
