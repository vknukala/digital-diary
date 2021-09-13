package com.github.vknukala.digitaldiary.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserView {

    private String username;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String token;
}
