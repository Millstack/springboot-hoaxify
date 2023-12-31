package com.hoaxify.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String userName;
	
	private String displayName;
	
	private String password;
}
