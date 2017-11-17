package com.gpch.phoneagenda.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
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
 * Integration test of the PersonRepository using H2 memory database.
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
public class PersonRepositoryTest {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Test
	@Transactional
	public void testSavePerson() {
		Person person = new Person(1L, "Gustavo", "Ponce");
		Phone phone = new Phone(1L, "1234567890", person);
		person.setPhones(new HashSet<Phone>(Arrays.asList(phone)));

		Person result = personRepository.save(person);
		assertEquals("Gustavo",result.getName());

	}
	
	@Test
	@Transactional
	public void testDeletePerson() {
		int initSize = personRepository.findAll().size();
		Person person = personRepository.findOne(1L);
		personRepository.delete(person);
		Person result = personRepository.findOne(1L);
		int endSize = personRepository.findAll().size();
		assertNull(result);
		assertEquals(initSize - 1, endSize);
	}
	
	@Test
	public void testFindAllPersons() {
		List<Person> persons = personRepository.findAll();
		assertEquals(7, persons.size());
	}
	
	@Test
	public void testFindPersonById() {
		Person person = personRepository.findOne(1L);
		assertEquals(1L, person.getPersonId());
	}


	@Test
	public void testFindPersonByName() {
		List<Person> persons = personRepository.findByName("Tedie");
		boolean sameName = true;
		for (Person person : persons) {
			if(!person.getName().equals("Tedie")) {
				sameName = false;
				break;
			}
		}
		assertTrue(sameName);
	}
	
	@Test
	public void testFindPersonByNameNotFound() {
		List<Person> persons = personRepository.findByName("NO NAME");
		assertEquals(0, persons.size());
	}
	
	@Test
	public void testFindPersonByLastName() {
		List<Person> persons = personRepository.findByLastName("Kounias");
		boolean sameLastName = true;
		for (Person person : persons) {
			if(!person.getLastName().equals("Kounias")) {
				sameLastName = false;
				break;
			}
		}
		assertTrue(sameLastName);
	}
	
	@Test
	public void testFindPersonByLastNameNotFound() {
		List<Person> persons = personRepository.findByLastName("NO LAST_NAME");
		assertEquals(0, persons.size());
	}

	
	@Test
	public void testPersonNotFound() {
		Person person = personRepository.findOne(-1L);
		assertNull(person);
	}
}
