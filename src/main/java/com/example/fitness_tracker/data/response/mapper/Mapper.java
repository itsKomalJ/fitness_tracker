package com.example.fitness_tracker.data.response.mapper;

import com.example.fitness_tracker.exceptions.ApiException;

import java.util.ArrayList;
import java.util.List;

public interface Mapper<Entity, Pojo> {
    Entity toEntity(Pojo pojo);

    Pojo toPojo(Entity entity) throws ApiException;

    default List<Entity> toEntities(List<Pojo> pojos) {

        List<Entity> entities = new ArrayList<>();
        for (Pojo pojo : pojos) {
            entities.add(toEntity(pojo));
        }

        return entities;
    }

    default List<Pojo> toPojos(List<Entity> entities) throws ApiException {
        List<Pojo> pojos = new ArrayList<>();
        for (Entity entity : entities) {
            pojos.add(toPojo(entity));
        }
        return pojos;
    }

}
