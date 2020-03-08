package com.example.restApi.UniquoRestaurant.controller;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.restApi.UniquoRestaurant.exception.ExceptionResponse;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.CustomerRepository;
import com.example.restApi.UniquoRestaurant.repository.OrderFoodRepository;
import com.example.restApi.UniquoRestaurant.repository.TableRepository;

/**
 * @author nishchay
 *
 */
@RestController
@RequestMapping("/uniquo")
public class TableController {
	
	@Autowired
	private TableRepository tableRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private OrderFoodRepository orderFoodRepo;
	
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
		TableRestaurant tableRest = new TableRestaurant(table.getId(), table.getBookingDateTime(), table.getStartDateTime(), table.getEndDateTime());
		TableRestaurant tableSaved = tableRepo.save(table);
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/id")
		.buildAndExpand(tableSaved.getId())
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
	 * Method - updateWaitingTime 
	 * Update the waiting time in the database 
	 * */
	
	@GetMapping("/table/updateWaitingTime")
	public ResponseEntity<Object> updateWaitingTime() throws ParseException
	{
		List<TableRestaurant> allTablesUpdate = tableRepo.findAll();
		for (TableRestaurant tableReceived : allTablesUpdate) {
			logger.info("tableIdOutdsideIf: {}", tableReceived.getId());
			if(tableReceived.getId() == 11 || tableReceived.getId() == 12)
			{
				continue;
			}
			else
			{
				logger.info("tableIdInsideElse: {}", tableReceived.getId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String endDate = sdf.format(tableReceived.getEndDateTime());
				Date todayDate = new Date();
				try {
					Date endDateTime = sdf.parse(endDate);
	
					if (endDateTime.compareTo(todayDate) < 0) 
					{ // if end date time is less than
						logger.info("Inside date less than condition: {}", tableReceived.getId());
						/*
						 * int num = orderFoodRepo.setExistingOrderByTable(tableReceived.getId());
						 * if(num == 1) {
						 * logger.info("Existing order in table order food is set to zero as the table "
						 * + "end time has passed the current time"); }
						 */
						tableRepo.setCurrentDateTime(tableReceived.getId());
						//customerRepo.updateByTableId(tableReceived.getId());
					} 
					else if (endDateTime.compareTo(todayDate) == 0) 
					{ // both date are same
						logger.info("Inside both date same condition: {}", tableReceived.getId());
						if (endDateTime.getTime() == todayDate.getTime() 
								|| endDateTime.getTime() < todayDate.getTime()) 
						{ // expired
							tableRepo.setCurrentDateTime(tableReceived.getId());
							//customerRepo.updateByTableId(tableReceived.getId());
							/*
							 * int num = orderFoodRepo.setExistingOrderByTable(tableReceived.getId());
							 * if(num == 1) {
							 * logger.info("Existing order in table order food is set to zero as the table "
							 * + "end time has passed the current time"); }
							 */
						}
					} 
					else 
					{
						continue;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				tableRepo.save(tableReceived);
			}
		}
		int updateWaitingTime = tableRepo.updateWaitingTime();
		logger.info("updateWaitingTime: {}", updateWaitingTime);
		if(!(updateWaitingTime >= 0))
		{
			throw new UniquoNotFoundException("Update unsuccessful: " + updateWaitingTime);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.OK).body(new ExceptionResponse(new Timestamp(System.currentTimeMillis()), "Update Successful", "", "OK"));
		}
	}	
	
	/* 
	 * Method - getAvailability() 
	 * To return the least waiting time in the restaurant for a table 
	 * */
	
	@GetMapping("/table/checkAvailability")
	public WaitingTime getAvailability() throws ParseException
	{

			//tableRepo.save(tableRepo.checkTableAvailability());
			TableRestaurant availableTime = tableRepo.checkTableAvailability();
			
			logger.info("waiting time ---> {}", availableTime.getWaitingTime());
			//tableRepo.save(availableTime);
			if(availableTime.equals(null))
			{
				throw new UniquoNotFoundException("The waiting time cannot be calculated");
			}
			int tableNumber = availableTime.getId();
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