package com.github.vknukala.digitaldiary.service;

import com.github.vknukala.digitaldiary.model.User;
import com.github.vknukala.digitaldiary.model.UserDiaryNote;
import com.github.vknukala.digitaldiary.repository.UserDiaryNoteRepository;
import com.github.vknukala.digitaldiary.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDiaryNoteRepository userDiaryNoteRepository;

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        log.info("Total users are {}", users.size());
        return users;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findById(userId);
    }

    public UserDiaryNote createUserDiaryNote(UserDiaryNote userDiaryNote) {
        return userDiaryNoteRepository.save(userDiaryNote);
    }

    public List<UserDiaryNote> getUserDiaryNotes(String userId) {
        return userDiaryNoteRepository.getDiaryNotesByUserId(userId);
    }

}
