package com.hr.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hr.dto.LoginDTO;
import com.hr.dto.UserDTO;
import com.hr.entity.User;
import com.hr.repository.UserRepository;
import com.hr.security.JwtUtil;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final MailService mailService;
	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, MailService mailService, JwtUtil jwtUtil,
			BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.mailService = mailService;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
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
	
	// Login User
	public ResponseEntity<Map<String, Object>> loginUser(LoginDTO loginDto) {

	    Optional<User> optionalUser = userRepository.findByEmail(loginDto.getEmail());

	    if (optionalUser.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of(
	                        "message", "Invalid email or password"
	                ));
	    }

	    User user = optionalUser.get();

	    // Compare raw password with BCrypt hash
	    if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of(
	                        "message", "Invalid email or password"
	                ));
	    }

	    // Generate JWT
	    String token = jwtUtil.createToken(user.getEmail());

	    return ResponseEntity.ok(Map.of(
	            "message", "Login successful",
	            "token", token,
	            "name", user.getName(),
	            "email", user.getEmail(),
	            "role", user.getRole()
	    ));
	}
	

}
