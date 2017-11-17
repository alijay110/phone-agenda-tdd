package com.gpch.phoneagenda.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gpch.phoneagenda.model.Person;
import com.gpch.phoneagenda.repository.PersonRepository;

@Service("personService")
public class PersonServiceImpl implements PersonService{
	
	private PersonRepository personRepository;
	
	public PersonServiceImpl(PersonRepository personRepository) {
		super();
		this.personRepository = personRepository;
	}

	@Override
	public Person getPersonById(long personId) {
		return personRepository.getOne(personId);
	}
	
	@Override
	public List<Person> getPersonsByName(String name) {
		return personRepository.findByName(name);
	}

	@Override
	public void deletePerson(long personId) {
		personRepository.delete(personId);
	}

	@Override
	public void updatePerson(Person person) {
		personRepository.save(person);
	}

	@Override
	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	@Override
	public List<Person> getAll() {
		return personRepository.findAll();
	}

}
