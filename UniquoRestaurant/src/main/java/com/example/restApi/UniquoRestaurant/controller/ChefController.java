package com.example.restApi.UniquoRestaurant.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.restApi.UniquoRestaurant.entity.Chef;
import com.example.restApi.UniquoRestaurant.entity.OrderFood;
import com.example.restApi.UniquoRestaurant.entity.Person;
import com.example.restApi.UniquoRestaurant.entity.TableRestaurant;
import com.example.restApi.UniquoRestaurant.exception.EmptyListException;
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
	
	@PostMapping("/chef")
	public ResponseEntity<Object> addChef(@RequestBody Person person)
	{
		Person personFetched  = personRepo.save(person);
		Chef chefSaved = new Chef(personFetched);
		logger.info("{}", chefSaved);
		chefRepo.save(chefSaved);
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/id")
		.buildAndExpand(chefSaved.getChefId())
		.toUri();
		
		return ResponseEntity.created(location).build();
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