package com.github.vknukala.digitaldiary.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequest {

    @NotEmpty(message = "The username must not be empty")
    @Size(min = 6, max = 20 , message = "username must be between 6 ans 2 characters long")
    private String username;
    @NotBlank
    private String password;
}
