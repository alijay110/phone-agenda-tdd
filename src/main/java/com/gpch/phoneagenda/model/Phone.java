package com.gpch.phoneagenda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "phone")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "phoneId")
public class Phone {
	@Id
	@GeneratedValue
	@Column(name = "phone_id")
	private long phoneId;
	@Column(name = "phone_number")
	private String phoneNumber;
	@ManyToOne
    @JoinColumn(name="person_id", nullable = false)
	private Person person;
	
	public Phone() {
		super();
	}

	public Phone(long phoneId, String phoneNumber, Person person) {
		super();
		this.phoneId = phoneId;
		this.phoneNumber = phoneNumber;
		this.person = person;
	}

	public long getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(long phoneId) {
		this.phoneId = phoneId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	

}
