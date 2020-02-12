package com.example.restApi.UniquoRestaurant.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restApi.UniquoRestaurant.dao.PersonCustomer;
import com.example.restApi.UniquoRestaurant.entity.Customer;
import com.example.restApi.UniquoRestaurant.entity.Person;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.CustomerRepository;
import com.example.restApi.UniquoRestaurant.repository.PersonRepository;

@RestController
@RequestMapping("/uniquo")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private PersonRepository personRepo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/customer/registration")
	public PersonCustomer addCustomer(@RequestBody Person person)
	{
		Customer customer = new Customer(person);
		person.setCustomer(customer);
		Customer cust = customerRepo.save(customer);
		logger.info("{}", cust);
		Optional<Person> person_Cust = personRepo.findById(cust.getPersonCustomer().getPersonId());
		Person personNew = person_Cust.get();
		PersonCustomer personCust = new PersonCustomer();
		
		if(cust.equals(null))
		{	
			personCust.setStatus("FAIL");
			personCust.setMessage("Registration failed");
			personCust.setTimestamp(personCust.timeStamp());
		}
		personCust.setStatus("OK");
		personCust.setMessage("Registration OK");
		personCust.setTimestamp(personCust.timeStamp());
		personCust.setPerson(personNew);
		
		return personCust;
	}
	
	@GetMapping("/customers")
	public List<Customer> getCustomer()
	{
		List<Customer> customerFound = customerRepo.findAll();
		if(customerFound.isEmpty())
		{
			throw new UniquoNotFoundException("There is no customer registered.");
		}
		return customerFound;
	}
	
	@GetMapping("/customer/{id}")
	public Resource<Customer> getSingleCustomer(@PathVariable int id)
	{
		Optional<Customer> customer = customerRepo.findById(id);
		if(!customer.isPresent())
		{
			throw new UniquoNotFoundException("id: " + id);
		}
		//"all-customers", SERVER_PATH + "/customer" 
		 Resource<Customer> resource = new Resource<Customer>(customer.get());
		 ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getCustomer());
		 ControllerLinkBuilder linkTo1 = linkTo(methodOn(this.getClass()).getSingleCustomer(id));
		 resource.add(linkTo.withRel("all-customers"));
		 resource.add(linkTo1.withRel("single-customer"));
		 return resource;
	}
	
	@DeleteMapping("/customer/{id}")
	public void deleteCustomer(@PathVariable int id)
	{
		Optional<Customer> customerById = customerRepo.findById(id);
		if(!customerById.isPresent())
		{
			throw new UniquoNotFoundException("id: " +id);
		}
		customerRepo.deleteById(id);
	}
}