package com.example.restApi.UniquoRestaurant.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.restApi.UniquoRestaurant.dao.WaitingTime;
import com.example.restApi.UniquoRestaurant.entity.TableRestaurant;
import com.example.restApi.UniquoRestaurant.exception.EmptyListException;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.TableRepository;

/**
 * @author manojagarwal
 *
 */
@RestController
@RequestMapping("/uniquo")
public class TableController {
	
	@Autowired
	private TableRepository tableRepo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/tables")
	public List<TableRestaurant> getAllTables()
	{
		List<TableRestaurant> tableList = tableRepo.findAll();
		if(tableList.isEmpty())
		{
			throw new EmptyListException("The tables list is empty");
		}
		return tableList;
	}
	
	@GetMapping("/table/{tableId}")
	public TableRestaurant getSingleTable(@PathVariable int tableId)
	{
		Optional<TableRestaurant> tableFound = tableRepo.findById(tableId);
		if(!tableFound.isPresent())
		{
			throw new UniquoNotFoundException("id: " +tableId);
		}
		TableRestaurant table = tableFound.get();
		return table;
	}
	
	@PostMapping("/table")
	public ResponseEntity<Object> createTable(@RequestBody TableRestaurant table)
	{
		TableRestaurant tableSaved = tableRepo.save(table);
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/id")
		.buildAndExpand(tableSaved.getTableId())
		.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/table/{tableId}")
	public void removeTable(@PathVariable int tableId)
	{
		Optional<TableRestaurant> tableById = tableRepo.findById(tableId);
		if(!tableById.isPresent())
		{
			throw new UniquoNotFoundException("id: " +tableId);
		}
		tableRepo.deleteById(tableId);
	}
	
	@PutMapping("/table/{tableId}")
	public TableRestaurant updateFoodItem(@PathVariable int tableId, @RequestBody TableRestaurant table)
	{
		Optional<TableRestaurant> tableFetched = tableRepo.findById(tableId);
		TableRestaurant updateTable = tableFetched.get();
		if(tableFetched.isPresent())
		{
			updateTable.setBookingDateTime(table.getBookingDateTime());
			updateTable.setStartDateTime(table.getStartDateTime());
			updateTable.setEndDateTime(table.getEndDateTime());
			tableRepo.save(updateTable);
		}
		else
			throw new UniquoNotFoundException("id: " + tableId);
		
		return updateTable;
	}
	
	/* 
	 * Method - getAvailability() 
	 * To return the least waiting time in the restaurant for a table 
	 * */
	
	@GetMapping("/table/checkAvailability")
	public WaitingTime getAvailability()
	{
		TableRestaurant availableTime = tableRepo.checkTableAvailability();
		if(availableTime.equals(null))
		{
			throw new UniquoNotFoundException("The waiting time cannot be calculated");
		}
		int tableNumber = availableTime.getTableId();
		logger.info("{}", tableNumber);
		int waiting_Time = availableTime.getWaitingTime();
		logger.info("{}", waiting_Time);
		WaitingTime waitingTime = new WaitingTime(tableNumber, waiting_Time);
		return waitingTime;
	}
	
	/* 
	 * Method - getAvailabilityList() 
	 * To return the list of waiting time in the restaurant for a table
	 * */
	
	@GetMapping("/table/checkAvailabilityList")
	public List<TableRestaurant> getAvailabilityList()
	{
		List<TableRestaurant> availableTime = tableRepo.checkTableAvailabilityList();
		if(availableTime.equals(null))
		{
			throw new UniquoNotFoundException("The waiting time cannot be calculated");
		}
		return availableTime;
	}
}