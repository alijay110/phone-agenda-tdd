package com.gpch.phoneagenda.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gpch.phoneagenda.model.Person;
import com.gpch.phoneagenda.model.Phone;



/**
 * Integration test of the PhoneRepository using H2 memory database.
 * <p>
 * These unit test suite will validate the expected behavior when
 * persistence operations will occur using JPARepository strategy
 * 
 * @author Gustavo Ponce
 * @version 1.0
 * @since   2017-11-16 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("integration")
@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql({"/db-integration/data.sql"})
public class PhoneRepositoryTest {

	@Autowired
	private PhoneRepository phoneRepository;
	
	@Test
	@Transactional
	public void testSavePhone() {
		Phone phone = new Phone(1L, "1234567890", new Person(1L, "Gustavo", "Ponce"));
		Phone result = phoneRepository.save(phone);
		assertEquals("1234567890",result.getPhoneNumber());
	}
	
	@Test
	@Transactional
	public void testDeletePhone() {
		int initSize = phoneRepository.findAll().size();
		Phone phone = phoneRepository.findOne(1L);
		phoneRepository.delete(phone);
		Phone result = phoneRepository.findOne(1L);
		int endSize = phoneRepository.findAll().size();
		assertNull(result);
		assertEquals(initSize - 1, endSize);
	}
	
	@Test
	public void testFindAllPhones() {
		List<Phone> phones = phoneRepository.findAll();
		assertEquals(5, phones.size());
	}
	
	@Test
	public void testFindPhoneById() {
		Phone phone = phoneRepository.findOne(1L);
		assertEquals(1L, phone.getPhoneId());
	}


	@Test
	public void testFindPhoneByPhoneNumber() {
		Phone phone = phoneRepository.findByPhoneNumber("2574000616");
		assertEquals("2574000616", phone.getPhoneNumber());
	}
	
	@Test
	public void testPhoneNumberNotFound() {
		Phone phone = phoneRepository.findByPhoneNumber("NUMBER_NO_FOUND");
		assertNull(phone);
	}

	@Test
	public void testPhoneNotFoundById() {
		Phone phone = phoneRepository.findOne(-1L);
		assertNull(phone);
	}
	
}
