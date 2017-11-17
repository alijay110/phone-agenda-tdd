package com.gpch.phoneagenda.service;

import com.gpch.phoneagenda.model.Phone;

public interface PhoneService {
	Phone getPhoneById(long personId);
	Phone getPhoneByNumber(String name);
	void deletePhone(long personId);
	void updatePhone(Phone person);
	Phone savePhone(Phone person);
}
