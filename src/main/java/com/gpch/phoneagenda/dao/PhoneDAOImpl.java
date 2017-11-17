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

import com.gpch.phoneagenda.mapper.PhoneRowMapper;
import com.gpch.phoneagenda.model.Phone;
import com.mysql.jdbc.Statement;

@Repository("phoneDAO")
public class PhoneDAOImpl implements PhoneDAO{

	JdbcTemplate jdbcTemplate;

	@Autowired
	public PhoneDAOImpl(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public long save(Phone phone) {
		final String sql = "INSERT into phone (phone_number, person_id) values (?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, phone.getPhoneNumber());
				preparedStatement.setLong(2, phone.getPerson().getPersonId());
				return preparedStatement;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}


	@Override
	public Phone findOne(long phoneId) {
		try {
			final String sql = "SELECT * FROM phone WHERE phone_id = ?";
			Phone phone = (Phone) jdbcTemplate.queryForObject(sql, new Object[] { phoneId }, new PhoneRowMapper());
			return phone;
		} catch (Exception e) {
			throw new EmptyResultDataAccessException(1);
		}
	}


	@Override
	public List<Phone> findAll() {
		String sql = "SELECT * FROM phone";
		List<Phone> phones = new ArrayList<>(jdbcTemplate.query(sql, new Object[] {}, new PhoneRowMapper()));
		return phones;
	}


	@Override
	public Phone findByPhoneNumber(String phoneNumber) {
		try {
			final String sql = "SELECT * FROM phone WHERE phone_number = ?";
			Phone phone = (Phone) jdbcTemplate.queryForObject(sql, new Object[] { phoneNumber }, new PhoneRowMapper());
			return phone;
		} catch (Exception e) {
			throw new EmptyResultDataAccessException(1);
		}
	}


	@Override
	public void delete(String phoneNumber) {
		String sql = "DELETE FROM phone WHERE phone_number=?";
		jdbcTemplate.update(sql, phoneNumber);
	}


	@Override
	public void update(Phone phone) {
		String sql = " UPDATE phone SET phone_number=? WHERE phone_id=?";
		 jdbcTemplate.update(sql, phone.getPhoneNumber(), phone.getPhoneId());
		
	}

}
