package com.example.fitness_tracker.data.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="user_activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="user_id")
    private long userId;

    @Column(name="activity")
    @Enumerated(EnumType.STRING)
    private com.example.fitness_tracker.data.enums.Activity activity;

    @Column(name="calories_burned")
    private long caloriesBurned;

    @Column(name="distance")
    private long distance;

    @Column(name="intensity")
    private String intensity;

    @Column(name="duration")
    private long duration;

}
