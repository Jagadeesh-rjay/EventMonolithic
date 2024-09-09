package com.rjay.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjay.user.entity.User;
import com.rjay.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User getUserByEmailAndStatus(String emailId, String value) {

		return userRepository.getUserByEmailIdAndStatus(emailId, value);
	}

	public User findByEmailId(String emailId) {

		return userRepository.findByEmailId(emailId);
	}
	
	public List<User> findByTel(Long tel) {

		return userRepository.findByMob(tel);
	}

	public User addUser(User user) {
		return userRepository.save(user);
	}

	public List<User> fetchAll() {
		
		return userRepository.findAll();

	}

	public Boolean deleteById(Long id) {
		userRepository.deleteById(id);
		if (userRepository.findById(id) == null) {
			log.info("user not deleted");
			return false;
		} else {
			log.info("user deleted");
			return true;
		}
	}

	public void save(User user) {
		userRepository.save(user);

	}
	
	public User getbyemail(String email) {
		return userRepository.findByEmailId(email);
	}
	
	public User getUserByEmailIdAndRoleAndStatus(String emailId, String role, String value) {
		return userRepository.findByEmailIdAndRoleAndStatus(emailId, role, value);
	}
	
	public User findById(Long id) {
	    return userRepository.findById(id).orElse(null);
	}
	
	public List<User> getActiveUsers() {
		return userRepository.findAllByStatus("active");
	}

	public int getActiveUsersCount() {
		return getActiveUsers().size();
	}

}