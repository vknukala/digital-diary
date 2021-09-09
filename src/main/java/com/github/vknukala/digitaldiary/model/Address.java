package com.github.vknukala.digitaldiary.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Field("type")
    @NonNull()
    private AddressType addressType;
    @Field("address_line_one")
    @NotEmpty(message = "AddressLineOne field should not be empty")
    private String addressLineOne;
    @Field("address_line_two")
    @NotEmpty(message = "AddressLinTwo field should not be empty")
    private String addressLineTwo;
    @Field("postal_code")
    @NotEmpty(message = "Postal code field should not be empty")
    private int postalCode;
    @Field("city")
    @NotEmpty(message = "City field should not be empty")
    private String city;
    @Field("state")
    @NotEmpty(message = "State field should not be empty")
    private String state;


    enum AddressType {
        HOME, OFFICE
    }

}
