package com.hoaxify;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hoaxify.shared.GenericResponse;
import com.hoaxify.user.User;
import com.hoaxify.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class UserControllerTest {

	private static final String API_1_0_USERS = "/api/1.0/users";

	@Autowired
	// test rest template for POST request
	TestRestTemplate testRestTemplate;

	@Autowired
	UserRepository userRepository;

	@Before
	public void cleanUp() {
		userRepository.deleteAll();
	}

	// user is sending to controller and checking HTTP response
	@Test
	public void postUser_whenUserIsValid_receiveOk() {

		User user = createValidUser();
		ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	// user is saving into the database
	@Test
	public void postUser_whenUserIsValid_userSavedToDatabase() {

		// checking saving user in database and single user saved count will be one
		User user = createValidUser();
		testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
		assertThat(userRepository.count()).isEqualTo(1);
	}

	// user is saving into the database, receiving success message
	@Test
	public void postUser_whenUserIsValid_receiveSuccessMessage() {

		// checking saving user in database and single user saved count will be one
		User user = createValidUser();
		ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user,
				GenericResponse.class);
		assertThat(response.getBody().getMessage()).isNotNull();
	}

	// saving the user while hashing the password
	@Test
	public void postUser_whenUserIsValid_passwardIsHashedInDatabase() {
		User user = createValidUser();
		testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
		List<User> users = userRepository.findAll();
		User inDB = users.get(0);
		assertThat(inDB.getPassword()).isNotEqualTo(user.getPassword());
	}
	
	private User createValidUser() {
		User user = new User();
		user.setUserName("test-user");
		user.setDisplayName("test-display");
		user.setPassword("P4ssword");
		return user;
	}
}
