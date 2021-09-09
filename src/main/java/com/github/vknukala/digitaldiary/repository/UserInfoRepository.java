package com.github.vknukala.digitaldiary.repository;

import com.github.vknukala.digitaldiary.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

    Optional<UserInfo> findByUsername(String username);

}
