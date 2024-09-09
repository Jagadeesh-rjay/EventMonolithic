package com.rjay.user.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rjay.user.entity.Admin;
import com.rjay.user.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Admin getAdminByEmailIdAndRoleAndStatus(String emailId, String role, String status) {
		return adminRepository.findByEmailIdAndRoleAndStatus(emailId, role, status);
	}

	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	public Admin loginAdmin(String emailId, String password) {
		Admin admin = adminRepository.findByEmailId(emailId);
		if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
			return admin;
		}
		return null;
	}

	public Admin getByEmail(String email) {
		return adminRepository.findByEmailId(email);
	}
}