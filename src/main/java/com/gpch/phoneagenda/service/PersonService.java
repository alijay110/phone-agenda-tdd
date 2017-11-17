package com.gpch.phoneagenda.service;

import java.util.List;

import com.gpch.phoneagenda.model.Person;

public interface PersonService {
	
	Person getPersonById(long personId);
	List<Person> getPersonsByName(String name);
	List<Person> getAll();
	void deletePerson(long personId);
	void updatePerson(Person person);
	Person savePerson(Person person);

}
