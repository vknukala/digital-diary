package com.github.vknukala.digitaldiary.controller;

import com.github.vknukala.digitaldiary.aspect.LogExecutionTime;
import com.github.vknukala.digitaldiary.dto.UpdateUserPasswordRequest;
import com.github.vknukala.digitaldiary.mapper.UserMapper;
import com.github.vknukala.digitaldiary.model.User;
import com.github.vknukala.digitaldiary.service.UserService;
import com.github.vknukala.digitaldiary.view.UserView;
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
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/v1")
@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserView.class)) }),
            @ApiResponse(responseCode = "204", description = "No users found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "The user unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping("/users")
    @LogExecutionTime
    public ResponseEntity<List<UserView>> getUsers(){
        List<User> users = new ArrayList<User>();
        userService.getUsers().forEach(users::add);
        if (users.isEmpty()) {
            log.info("There are no users");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserView> userViews = userMapper.toUserViewList(users);
        return new ResponseEntity<>(userViews, HttpStatus.OK);

    }

    @PatchMapping("/user/{user-name}")
    @Operation(summary = "Update the user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user password",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserView.class)) }),
            @ApiResponse(responseCode = "204", description = "No users found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "The user unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<UserView> updatePassword(@Parameter(description = "The password tp update")
                                                   @PathVariable("user-name") String username,
                                                   @Valid @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest){
        User user = userService.loadUserByUsername(username);
        userService.updateUser(user,updateUserPasswordRequest);
        return new ResponseEntity<UserView>(userMapper.toUserView(user), HttpStatus.OK);
    }

}
