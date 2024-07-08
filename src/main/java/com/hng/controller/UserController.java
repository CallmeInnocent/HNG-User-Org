package com.hng.controller;

import com.hng.dto.Data;
import com.hng.dto.UserDetail;
import com.hng.dto.UserLoginResponse;
import com.hng.exception.UserNotFoundException;
import com.hng.service.UserService;
import com.hng.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(UserNotFoundException::new);

        if (!user.getUserId().equals(id)) {
          throw new AccessDeniedException("Access is denied");
        }

        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(user.getUserId());
        userDetail.setFirstName(user.getFirstName());
        userDetail.setLastName(user.getLastName());
        userDetail.setEmail(user.getEmail());
        userDetail.setPhone(user.getPhone());

        Data data = new Data();
        data.setUser(userDetail);

        UserLoginResponse response = new UserLoginResponse();
        response.setStatus("success");
        response.setMessage("User Info fetched successfully");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(userService.findAllUsers());
//    }
}