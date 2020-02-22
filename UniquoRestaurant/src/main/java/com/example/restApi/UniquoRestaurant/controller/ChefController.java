package com.example.restApi.UniquoRestaurant.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restApi.UniquoRestaurant.dao.PersonUser;
import com.example.restApi.UniquoRestaurant.entity.Chef;
import com.example.restApi.UniquoRestaurant.entity.Person;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.ChefRepository;
import com.example.restApi.UniquoRestaurant.repository.PersonRepository;

@RestController
@RequestMapping("/uniquo")
public class ChefController {

	@Autowired
	private ChefRepository chefRepo;
	
	@Autowired
	private PersonRepository personRepo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/chef/add")
	public PersonUser addChef(@RequestBody Person person)
	{
		Chef chef = new Chef(person);
		person.setChef(chef);
		Chef chefSaved = chefRepo.save(chef);
		logger.info("{}", chefSaved.toString());
		Optional<Person> person_Chef = personRepo.findById(chefSaved.getPersonChef().getPersonId());
		Person personNew = person_Chef.get();
		PersonUser personChef = new PersonUser();
		
		if(chefSaved.equals(null))
		{	
			personChef.setStatus("FAIL");
			personChef.setMessage("Chef registration failed");
			personChef.setTimestamp();
		}
		personChef.setStatus("OK");
		personChef.setMessage("Registration OK");
		personChef.setTimestamp();
		personChef.setPerson(personNew);
		
		return personChef;
	}
	
	@GetMapping("/chef")
	public List<Chef> getChef()
	{
		List<Chef> chefFound = chefRepo.findAll();
		if(chefFound.isEmpty())
		{
			throw new UniquoNotFoundException("There is no chef registered.");
		}
		return chefFound;
	}
}