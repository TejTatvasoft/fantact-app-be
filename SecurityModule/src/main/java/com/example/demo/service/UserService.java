package com.example.demo.service;

import com.example.demo.dto.request.LoginRequestDto;
import com.example.demo.dto.request.RegisterRequestDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class UserService {

    @Autowired
    UserRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseEntity login(LoginRequestDto loginRequest) {

        int strength = 10;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

        ResponseEntity<Object> unauthroizedResponseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return unauthroizedResponseEntity;
        }
        User user = usersRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            return unauthroizedResponseEntity;
        }

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(jwtTokenProvider.generateToken(authentication));
        }
        return unauthroizedResponseEntity;

    }

    public boolean isEmailValid(String email) {
        return true;
//        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
    }

    public boolean isPasswordValid(String password) {
//        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,10}$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(password);
//        return matcher.matches();
        return true;
    }

    public ResponseEntity register(RegisterRequestDto registerRequest) {

        User registerUser = null;
        int strength = 10;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        if (!(registerRequest.getUsername() != null && registerRequest.getEmail() != null && registerRequest.getPassword() != null)) {
            return ResponseEntity.badRequest().build();
        }

        if (!isEmailValid(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        registerUser = usersRepository.findByEmail(registerRequest.getEmail());
        if (registerUser != null) {
            return ResponseEntity.badRequest().build();
        }

        registerUser = new User();
        registerUser.setEmail(registerRequest.getEmail());
        registerUser.setUserName(registerRequest.getUsername());
        registerUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        usersRepository.save(registerUser);
        return ResponseEntity.ok(registerUser);
    }
}

