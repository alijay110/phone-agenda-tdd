package com.gpch.phoneagenda.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gpch.phoneagenda.PhoneAgendaApplication;
import com.gpch.phoneagenda.model.Person;
import com.gpch.phoneagenda.repository.PersonRepository;

/**
 * @author Gustavo_Ponce
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("integration")
@ContextConfiguration(classes = PhoneAgendaApplication.class)
public class PersonServiceTest {

	@Mock
	PersonRepository personRepository;
	@InjectMocks
	PersonServiceImpl personService;
	
	private Person person1;
	private Person person2;
	private List<Person> persons;

	@Before
	public void init() {
		person1 = new Person();
		person1.setPersonId(1L);
		person1.setName("Gustavo");
		person1.setLastName("Ponce");
		
		person2 = new Person();
		person2.setPersonId(2L);
		person2.setName("Gustavo");
		person2.setLastName("Perez");
		
		persons = new ArrayList<>();
		persons.add(person1);
		persons.add(person2);
	}

	@Test
	public void testSavePerson() {
		when(personRepository.save(person1)).thenReturn(person1);
		personService.savePerson(person1);
		verify(personRepository, times(1)).save(person1);
	}
	
	@Test
	public void testFindPersonById() {
		when(personRepository.getOne(1L)).thenReturn(person1);
		Person result = personService.getPersonById(1L);
		assertEquals(1L, result.getPersonId());
	}
	
	@Test
	public void testFindPersonByName() {
		when(personRepository.findByName("Gustavo")).thenReturn(persons);
		List<Person> persons = personService.getPersonsByName("Gustavo");
		for (Person person : persons) {
			assertEquals("Gustavo", person.getName());
		}
	}
	
	@Test
	public void testDeletePerson() {
		personService.deletePerson(1L);
		verify(personRepository, times(1)).delete(1L);
	}
	
	@Test
	public void testUpdatePerson() {
		personService.updatePerson(person1);
		verify(personRepository, times(1)).save(person1);
	}


}
