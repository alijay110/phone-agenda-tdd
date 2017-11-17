package com.gpch.phoneagenda.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.gpch.phoneagenda.PhoneAgendaApplication;
import com.gpch.phoneagenda.model.Person;
import com.gpch.phoneagenda.model.Phone;
import com.gpch.phoneagenda.service.PersonService;
import com.gpch.phoneagenda.service.PhoneService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PhoneAgendaApplication.class })
public class AgendaControllerTest {
	
	@Mock
	private PersonService personService;
	@Mock
	private PhoneService phoneService;

	@InjectMocks
    private AgendaController agendaController;
	
	private MockMvc mockMvc;
	
	private Person person1 = null;
	private Person person2 = null;
	private List<Person> persons = null;
	
	private Phone phone1 = null;
	private Phone phone2 = null;
	private List<Phone> phones = null;
	
	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(agendaController)
                .build();
        
        //Sample data
        person1 = new Person(1L, "Daenerys", "Targaryen");
        person2 = new Person(2L, "John", "Snow");
        phone1 = new Phone(1L, "0123456789", person1);
        phone2 = new Phone(2L, "9876543210", person2);
        person1.setPhones(new HashSet<Phone>(Arrays.asList(phone1)));
        person2.setPhones(new HashSet<Phone>(Arrays.asList(phone2)));
        persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        phones = new ArrayList<>();
        phones.add(phone1);
        phones.add(phone2);
        
    }

	@Test
	public void findAll_PersonsFound_ShouldReturnFoundPersonEntries() throws Exception {
		when(personService.getAll()).thenReturn(persons);
		mockMvc.perform(get("/persons"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].personId", equalTo(1)))
			.andExpect(jsonPath("$[0].name", equalTo("Daenerys")))
			.andExpect(jsonPath("$[0].lastName", equalTo("Targaryen")))
			.andExpect(jsonPath("$[0].phones[0].phoneId", equalTo(1)))
			.andExpect(jsonPath("$[0].phones[0].phoneNumber", equalTo("0123456789")));
		verify(personService, times(1)).getAll();
		verifyNoMoreInteractions(personService);
	}
	
	
	@Test
	public void findPhoneNumber_PhoneNumberFound_ShouldReturnPhoneNumber() throws Exception {
		when(phoneService.getPhoneByNumber("0123456789")).thenReturn(phone1);
		mockMvc.perform(get("/phone?number=0123456789"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$['phoneId']", equalTo(1)))
			.andExpect(jsonPath("$['phoneNumber']", equalTo("0123456789")))
			.andExpect(jsonPath("$['person']['personId']", equalTo(1)))
			.andExpect(jsonPath("$['person']['name']", equalTo("Daenerys")))
			.andExpect(jsonPath("$['person']['lastName']", equalTo("Targaryen")));
		verify(phoneService, times(1)).getPhoneByNumber("0123456789");
		verifyNoMoreInteractions(phoneService);
	}
}
