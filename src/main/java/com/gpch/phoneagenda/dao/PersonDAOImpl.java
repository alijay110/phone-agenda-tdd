package com.gpch.phoneagenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gpch.phoneagenda.mapper.PersonRowMapper;
import com.gpch.phoneagenda.model.Person;
import com.mysql.jdbc.Statement;

@Repository("personDAO")
public class PersonDAOImpl implements PersonDAO {

	JdbcTemplate jdbcTemplate;

	@Autowired
	public PersonDAOImpl(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public long save(Person person) {
		final String sql = "INSERT into person (name, last_name) values (?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, person.getName());
				preparedStatement.setString(2, person.getLastName());
				return preparedStatement;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public Person findOne(long personId) {
		try {
			final String sql = "SELECT * FROM person WHERE person_id = ?";
			Person person = (Person) jdbcTemplate.queryForObject(sql, new Object[] { personId }, new PersonRowMapper());
			return person;
		} catch (Exception e) {
			throw new EmptyResultDataAccessException(1);
		}
	}

	@Override
	public List<Person> findAll() {
		String sql = "SELECT * FROM person";
		List<Person> persons = new ArrayList<>(jdbcTemplate.query(sql, new Object[] {}, new PersonRowMapper()));
		return persons;
	}

	@Override
	public void delete(long personId) {
		String sqlDeletePhones = "DELETE FROM phone WHERE person_id=?";
		jdbcTemplate.update(sqlDeletePhones, personId);
		String sqlDeletePersons = "DELETE FROM person WHERE person_id=?";
		jdbcTemplate.update(sqlDeletePersons, personId);
	}

	@Override
	public void update(Person person) {
		 String sql = " UPDATE person SET name=?, last_name=? WHERE person_id=?";
		 jdbcTemplate.update(sql, person.getName(), person.getLastName(), person.getPersonId());
	}

}
