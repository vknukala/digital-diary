package com.github.vknukala.digitaldiary.service;

import com.github.vknukala.digitaldiary.dto.CreateUserRequest;
import com.github.vknukala.digitaldiary.dto.UpdateUserPasswordRequest;
import com.github.vknukala.digitaldiary.mapper.UserMapper;
import com.github.vknukala.digitaldiary.model.User;
import com.github.vknukala.digitaldiary.repository.UserRepository;
import com.github.vknukala.digitaldiary.view.UserView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService  implements UserDetailsService{


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserView createUser(CreateUserRequest createUserRequest){
        log.info("trying to create the user {}", createUserRequest.getUsername());
        if (userRepository.findByUsername(createUserRequest.getUsername()).isPresent()) {
            throw new ValidationException("Username already exists");
        }

        User user = userMapper.toUser(createUserRequest);
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user = userRepository.insert(user);
        UserView userView = userMapper.toUserView(user);
        log.info("User detals::{}", userView);
        return userView;
    }


    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Retrieve user details for {}", username);
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(
                format("user name - %s not valid",username)));
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        log.info("Total users are {}", users.size());
        return users;
    }

    public UserView updateUser(User user, UpdateUserPasswordRequest updateUserPasswordRequest){
        log.info("trying to create the user {}", user.getUsername());
        if (!(updateUserPasswordRequest.getPassword().equalsIgnoreCase(updateUserPasswordRequest.getReenterPassword()))) {
            throw new ValidationException("Reentered password should match the original password");
        }
        user = userMapper.updateUser(user, updateUserPasswordRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserView userView = userMapper.toUserView(user);
        log.debug("Updated user details::{}", userView);
        return userView;
    }


}
