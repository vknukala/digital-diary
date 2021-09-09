package com.github.vknukala.digitaldiary.view;

import com.github.vknukala.digitaldiary.model.Address;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoView {


    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String emailId;

    private List<Address> addresses = new ArrayList<>();

    private String username;
}
