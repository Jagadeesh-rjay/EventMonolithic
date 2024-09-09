package com.rjay.user.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjay.user.dto.AdminLoginRequest;
import com.rjay.user.dto.AdminLoginResponse;
import com.rjay.user.entity.Admin;
import com.rjay.user.entity.EventManager;
import com.rjay.user.entity.User;
import com.rjay.user.service.AdminService;
import com.rjay.user.service.EventManagerService;
import com.rjay.user.service.EventService;
import com.rjay.user.service.UserService;
import com.rjay.user.utility.JwtUtils;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final UserService userService;
	private final EventManagerService eventManagerService;
	private final AdminService adminService;
	private final JwtUtils jwtUtils;
	private final MeterRegistry meterRegistry;
	private final EventService eventService;
	

	public AdminController(UserService userService, EventManagerService eventManagerService, AdminService adminService,
			JwtUtils jwtUtils, MeterRegistry meterRegistry, EventService eventService) {
		super();
		this.userService = userService;
		this.eventManagerService = eventManagerService;
		this.adminService = adminService;
		this.jwtUtils = jwtUtils;
		this.meterRegistry = meterRegistry;
		this.eventService = eventService;
		
	}

	@PostMapping("/login")
	@Timed(value = "admin.login", description = "Time taken to login as admin")
	public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest adminLoginRequest) {
		AdminLoginResponse response = new AdminLoginResponse();
		try {
			Admin admin = adminService.loginAdmin(adminLoginRequest.getEmailId(), adminLoginRequest.getPassword());
			if (admin != null) {
				String token = jwtUtils.generateToken(admin.getEmailId());
				response.setMessage("Login successful");
				response.setToken(token);
				meterRegistry.counter("admin.login.success").increment();
				return ResponseEntity.ok(response);
			} else {
				response.setMessage("Invalid credentials");
				meterRegistry.counter("admin.login.failure").increment();
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		} catch (Exception e) {
			response.setMessage("Login failed: " + e.getMessage());
			meterRegistry.counter("admin.login.failure").increment();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/user")
	@Timed(value = "admin.getAllUsers", description = "Time taken to get all active Users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> user = userService.getActiveUsers();
		return ResponseEntity.ok(user);
	}

	@GetMapping("/Manager")
	@Timed(value = "admin.getAllManagers", description = "Time taken to get all active Managers")
	public ResponseEntity<List<EventManager>> getAllManagers() {
		List<EventManager> manager = eventManagerService.getActiveManagers();
		return ResponseEntity.ok(manager);
	}

	@GetMapping("/totalEvents")
	@Timed(value = "admin.getTotalEvents", description = "Time taken to get total events")
	public ResponseEntity<Integer> getTotalEvents() {
		int totalEvents = eventService.getTotalEvents();
		return ResponseEntity.ok(totalEvents);
	}

}
