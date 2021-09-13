package com.github.vknukala.digitaldiary.controller;

import com.github.vknukala.digitaldiary.aspect.LogExecutionTime;
import com.github.vknukala.digitaldiary.config.security.JwtTokenUtil;
import com.github.vknukala.digitaldiary.dto.AuthenticationRequest;
import com.github.vknukala.digitaldiary.dto.CreateUserRequest;
import com.github.vknukala.digitaldiary.mapper.UserMapper;
import com.github.vknukala.digitaldiary.model.User;
import com.github.vknukala.digitaldiary.service.UserService;
import com.github.vknukala.digitaldiary.view.UserView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
@RestController
public class RegisterController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate the given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserView.class)) }),
            @ApiResponse(responseCode = "401", description = "The user is unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @SecurityRequirements
    @LogExecutionTime
    public ResponseEntity<UserView> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        try {
            log.info("Trying to authenticate the user -  {}",request.getUsername());
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = (User) authenticate.getPrincipal();
            UserView userView = userMapper.toUserView(user);
            userView.setToken(jwtTokenUtil.generateAccessToken(user));
            return ResponseEntity.ok().body(userView);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register the given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserView.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @SecurityRequirements
    @LogExecutionTime
    public UserView register(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }
}
