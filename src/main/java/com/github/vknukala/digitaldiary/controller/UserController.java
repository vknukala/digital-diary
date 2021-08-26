package com.github.vknukala.digitaldiary.controller;

import com.github.vknukala.digitaldiary.error.UserNotFoundException;
import com.github.vknukala.digitaldiary.model.Address;
import com.github.vknukala.digitaldiary.model.User;
import com.github.vknukala.digitaldiary.model.UserDiaryNote;
import com.github.vknukala.digitaldiary.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequestMapping("/v1")
@Slf4j
@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Get list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "204", description = "No users found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        try {
            List<User> users = new ArrayList<User>();
            userService.getUsers().forEach(users::add);
            if (users.isEmpty()) {
                log.info("There are no users");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Fetch user details for given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping("/user/{user-id}")
    public ResponseEntity<User> findUserById(@Parameter(description = "id of user to find")
                                             @PathVariable("user-id") String userId){
        Optional<User> user = userService.findByUserId(userId);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }else{
            throw new UserNotFoundException(userId);
        }
    }

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        try {

            userService.saveUser(user);
            log.info("User details {} created successfully ",user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PutMapping("/user/{user-id}")
    public ResponseEntity<User> updateUser(@Parameter(description = "id of user to update") @PathVariable("user-id") String userId,
                                           @Valid @RequestBody User user){
        Optional<User> userOptional = userService.findByUserId(userId);
        if (userOptional.isPresent()) {
            User updatedUser = userOptional.get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            updatedUser.setEmailId(user.getEmailId());

            Address updatedAddress = updatedUser.getAddress();
            updatedAddress.setAddressLineOne(user.getAddress().getAddressLineOne());
            updatedAddress.setAddressLineTwo(user.getAddress().getAddressLineTwo());
            updatedAddress.setPostalCode(user.getAddress().getPostalCode());
            updatedAddress.setCity(user.getAddress().getCity());
            updatedAddress.setState(user.getAddress().getState());

            updatedUser.setAddress(updatedAddress);
            userService.saveUser(updatedUser);

            log.info("User details updated successfully for {}",userId);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            throw new UserNotFoundException(userId);
        }
    }


    @Operation(summary = "Get diary notes for given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched diary notes for given user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDiaryNote.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "204", description = "No diary notes found for the user",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping("/user/{user-id}/diarynotes")
    public ResponseEntity<List<UserDiaryNote>> getUserDiaryNotes(@Parameter(description = "id of user to find diary notes")
                                                                 @PathVariable("user-id") String userId){
        try {
            List<UserDiaryNote> userDiaryNotes = new ArrayList<>();
            userService.getUserDiaryNotes(userId).forEach(userDiaryNotes::add);
            if (userDiaryNotes.isEmpty()) {
                log.info("There are no diary notes for the user {}",userId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userDiaryNotes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Create a user diary notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a diary note",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDiaryNote.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PostMapping("/userdiarynotes")
    public ResponseEntity<UserDiaryNote> createDiaryNotes(@RequestBody UserDiaryNote userDiaryNote){
        String userId = userDiaryNote.getUserId();
        Optional<User> user = userService.findByUserId(userId);

        if (user.isPresent()) {
            userService.createUserDiaryNote(userDiaryNote);
            log.info("Diary notes for user {} created successfully",userId);
            return new ResponseEntity<UserDiaryNote>(userDiaryNote, HttpStatus.CREATED);
        } else {
            throw new UserNotFoundException(userId);
        }
    }




}
