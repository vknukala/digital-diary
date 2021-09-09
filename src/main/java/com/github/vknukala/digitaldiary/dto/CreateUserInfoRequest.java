package com.github.vknukala.digitaldiary.dto;

import com.github.vknukala.digitaldiary.model.Address;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateUserInfoRequest {

    @NotEmpty(message = "firstname cannot be empty")
    private String firstName;
    @NotEmpty(message = "lastname cannot be empty")
    private String lastName;
    @Pattern(regexp="^$|[0-9]{8}")
    private String phoneNumber;
    @Email(message = "Email should be valid", regexp = "^.+@.+\\..+$")
    private String emailId;
    private List<Address> addresses = new ArrayList<>();
}
