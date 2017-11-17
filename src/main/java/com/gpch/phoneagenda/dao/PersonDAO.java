package com.gpch.phoneagenda.dao;

import java.util.List;

import com.gpch.phoneagenda.model.Person;

public interface PersonDAO {

	public long save(Person person);
	public Person findOne(long personId);
	List<Person> findAll();
	void delete(long personId);
	void update(Person person);
}
