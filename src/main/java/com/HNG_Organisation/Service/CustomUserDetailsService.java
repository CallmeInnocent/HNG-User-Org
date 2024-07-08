package com.HNG_Organisation.Service;

import com.HNG_Organisation.entity.User;
import com.HNG_Organisation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<User> user = userRepository.findByEmail(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            return new User(user.get().getEmail(), user.get().getPassword(), Collections.emptyList());
        }
}
