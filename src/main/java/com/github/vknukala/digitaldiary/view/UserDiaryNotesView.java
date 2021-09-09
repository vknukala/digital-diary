package com.github.vknukala.digitaldiary.view;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDiaryNotesView {

    private String notes;
    private List<String> attachments = new ArrayList<>();

}
