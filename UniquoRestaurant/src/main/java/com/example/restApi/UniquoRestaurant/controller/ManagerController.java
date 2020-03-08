package com.example.restApi.UniquoRestaurant.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restApi.UniquoRestaurant.dao.PersonUser;
import com.example.restApi.UniquoRestaurant.entity.Manager;
import com.example.restApi.UniquoRestaurant.entity.Person;
import com.example.restApi.UniquoRestaurant.repository.FoodItemRepository;
import com.example.restApi.UniquoRestaurant.repository.ManagerRepository;
import com.example.restApi.UniquoRestaurant.repository.PersonRepository;

@RestController
@RequestMapping("/uniquo")
public class ManagerController {
	
	@Autowired
	private PersonRepository personRepo;
	
	@Autowired
	private ManagerRepository managerRepo;
	
	private FoodItemRepository foodItemRepo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/manager")
	public PersonUser addManager(@RequestBody Person person)
	{
		Manager manager = new Manager(person);
		person.setManager(manager);
		Manager managerSaved = managerRepo.save(manager);
		logger.info("{}", managerSaved.toString());
		Optional<Person> person_Manager = personRepo.findById(managerSaved.getPersonManager().getPersonId());
		Person personNew = person_Manager.get();
		PersonUser personManager = new PersonUser();
		
		if(managerSaved.equals(null))
		{	
			personManager.setStatus("FAIL");
			personManager.setMessage("Manager registration failed");
			personManager.setTimestamp();
		}
		personManager.setStatus("OK");
		personManager.setMessage("Registration OK");
		personManager.setTimestamp();
		personManager.setPerson(personNew);
		
		return personManager;
	}
	
}