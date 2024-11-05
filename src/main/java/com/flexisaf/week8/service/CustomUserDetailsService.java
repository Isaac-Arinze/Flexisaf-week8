package com.flexisaf.week8.service;

import com.flexisaf.week8.model.User;
import com.flexisaf.week8.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(username);

        // Check if the user is present
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Return the CustomUserDetails object
        return new CustomUserDetails(user);
    }
}
