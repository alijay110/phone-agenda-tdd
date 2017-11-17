package com.gpch.phoneagenda.service;

import org.springframework.stereotype.Service;

import com.gpch.phoneagenda.model.Phone;
import com.gpch.phoneagenda.repository.PhoneRepository;

@Service("phoneService")
public class PhoneServiceImpl implements PhoneService{

	private PhoneRepository personRepository;
	
	public PhoneServiceImpl(PhoneRepository personRepository) {
		super();
		this.personRepository = personRepository;
	}
	
	@Override
	public Phone getPhoneById(long phoneId) {
		return personRepository.findOne(phoneId);
	}

	@Override
	public Phone getPhoneByNumber(String phoneNumber) {
		return personRepository.findByPhoneNumber(phoneNumber);
	}

	@Override
	public void deletePhone(long phoneId) {
		personRepository.delete(phoneId);
		
	}

	@Override
	public void updatePhone(Phone phone) {
		personRepository.save(phone);
		
	}

	@Override
	public Phone savePhone(Phone phone) {
		return personRepository.save(phone);
	}

}
