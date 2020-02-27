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
import com.example.restApi.UniquoRestaurant.entity.Cashier;
import com.example.restApi.UniquoRestaurant.entity.Person;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.CashierRepository;
import com.example.restApi.UniquoRestaurant.repository.PersonRepository;

@RestController
@RequestMapping("/uniquo")
public class CashierController {
	
	@Autowired
	private PersonRepository personRepo;
	
	@Autowired
	private CashierRepository cashierRepo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/cashier/add")
	public PersonUser addCashier(@RequestBody Person person)
	{
		Cashier cashier = new Cashier(person);
		person.setCashier(cashier);
		Cashier cashierSaved = cashierRepo.save(cashier);
		logger.info("CashierId: {}, PersonId: {}, Person Name: {}", cashierSaved.getId(), 
				cashierSaved.getPersonCashier().getPersonId(), cashierSaved.getPersonCashier().getName());
		Optional<Person> person_Cashier = personRepo.findById(cashierSaved.getPersonCashier().getPersonId());
		Person personNew = person_Cashier.get();
		PersonUser personCashier = new PersonUser();
		
		if(cashierSaved.equals(null))
		{	
			personCashier.setStatus("FAIL");
			personCashier.setMessage("Cashier registration failed");
			personCashier.setTimestamp();
		}
		personCashier.setStatus("OK");
		personCashier.setMessage("Registration OK");
		personCashier.setTimestamp();
		personCashier.setPerson(personNew);
		
		return personCashier;
	}
	
	@GetMapping("/cashier")
	public List<Cashier> getCashier()
	{
		List<Cashier> cashierFound = cashierRepo.findAll();
		if(cashierFound.isEmpty())
		{
			throw new UniquoNotFoundException("There is no cashier registered.");
		}
		return cashierFound;
	}
}