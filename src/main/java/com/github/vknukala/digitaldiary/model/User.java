package com.github.vknukala.digitaldiary.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Document(collection = "users")
@Data

@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    //@Pattern(regexp = "")
    private String id;
    @Field("first_name")
    @NotEmpty(message = "First name should not be empty")
    private String firstName;
    @Field("last_name")
    @NotEmpty(message = "Last name should not be empty")
    private String lastName;
    @Field("phone_number")
    @Pattern(regexp="^$|[0-9]{8}")
    private String phoneNumber;
    @Field("email_id")
    @Email(message = "Email should be valid", regexp = "^.+@.+\\..+$")
    private String emailId;
    @Field("address")
    private Address address;
}
