package com.example.fitness_tracker.data.response.mapper;

import com.example.fitness_tracker.data.entity.User;
import com.example.fitness_tracker.data.response.UserResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("userResponseMapper")
public class UserResponseMapper implements Mapper<User, UserResponse>{

    @Override
    public User toEntity(UserResponse userResponse){
        return null;
    }

    @Override
    public UserResponse toPojo(User user){
        UserResponse response = UserResponse.builder()
                .email(user.getEmail())
                .id(user.getId())
                .sex(user.getSex())
                .firstName(user.getFirstName())
                .height(user.getHeight())
                .lastName(user.getLastName())
                .weight(user.getWeight())
                .build();

        return response;
    }

}
