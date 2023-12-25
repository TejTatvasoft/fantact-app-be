package com.example.demo.controller;

import com.example.demo.dto.request.RegisterRequestDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.LoginRequestDto;
import com.example.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDto loginRequest) {
		return userService.login(loginRequest);
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody RegisterRequestDto request) {
		return userService.register(request);

	}

	@GetMapping("/all")
	public List<User> all() {
		return userRepository.findAll();

	}
}
