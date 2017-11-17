package com.gpch.phoneagenda.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gpch.phoneagenda.model.Person;

public class PersonRowMapper implements RowMapper<Person> {

	@Override
	public Person mapRow(ResultSet rs, int arg1) throws SQLException {
		Person person = new Person();
		person.setPersonId(rs.getLong("person_id"));
		person.setName(rs.getString("name"));
		person.setLastName(rs.getString("last_name"));
		return person;
	}

}
