package com.hr.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hr.dto.UserDTO;
import com.hr.entity.User;
import com.hr.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final MailService mailService;
	
	@Autowired
	private UserService(UserRepository userRepository, MailService mailService) {
		super();
		this.userRepository = userRepository;
		this.mailService = mailService;
	}
	
	// Register User
	public ResponseEntity<Map<String, Object>> registerUser(UserDTO userDto) {
		
		// Check duplicate email
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "This E-mail already exists, please try with another one!"));
        }
        
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setCreatedAt(LocalDateTime.now());
        
        userRepository.save(user);
        
        mailService.sendAndLogEmail(user.getEmail(), user.getName(), user.getRole());
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                    "message", "User registration successful and email sent!",
                    "Role", user.getRole()
                ));
	}

}
