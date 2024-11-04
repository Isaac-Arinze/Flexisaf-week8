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
public class CustomUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);
//        User> user = userService.findByEmail(username);

        if(Objects.isNull(user)) {
            System.out.println("User not available");
            throw new UsernameNotFoundException("User Not DFound");

        }
        return new CustomUserDetails(user);
    }
}
