package com.rjay.user.dto;



import com.rjay.user.entity.EventManager;
import com.rjay.user.entity.User;

import lombok.Data;

@Data
public class Responsedto {

	private User user;
	private EventManager buyer;
	private String message;
}
