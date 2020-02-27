package com.example.restApi.UniquoRestaurant.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restApi.UniquoRestaurant.dao.PersonUser;
import com.example.restApi.UniquoRestaurant.entity.Customer;
import com.example.restApi.UniquoRestaurant.entity.Person;
import com.example.restApi.UniquoRestaurant.entity.TableRestaurant;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.CustomerRepository;
import com.example.restApi.UniquoRestaurant.repository.PersonRepository;
import com.example.restApi.UniquoRestaurant.repository.TableRepository;

@RestController
@RequestMapping("/uniquo")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private PersonRepository personRepo;
	
	@Autowired
	private TableRepository tableRepo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/customer/registration")
	public PersonUser addCustomer(@RequestBody Person person)
	{
		Optional<TableRestaurant> tableRest = tableRepo.findById(12);
		Customer customer = new Customer(person, tableRest.get(), "Takeout");
		person.setCustomer(customer);
		Customer cust = customerRepo.save(customer);
		logger.info("custid: {},  orderFood: {}, personId: {}, tableId: {}", cust.getCustomerId(), cust.getOrderFood(), cust.getPersonCustomer(), cust.getTablesRestaurant());
		Optional<Person> person_Cust = personRepo.findById(cust.getPersonCustomer().getPersonId());
		Person personNew = person_Cust.get();
		PersonUser personCust = new PersonUser();
		//tableRepo.save(tableRest.get());
		if(cust.equals(null))
		{	
			personCust.setStatus("FAIL");
			personCust.setMessage("Registration failed");
			personCust.setTimestamp();
		}
		personCust.setStatus("OK");
		personCust.setMessage("Registration OK");
		personCust.setTimestamp();
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
	
	@PutMapping("/customer/bookTable/{tableId}")
	public Customer bookTableCust(@PathVariable int tableId, @RequestBody Person person)
	{
		Optional<TableRestaurant> tableResto = tableRepo.findById(tableId);
		Customer updateCustomer = customerRepo.findByEmailAddress(person.getEmail());
		TableRestaurant table = tableResto.get();
		if(!updateCustomer.equals(null))
		{
			updateCustomer.setTablesRestaurant(tableResto.get());
			updateCustomer.setOrderType("Dine In");
			customerRepo.save(updateCustomer);
			table.setBookingDateTime(new Timestamp(System.currentTimeMillis()));
			tableRepo.save(table);
		}
		else
			throw new UniquoNotFoundException("Table booking is not successful");
		
		return updateCustomer;
	}
	
	@PutMapping("/customer/takeOut/{customerId}")
	public PersonUser takeOutCustomer(@PathVariable int customerId)
	{
		Customer custFetched = customerRepo.updateById(customerId);
		Optional<Person> personFetched = personRepo.findById(custFetched.getPersonCustomer().getPersonId());
		PersonUser personCust = new PersonUser();
		personCust.setStatus("OK");
		personCust.setMessage("Table number for take-out customer is updated");
		personCust.setTimestamp();
		personCust.setPerson(personFetched.get());
		return personCust;
	}
}