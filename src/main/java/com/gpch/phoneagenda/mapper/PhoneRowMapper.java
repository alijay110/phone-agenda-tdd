package com.gpch.phoneagenda.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gpch.phoneagenda.model.Phone;

public class PhoneRowMapper implements RowMapper<Phone> {

	@Override
	public Phone mapRow(ResultSet rs, int arg1) throws SQLException {
		Phone phone = new Phone();
		phone.setPhoneId(rs.getLong("phone_id"));
		phone.setPhoneNumber(rs.getString("phone_number"));
		return phone;
	}

}
