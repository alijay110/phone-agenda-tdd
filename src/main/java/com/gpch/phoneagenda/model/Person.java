package com.gpch.phoneagenda.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "person")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "personId")
public class Person {
	@Id
	@GeneratedValue
	@Column(name = "person_id")
	private long personId;
	@Column(name = "name")
	private String name;
	@Column(name = "last_name")
	private String lastName;
	@OneToMany(mappedBy="person", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Phone> phones;
	
	public Person(long personId, String name, String lastName) {
		super();
		this.personId = personId;
		this.name = name;
		this.lastName = lastName;
	}

	public Person() {
		super();
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

}
