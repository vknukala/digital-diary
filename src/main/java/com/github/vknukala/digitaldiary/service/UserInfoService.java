package com.github.vknukala.digitaldiary.service;

import com.github.vknukala.digitaldiary.model.UserInfo;
import com.github.vknukala.digitaldiary.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {


    private final UserInfoRepository userInfoRepository;

    public List<UserInfo> getUserInformations() {
        List<UserInfo> users = userInfoRepository.findAll();
        log.info("Total users are {}", users.size());
        return users;
    }

    public UserInfo saveUserInfo(UserInfo userinfo) {
        return userInfoRepository.save(userinfo);
    }

    public UserInfo insertUserInfo(UserInfo userinfo) {
        return userInfoRepository.insert(userinfo);
    }

    public Optional<UserInfo> findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

}
