package com.gpch.phoneagenda.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gpch.phoneagenda.PhoneAgendaApplication;
import com.gpch.phoneagenda.model.Person;
import com.gpch.phoneagenda.model.Phone;

/**
 * Integration test of the PhoneDAO using H2 memory database.
 * <p>
 * These unit test suite will validate the expected behavior when
 * persistence operations will occur using JDBCTemplate strategy
 * 
 * @author Gustavo Ponce
 * @version 1.0
 * @since   2017-11-16 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("integration")
@ContextConfiguration(classes = PhoneAgendaApplication.class)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) //Other alternative to execute the integration test in DB memory
@Sql({"/db-integration/data.sql"})
public class PhoneDAOTest {

	@Autowired
	private PhoneDAO phoneDAO;
	@Autowired
	private PersonDAO personDAO;
	
	@Test
	@Transactional
	public void testSavePhone() {
		Person person = personDAO.findOne(1L);
		Phone phone = new Phone();
		phone.setPhoneNumber("1234567890");
		phone.setPerson(person);
		long phoneId = phoneDAO.save(phone);
		Phone result = phoneDAO.findOne(phoneId);
		assertEquals("1234567890", result.getPhoneNumber());
	}
	
	@Test
	public void testFindAllPhones() {
		List<Phone> phones = phoneDAO.findAll();
		assertEquals(5, phones.size());
	}
	
	@Test
	public void testFindPhoneByPhoneNumber() {
		Phone phone = phoneDAO.findByPhoneNumber("2574000613");
		assertEquals("2574000613", phone.getPhoneNumber());
	}
	
	@Test(expected = EmptyResultDataAccessException.class)
	@Transactional
	public void testDeletePhoneNumber() {
		Phone phone = phoneDAO.findByPhoneNumber("2574000613");
		assertNotNull(phone);
		phoneDAO.delete("2574000613");
		phone = phoneDAO.findByPhoneNumber("2574000613");
		assertNull(phone);
	}
	
	@Test(expected = EmptyResultDataAccessException.class)
	public void testFindNumberNotFound() {
		phoneDAO.findByPhoneNumber("NUMBER_NO_FOUND");
	}
	
	@Test
	@Transactional
	public void testUpdatePhoneNumber() {
		Phone phone = phoneDAO.findOne(1L);
		phone.setPhoneNumber("12345123");
		phoneDAO.update(phone);
		phone = phoneDAO.findOne(1L);
		assertEquals("12345123", phone.getPhoneNumber());
	}
	
	
}
