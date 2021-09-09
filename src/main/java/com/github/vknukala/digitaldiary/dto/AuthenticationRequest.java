package com.github.vknukala.digitaldiary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotEmpty(message = "The username must not be empty")
    @Size(min = 6, max = 20 , message = "username must be between 6 ans 2 characters long")
    private String username;
    @NotBlank
    private String password;
}
