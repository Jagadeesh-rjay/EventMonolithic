package com.rjay.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjay.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
	User getUserByEmailIdAndStatus(String emailId, String status);
	
    User findByEmailId(String emailId);
    
    List<User> findByMob(Long tel);
    
    User findByEmailIdAndRoleAndStatus(String emailId, String role, String value);
    
    List<User> findAllByStatus(String status);
}
