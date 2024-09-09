package com.rjay.user.dto;

import lombok.Data;

@Data
public class UserLoginResponseJWT extends CommonResponseAPI {
	
	private UserDTO user;
	
	private  EventManagerDTO manager;

	private String jwtToken;

	
}
