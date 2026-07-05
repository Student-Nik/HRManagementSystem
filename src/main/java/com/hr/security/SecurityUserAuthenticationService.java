package com.hr.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.hr.entity.User;
import com.hr.repository.UserRepository;

@Component
public class SecurityUserAuthenticationService implements UserDetailsService{
	
	private final UserRepository userRepository;

	private SecurityUserAuthenticationService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
		           .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		return new EndUserDetails(user);
	}

}
