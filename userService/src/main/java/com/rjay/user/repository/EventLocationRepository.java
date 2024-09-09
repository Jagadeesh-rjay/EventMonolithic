package com.rjay.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjay.user.entity.EventLocation;


@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {
	 List<EventLocation> findByPincode(int pincode);
	}
