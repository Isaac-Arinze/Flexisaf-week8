package com.flexisaf.week8.service.impl;

import com.flexisaf.week8.dto.LoginDto;
import com.flexisaf.week8.dto.UserDto;
import com.flexisaf.week8.exception.BadRequestException;
import com.flexisaf.week8.exception.EmailAlreadyExistsException;
import com.flexisaf.week8.exception.ForbiddenException;
import com.flexisaf.week8.exception.ResourceNotFoundException;
import com.flexisaf.week8.model.User;
import com.flexisaf.week8.repository.UserRepository;
import com.flexisaf.week8.service.JwtService;
import com.flexisaf.week8.service.UserService;
import com.flexisaf.week8.util.Helper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        logger.info("Creating user with email: {}", userDto.getEmail());
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists for user");
        }

        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("existingUs", "id", user.getId()));

        existingUser.setFirstname(user.getFirstName());
        existingUser.setMiddleName(user.getMiddleName());
        existingUser.setLastname(user.getLastName());
        existingUser.setContactAddress(user.getContactAddress());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setEmail(user.getEmail());

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(existingUser);
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        // Authenticate the user
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        if (authenticate.isAuthenticated()) {
            // Load the user by email to get the UserDetails object
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());

            // Generate and return JWT token
            return jwtService.generateToken(userDetails);  // Pass UserDetails to generateToken
        }

        throw new BadRequestException("Invalid credentials");
    }


    @Override
    public ResponseEntity<String> logout(Authentication authentication) {
        if (Helper.isEmpty(authentication)) {
            throw new ForbiddenException("Authentication is required");
        }

        Optional<User> user = userRepository.findByEmail(authentication.getName());

        if (user.isEmpty()) {
            throw new BadRequestException("Invalid User");
        }

        User loggedInUser = user.get();
        loggedInUser.setLoggedIn(false);
        loggedInUser.setLastLoggedIn(LocalDateTime.now());
        userRepository.save(loggedInUser);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out");
    }
}
