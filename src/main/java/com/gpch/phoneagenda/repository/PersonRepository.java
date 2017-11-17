package com.gpch.phoneagenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gpch.phoneagenda.model.Person;

@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long> {
	List<Person> findByName(String name);
	List<Person> findByLastName(String name);
}
