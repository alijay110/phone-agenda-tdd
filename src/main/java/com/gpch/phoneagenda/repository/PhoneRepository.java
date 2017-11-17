package com.gpch.phoneagenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gpch.phoneagenda.model.Phone;

@Repository("phoneRepository")
public interface PhoneRepository extends JpaRepository<Phone, Long> {
	Phone findByPhoneNumber(String phoneNumber);
}
