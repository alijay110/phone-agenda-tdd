package com.gpch.phoneagenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gpch.phoneagenda.model.Person;
import com.gpch.phoneagenda.model.Phone;
import com.gpch.phoneagenda.service.PersonService;
import com.gpch.phoneagenda.service.PhoneService;

@RestController
public class AgendaController {
	
    private PersonService personService;
	private PhoneService phoneService;
	
	@Autowired
    public AgendaController(PersonService personService, PhoneService phoneService) {
		super();
		this.personService = personService;
		this.phoneService = phoneService;
	}

	@RequestMapping(value = "/persons", method = RequestMethod.GET)
    public List<Person> getAllPersons() {
        return personService.getAll();
    }
    
    @RequestMapping(value = "/phone", method = RequestMethod.GET)
    public Phone getPhone(@RequestParam("number") String number) {
    	return phoneService.getPhoneByNumber(number);
    }

}
