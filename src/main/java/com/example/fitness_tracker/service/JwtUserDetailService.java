package com.example.fitness_tracker.service;

import com.example.fitness_tracker.data.constants.Constants;
import com.example.fitness_tracker.data.entity.User;
import com.example.fitness_tracker.data.enums.Roles;
import com.example.fitness_tracker.data.pojo.UserDetails;
import com.example.fitness_tracker.data.request.JwtRequest;
import com.example.fitness_tracker.data.response.JwtResponse;
import com.example.fitness_tracker.exceptions.ApiException;
import com.example.fitness_tracker.repository.UserRepository;
import com.example.fitness_tracker.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@Component
public class JwtUserDetailService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    public JwtResponse login(JwtRequest authenticationRequest) throws ApiException {
        User user = userService.login(authenticationRequest);
        String token = jwtTokenUtil.generateToken(user.getEmail(),user.getUseRole().name(), user.getId());
        return new JwtResponse(token);
    }

    public UserDetails getUserByRole(String userName, Roles role) throws ApiException {
        User user = userRepository.findByEmail(userName).orElseThrow(()-> new ApiException(Constants.USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        return new UserDetails(user.getEmail(),user.getPassword(),user.getId(),user.getUseRole(),new ArrayList<>());
    }
}
