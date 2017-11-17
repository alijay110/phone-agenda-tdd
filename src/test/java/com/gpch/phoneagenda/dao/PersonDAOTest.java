package com.gpch.phoneagenda.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Integration test of the PersonDAO using H2 memory database.
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
public class PersonDAOTest {

	@Autowired
	private PersonDAO personDAO;
	
	@Test
	@Transactional
	public void testSaveNewPerson() {
		Person person = new Person();
		person.setName("Gustavo");
		person.setLastName("Ponce");
		
		Phone phone1 = new Phone();
		phone1.setPhoneNumber("1234567890");
		Phone phone2 = new Phone();
		phone2.setPhoneNumber("2345678901");
		
		Set<Phone> phones = new HashSet<>();
		phones.add(phone1);
		phones.add(phone2);
		
		person.setPhones(phones);
		long personId = personDAO.save(person);
		Person personSaved = personDAO.findOne(personId);
		assertEquals(personId, personSaved.getPersonId());
	}
	
	@Test 
	public void testFindOneById() {
		final long personId = 3;
		Person person = personDAO.findOne(personId);
		assertEquals(personId, person.getPersonId());

	}
	
	@Test
	public void testFindAllPersons() {
		List<Person> persons =  personDAO.findAll();
		assertEquals(7, persons.size());
	}
	
	@Test
	@Transactional
	public void testDeletePerson() {
		int initSize = personDAO.findAll().size();
		Person person = personDAO.findOne(1L);
		personDAO.delete(person.getPersonId());
		int finalSize = personDAO.findAll().size();
		assertEquals(initSize - 1, finalSize);
	}
	
	@Test(expected = EmptyResultDataAccessException.class)
	public void testPersonNotFound() {
		Person person = personDAO.findOne(-1);
		assertNull(person);
	}
	
	@Test
	@Transactional
	public void testUpdatePerson() {
		Person person = personDAO.findOne(1);
		person.setName("New Name");
		personDAO.update(person);
		person = personDAO.findOne(1);
		assertEquals("New Name", person.getName());
	}
}
