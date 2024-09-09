package com.rjay.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjay.user.entity.Admin;



@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	Admin findByEmailIdAndRoleAndStatus(String emailId, String role, String status);

	Admin findByEmailId(String emailId);
}