package com.gpch.phoneagenda.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gpch.phoneagenda.PhoneAgendaApplication;
import com.gpch.phoneagenda.model.Phone;
import com.gpch.phoneagenda.repository.PhoneRepository;

/**
 * @author Gustavo_Ponce
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("integration")
@ContextConfiguration(classes = PhoneAgendaApplication.class)
public class PhoneServiceTest {
	@Mock
	PhoneRepository phoneRepository;
	@InjectMocks
	PhoneServiceImpl phoneService;
	
	private Phone phone1;
	private Phone phone2;
	private List<Phone> phones;

	@Before
	public void init() {
		phone1 = new Phone();
		phone1.setPhoneId(1L);
		phone1.setPhoneNumber("1234567890");
		
		phone2 = new Phone();
		phone2.setPhoneId(2L);
		phone2.setPhoneNumber("5678901234");
		
		phones = new ArrayList<>();
		phones.add(phone1);
		phones.add(phone2);
	}

	@Test
	public void testSavePhone() {
		when(phoneRepository.save(phone1)).thenReturn(phone1);
		phoneService.savePhone(phone1);
		verify(phoneRepository, times(1)).save(phone1);
	}
	
	@Test
	public void testFindPhoneById() {
		when(phoneRepository.findOne(1L)).thenReturn(phone1);
		Phone result = phoneService.getPhoneById(1L);
		assertEquals(1L, result.getPhoneId());
	}
	
	@Test
	public void testFindPhoneByPhoneNumber() {
		when(phoneRepository.findByPhoneNumber("1234567890")).thenReturn(phone1);
		assertEquals("1234567890", phoneService.getPhoneByNumber("1234567890").getPhoneNumber());
	}
	
	@Test
	public void testDeletePhone() {
		phoneService.deletePhone(1L);
		verify(phoneRepository, times(1)).delete(1L);
	}
	
	@Test
	public void testUpdatePhone() {
		phoneService.updatePhone(phone1);
		verify(phoneRepository, times(1)).save(phone1);
	}

}
