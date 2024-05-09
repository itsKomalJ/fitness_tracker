package com.example.fitness_tracker.service;

import com.example.fitness_tracker.data.constants.Constants;
import com.example.fitness_tracker.data.entity.User;
import com.example.fitness_tracker.data.enums.Roles;
import com.example.fitness_tracker.data.request.JwtRequest;
import com.example.fitness_tracker.data.request.RegisterUser;
import com.example.fitness_tracker.data.request.UpdateUserRequest;
import com.example.fitness_tracker.data.response.UserResponse;
import com.example.fitness_tracker.data.response.mapper.UserResponseMapper;
import com.example.fitness_tracker.exceptions.ApiException;
import com.example.fitness_tracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userResponseMapper")
    private UserResponseMapper userResponseMapper;

    public User login(JwtRequest jwtRequest) throws ApiException {
        User user = userRepository.findByEmail(jwtRequest.getUserName()).orElseThrow(()-> new ApiException(Constants.USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        String password = jwtRequest.getPassword();
        String salt = user.getSalt();
        if(!getSecurePassword(password,salt).equals(user.getPassword())) {
            throw new ApiException(Constants.INVALID_CREDENTIAL,HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    public UserResponse register(RegisterUser registerUser) throws NoSuchAlgorithmException, NoSuchProviderException, ApiException {
        Optional<User> optionalUser = userRepository.findByEmail(registerUser.getEmail());
        if(optionalUser.isPresent()){
            throw new ApiException(Constants.USER_EXISTS, HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        byte[] salt = getSalt();
        String saltString = bytetoString(salt);
        String securePassword = getSecurePassword(registerUser.getPassword(), saltString);
        user.setEmail(registerUser.getEmail());
        user.setSalt(saltString);
        user.setPassword(securePassword);
        user.setFirstName(registerUser.getFirstName());
        user.setLastName(registerUser.getLastName());
        user.setSex(registerUser.getSex());
        user.setUseRole(Roles.CONSUMER);
        if(registerUser.getHeight()!=0){
            user.setHeight(registerUser.getHeight());
        }
        if(registerUser.getWeight()!=0){
            user.setWeight(registerUser.getWeight());
        }
        userRepository.save(user);

        return userResponseMapper.toPojo(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest, long id) throws ApiException {
        User user = userRepository.findById(id).orElseThrow(()-> new ApiException(Constants.USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        if(updateUserRequest.getFirstName()!=null){
            user.setFirstName(updateUserRequest.getFirstName());
        }
        if(updateUserRequest.getLastName()!=null){
            user.setLastName(updateUserRequest.getLastName());
        }
        if(updateUserRequest.getSex()!=null){
            user.setSex(updateUserRequest.getSex());
        }
        if(updateUserRequest.getWeight()!=0){
            user.setWeight(updateUserRequest.getWeight());
        }
        if(updateUserRequest.getHeight()!=0){
            user.setHeight(updateUserRequest.getHeight());
        }

        return userResponseMapper.toPojo(user);

    }

    public UserResponse profile(long id) throws ApiException {
        User user = userRepository.findById(id).orElseThrow(()-> new ApiException(Constants.USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        return userResponseMapper.toPojo(user);
    }

    private String getSecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {

            byte[] saltBytes = stringToByte(salt);
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(saltBytes);
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());

            generatedPassword = bytetoString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private String bytetoString(byte[] input) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(input);
    }

    private byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);

        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }

    public static  byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[20];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt;
    }

}
