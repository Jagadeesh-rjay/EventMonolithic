package com.rjay.user.dto;

import com.rjay.user.entity.Admin;

public class AdminLoginResponse {
	private String message;
	private String token;
	private Admin admin;
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	// Getters and Setters
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
