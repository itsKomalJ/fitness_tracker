package com.example.fitness_tracker.repository;

import com.example.fitness_tracker.data.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {

    Optional<Activity> findById(long id);

    Page<Activity> findAllByUserId(long userId, Pageable page);

    @Query("SELECT COUNT(a) FROM Activity a WHERE a.userId=userId")
    long findAllActivityCountByUserId(@Param("userId")long userId);

}
