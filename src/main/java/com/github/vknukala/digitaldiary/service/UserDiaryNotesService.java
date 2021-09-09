package com.github.vknukala.digitaldiary.service;

import com.github.vknukala.digitaldiary.model.UserDiaryNote;
import com.github.vknukala.digitaldiary.repository.UserDiaryNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDiaryNotesService {

    private final UserDiaryNoteRepository userDiaryNoteRepository;

    public UserDiaryNote createUserDiaryNote(UserDiaryNote userDiaryNote) {
        return userDiaryNoteRepository.save(userDiaryNote);
    }

    public List<UserDiaryNote> getUserDiaryNotesByUsername(String username) {
        return userDiaryNoteRepository.getDiaryNotesByUsername(username);
    }
}
