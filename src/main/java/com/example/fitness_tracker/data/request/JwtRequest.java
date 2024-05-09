package com.example.fitness_tracker.data.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {

    @NotNull
    private String userName;

    @NotNull
    private String password;

    @NotNull
    private String role;

}
