package com.rjay.user.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjay.user.entity.Address;
import com.rjay.user.repository.AddressDao;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;

	@Override
	public Address addAddress(Address address) {
		return addressDao.save(address);
	}
}
