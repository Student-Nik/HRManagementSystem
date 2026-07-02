package com.hr.entity;

import java.time.LocalDateTime;

import com.hr.enums.UserRole;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;	
	
	private String name;
	private String sender;
	
	@Email(message="Invalid Email Format")
    private String recipient;
	
    private String subject;
    private LocalDateTime sentAt;
    private boolean success;
    
    private UserRole role;
}
