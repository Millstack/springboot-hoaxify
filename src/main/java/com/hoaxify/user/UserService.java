package com.hoaxify.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	UserRepository userRepository;

	// using BCrypt for hashing the passwords for database
	BCryptPasswordEncoder passwordEncoder;

	// constructor injection
	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	// save user with password encoder
	public User saveUser(User user) {
		//to initialize the passwordEncoder, when Security COnfiguration is excluded from main class
		// using the configuration class with bean and using ComponentScan for scanning base package
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}
