package com.github.vknukala.digitaldiary.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException{

    private String userId;

}
