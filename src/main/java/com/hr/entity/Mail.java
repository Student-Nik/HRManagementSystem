package com.hr.entity;

import java.time.LocalDateTime;

import com.hr.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity
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
    
    @Column(columnDefinition = "BOOLEAN")
    private boolean success;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
}
