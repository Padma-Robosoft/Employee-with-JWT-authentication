package com.example.Employee_Details.services;

import com.example.Employee_Details.Utils.MailUtil;
import com.example.Employee_Details.dto.LoginResponse;
import com.example.Employee_Details.dto.RegisterResponse;
import com.example.Employee_Details.dto.UserRequest;
import com.example.Employee_Details.dto.UserResponseDTO;
import com.example.Employee_Details.jwt.JwtUtils;
import com.example.Employee_Details.model.User;
import com.example.Employee_Details.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${success.user.created}")
    private String userCreatedMessage;

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${mail.registration.subject}")
    String registrationSubject;

    @Value("${mail.registration.message}")
    String registrationMessage;

    @Value("${mail.login.subject}")
    String loginSubject;

    @Value("${mail.login.message}")
    String loginMessage;

    @Override
    public ResponseEntity<UserResponseDTO<RegisterResponse>> registerUser(UserRequest userRequest) {

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>(new UserResponseDTO<>(-1, 409, "Email already Present", null), HttpStatusCode.valueOf(409));
        }

        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setRole(userRequest.getRole());

        logger.info(userCreatedMessage);
        userRepository.save(newUser);
        mailUtil.sendEmail(newUser, registrationSubject, registrationMessage);

        RegisterResponse response = new RegisterResponse(newUser.getEmail(), newUser.getRole());

        return new ResponseEntity<>(new UserResponseDTO<>(0, 200, userCreatedMessage, response), HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<UserResponseDTO<LoginResponse>> loginUser(UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userRequest.getEmail());
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(new UserResponseDTO<>(-1, 404, "User not found", null), HttpStatusCode.valueOf(404));
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(new UserResponseDTO<>(-1, 401, "Invalid credentials", null), HttpStatusCode.valueOf(401));
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        LoginResponse loginResponse = new LoginResponse(user.getEmail(), user.getRole(),jwtToken);

        mailUtil.sendEmail(user, loginSubject, loginMessage);

        return new ResponseEntity<>(new UserResponseDTO<>(0, 200, userCreatedMessage, loginResponse), HttpStatusCode.valueOf(200));
    }
}
