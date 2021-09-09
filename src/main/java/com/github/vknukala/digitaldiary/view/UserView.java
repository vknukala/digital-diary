package com.github.vknukala.digitaldiary.view;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserView {

    private String username;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String token;
}
