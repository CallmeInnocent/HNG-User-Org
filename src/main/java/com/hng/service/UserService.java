package com.hng.service;

import com.hng.dto.UserRegistrationRequestDto;
import com.hng.entity.User;
import com.hng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        user.setEmail(userRegistrationRequestDto.getEmail());
        user.setPhone(userRegistrationRequestDto.getPhone());
        user.setFirstName(userRegistrationRequestDto.getFirstName());
        user.setLastName(userRegistrationRequestDto.getLastName());
        user.setPhone(userRegistrationRequestDto.getPhone());
        return userRepository.save(user);
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }



    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
//
//    public List<User> findAllUsers() {
//        return userRepository.findAll();
//    }
}