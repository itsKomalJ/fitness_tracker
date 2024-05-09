package com.example.fitness_tracker.controller;

import com.example.fitness_tracker.data.request.JwtRequest;
import com.example.fitness_tracker.data.request.RegisterUser;
import com.example.fitness_tracker.data.request.UpdateUserRequest;
import com.example.fitness_tracker.data.response.BaseResponse;
import com.example.fitness_tracker.data.response.JwtResponse;
import com.example.fitness_tracker.data.response.UserResponse;
import com.example.fitness_tracker.service.JwtUserDetailService;
import com.example.fitness_tracker.service.UserService;
import com.example.fitness_tracker.utils.ResponseUtil;
import com.example.fitness_tracker.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
public class UserController {

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse<JwtResponse>> login(@RequestBody JwtRequest authenticationRequest) throws Exception {
        ResponseUtil<JwtResponse> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()-> jwtUserDetailService.login(authenticationRequest));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserResponse>> createUser(@RequestBody @Valid RegisterUser registerUser){
        ResponseUtil<UserResponse> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()-> userService.register(registerUser));

    }

    @PreAuthorize("hasRole('CONSUMER')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest, HttpServletRequest request){
        ResponseUtil<UserResponse> responseUtil = new ResponseUtil<>();
        long id = Utilities.getUserDetailsFromRequest(request).getId();
        return responseUtil.getResponse(()-> userService.updateUser(updateUserRequest, id));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @RequestMapping(value = "/profile", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserResponse>> getUserProfile( HttpServletRequest request){
        ResponseUtil<UserResponse> responseUtil = new ResponseUtil<>();
        long id = Utilities.getUserDetailsFromRequest(request).getId();
        return responseUtil.getResponse(()-> userService.profile(id));
    }

}
