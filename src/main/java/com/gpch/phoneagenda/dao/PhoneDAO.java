package com.gpch.phoneagenda.dao;

import java.util.List;

import com.gpch.phoneagenda.model.Phone;

public interface PhoneDAO {
	
	long save(Phone phone);
	Phone findOne(long phoneId);
	List<Phone> findAll();
	Phone findByPhoneNumber(String phoneNumber);
	void delete(String phoneNumber);
	void update(Phone phone);

}
