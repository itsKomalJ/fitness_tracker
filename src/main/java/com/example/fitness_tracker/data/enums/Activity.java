package com.example.fitness_tracker.data.enums;

public enum Activity {

    RUNNING("Running"),
    WALKING("Walking"),
    SWIMMING("Swimming"),
    STRENGTH_TRAINING("Strength Training"),
    CYCLING("Cycling"),
    CARDIO("Cardio"),
    ATHLETICS("Athletics");

    private String name;

    Activity(String name){
        this.name=name;
    }

}
