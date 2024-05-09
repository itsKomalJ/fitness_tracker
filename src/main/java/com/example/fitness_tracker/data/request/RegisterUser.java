package com.example.fitness_tracker.data.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterUser {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String sex;

    private long height;

    private long weight;

}
