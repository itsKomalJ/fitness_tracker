package com.example.fitness_tracker.utils;

public interface IResponse<T> {

    T exec() throws Exception;

}
