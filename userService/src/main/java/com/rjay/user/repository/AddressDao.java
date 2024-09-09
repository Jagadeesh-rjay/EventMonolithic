package com.rjay.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjay.user.entity.Address;

public interface AddressDao extends JpaRepository<Address, Long> {

}