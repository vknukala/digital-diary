package com.github.vknukala.digitaldiary.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateUserDiaryNotesRequest {
    private String notes;
    private List<String> attachments = new ArrayList<>();
}
