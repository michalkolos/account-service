package com.michalkolos.account.api.auth.dto;

import com.michalkolos.account.api.security.userStore.User;

public class DtoSignupUserResponse {
	private long id;
	private String name;
	private String lastname;
	private String email;

	public DtoSignupUserResponse() {
	}

	public DtoSignupUserResponse(long id, String name, String lastname, String email) {
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
	}

	public DtoSignupUserResponse(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.lastname = user.getLastName();
		this.email = user.getUsername();
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
