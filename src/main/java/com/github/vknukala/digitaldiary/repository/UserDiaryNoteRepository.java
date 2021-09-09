package com.github.vknukala.digitaldiary.repository;

import com.github.vknukala.digitaldiary.model.UserDiaryNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDiaryNoteRepository extends MongoRepository<UserDiaryNote, String> {
    List<UserDiaryNote> getDiaryNotesByUsername(String username);
}
