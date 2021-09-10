package com.github.vknukala.digitaldiary.controller;

import com.github.vknukala.digitaldiary.dto.CreateUserInfoRequest;
import com.github.vknukala.digitaldiary.error.UserNotFoundException;
import com.github.vknukala.digitaldiary.mapper.UserMapper;
import com.github.vknukala.digitaldiary.model.User;
import com.github.vknukala.digitaldiary.model.UserInfo;
import com.github.vknukala.digitaldiary.service.UserInfoService;
import com.github.vknukala.digitaldiary.service.UserService;
import com.github.vknukala.digitaldiary.view.UserInfoView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/v1/user")
@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UserService userService;
    private final UserMapper userMapper;

    /*@Operation(summary = "Get list of user details ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoView.class)) }),
            @ApiResponse(responseCode = "204", description = "No users found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "The user unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping("/{user-name}/details")
    public ResponseEntity<List<UserInfoView>> getUserInformations(){

        try {
            List<UserInfo> users = new ArrayList<UserInfo>();
            List<UserInfoView> userInfoViews = new ArrayList<UserInfoView>();
            userInfoService.getUserInformations().forEach(users::add);
            if (users.isEmpty()) {
                log.info("There are no users");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            userInfoViews = userMapper.toUserInfoViewList(users);
            return new ResponseEntity<>(userInfoViews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/


    @Operation(summary = "Fetch user details for given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoView.class)) }),
            @ApiResponse(responseCode = "401", description = "The user is unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping(value = "/{user-name}/details")
    public ResponseEntity<UserInfoView> getUserInfo(@Parameter(description = "id of user to find") @PathVariable("user-name") String username){
        Optional<UserInfo> user = userInfoService.findByUsername(username);
        if(user.isPresent()){
            return new ResponseEntity<>(userMapper.toUserInfoView(user.get()),HttpStatus.OK);
        }else{
            throw new UserNotFoundException(username);
        }
    }

    @Operation(summary = "Create a user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoView.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "The user is unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PostMapping("/{user-name}/details")
    public ResponseEntity<UserInfoView> createUserInfo(@Parameter(description = "username to create the user details for")
                                                       @PathVariable ("user-name") String userName,
                                                   @Valid @RequestBody CreateUserInfoRequest createUserInfoRequest){
        try {
            User user = userService.loadUserByUsername(userName);
            UserInfo userInfo = userMapper.toUserInfo(createUserInfoRequest);
            userInfo.setUsername(user.getUsername());
            userInfoService.insertUserInfo(userInfo);
            log.info("User details {} created successfully ",userInfo);
            return new ResponseEntity<UserInfoView>(userMapper.toUserInfoView(userInfo), HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoView.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PutMapping("/{user-name}/details")
    public ResponseEntity<UserInfoView> updateUserInfo(@Parameter(description = "id of user to update") @PathVariable("user-name") String username,
                                               @Valid @RequestBody CreateUserInfoRequest createUserInfoRequest){
        Optional<UserInfo> optionalUserInfo = userInfoService.findByUsername(username);
        if (optionalUserInfo.isPresent()) {
            UserInfo updatedUser = optionalUserInfo.get();
            updatedUser = userMapper.updateUserInfo(updatedUser, createUserInfoRequest);
            userInfoService.saveUserInfo(updatedUser);
            log.info("User details updated successfully for {}",username);
            return new ResponseEntity<>(userMapper.toUserInfoView(updatedUser), HttpStatus.OK);
        } else {
            throw new UserNotFoundException(username);
        }
    }



}
