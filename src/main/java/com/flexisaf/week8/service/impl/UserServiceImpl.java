package com.flexisaf.week8.service.impl;

import com.flexisaf.week8.dto.UserDto;
import com.flexisaf.week8.exception.EmailAlreadyExistsException;
import com.flexisaf.week8.exception.ResourceNotFoundException;
import com.flexisaf.week8.model.User;
import com.flexisaf.week8.repository.UserRepository;
import com.flexisaf.week8.service.JwtService;
import com.flexisaf.week8.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private final UserRepository userRepository;


    private ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        logger.info("Creating user with email: {}", userDto.getEmail());
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Optional <User> optionalUser = userRepository.findByEmail(userDto.getEmail());
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
    public UserDto loginUser(UserDto userDto) {
        Authentication authenticate = authenticationManager
                                    .authenticate(new UsernamePasswordAuthenticationToken(
                                    userDto.getEmail(), userDto.getPassword()
                )
        );


//        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
         if (authenticate.isAuthenticated()){
        return jwtService.generateToken(userDto);

    }
         return null;
    }

}
