package com.example.restApi.UniquoRestaurant.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.restApi.UniquoRestaurant.dao.PersonUser;
import com.example.restApi.UniquoRestaurant.entity.Person;
import com.example.restApi.UniquoRestaurant.exception.EmptyListException;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.PersonRepository;

@RestController
@RequestMapping("/uniquo")
public class PersonController {

	@Autowired
	private PersonRepository personRepo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/person")
	public ResponseEntity<Object> createPerson(@RequestBody Person person)
	{
		Person personSaved = personRepo.save(person);
		URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/id")
					.buildAndExpand(personSaved.getPersonId())
					.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/person")
	public List<Person> getAllPersons()
	{
		List<Person> personList = personRepo.findAll();
		if(personList.isEmpty())
		{
			throw new EmptyListException("The person list is empty");
		}
		return personList;
	}
	
	@GetMapping("/person/{id}")
	public Resource<Person> getSinglePerson(@PathVariable int id)
	{
		Optional<Person> person = personRepo.findById(id);
		if(!person.isPresent())
		{
			throw new UniquoNotFoundException("id: " + id);
		}
		//"all-persons", SERVER_PATH + "/person" 
		 Resource<Person> resource = new Resource<Person>(person.get());
		 ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllPersons());
		 ControllerLinkBuilder linkTo1 = linkTo(methodOn(this.getClass()).getSinglePerson(id));
		 resource.add(linkTo.withRel("all-persons"));
		 resource.add(linkTo1.withRel("single-person"));
		 return resource;
	}
	
	@DeleteMapping("/person/{id}")
	public void deletePerson(@PathVariable int id)
	{
		Optional<Person> personById = personRepo.findById(id);
		if(!personById.isPresent())
		{
			throw new UniquoNotFoundException("id: " +id);
		}
		personRepo.deleteById(id);
	}
	
	@PutMapping("/person/{personId}")
	public Person updatePerson(@PathVariable int personId, @Valid @RequestBody Person person)
	{
		Optional<Person> getPerson = personRepo.findById(personId);
		Person updatePerson=getPerson.get();
		if(getPerson.isPresent())
		{
			updatePerson.setName(person.getName());
			updatePerson.setPhoneNumber(person.getPhoneNumber());
			updatePerson.setEmail(person.getEmail());
			updatePerson.setPassword(person.getPassword());
			personRepo.save(updatePerson);
		}
		else {
			throw new UniquoNotFoundException("id: " + personId);
		}
		return updatePerson;
	}
	
	@GetMapping("/person/login")
	public PersonUser login(@RequestParam String email, @RequestParam String password)
	{
		Person personByEmail = personRepo.findByEmail(email);
		if(personByEmail == null)
		{
			throw new UniquoNotFoundException("Email specified is not found");
		}
		else if(!personByEmail.getPassword().equals(password))
		{
			throw new UniquoNotFoundException("Password not matched");
		}
		Person personFetched = personRepo.findPersonByEmailAndPassword(email, password);
		
		logger.info("email --> {} password --> {} ", email, password);
		if(personFetched.equals(null))
		{
			throw new UniquoNotFoundException("Username and password not specified");
		}
		
		PersonUser personCust = new PersonUser();
		personCust.setStatus("OK");
		personCust.setMessage("Login Successful");
		personCust.setTimestamp();
		personCust.setPerson(personFetched);
		return personCust;
	}
}