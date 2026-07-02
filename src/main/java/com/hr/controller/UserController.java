package com.hr.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hr.dto.UserDTO;
import com.hr.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	private final UserService userService;

	@Autowired
	private UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	// Register User
	@PostMapping("/api/auth/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody UserDTO userDto) {
        return userService.registerUser(userDto);
    }
}
