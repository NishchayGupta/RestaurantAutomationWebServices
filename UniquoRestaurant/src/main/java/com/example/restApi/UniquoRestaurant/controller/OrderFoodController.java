package com.example.restApi.UniquoRestaurant.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.example.restApi.UniquoRestaurant.entity.OrderFood;
import com.example.restApi.UniquoRestaurant.exception.EmptyListException;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.OrderFoodRepository;

@RestController
@RequestMapping("/uniquo")
public class OrderFoodController {

	@Autowired
	private OrderFoodRepository orderFoodRepo;
	
	@GetMapping("/orders")
	public List<OrderFood> getAllOrders()
	{
		List<OrderFood> orderFoodList = orderFoodRepo.findAll();
		if(orderFoodList.isEmpty())
		{
			throw new EmptyListException("The order food list is empty");
		}
		return orderFoodList;
	}
	
	@GetMapping("/order/{orderFoodId}")
	public OrderFood getSingleOrder(@PathVariable int orderFoodId)
	{
		Optional<OrderFood> orderFoodFound = orderFoodRepo.findById(orderFoodId);
		if(!orderFoodFound.isPresent())
		{
			throw new UniquoNotFoundException("id: " +orderFoodId);
		}
		OrderFood orderFood = orderFoodFound.get();
		return orderFood;
	}
	
	@PostMapping("/order")
	public ResponseEntity<Object> createOrder(@RequestBody OrderFood orderFood)
	{
		OrderFood orderFoodSaved = orderFoodRepo.save(orderFood);
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/id")
		.buildAndExpand(orderFoodSaved.getOrderId())
		.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/order/{orderFoodId}")
	public void removeOrder(@PathVariable int orderFoodId)
	{
		Optional<OrderFood> orderFoodById = orderFoodRepo.findById(orderFoodId);
		if(!orderFoodById.isPresent())
		{
			throw new UniquoNotFoundException("id: " +orderFoodId);
		}
		orderFoodRepo.deleteById(orderFoodId);
	}
	
	@PutMapping("/order/{orderFoodId}")
	public OrderFood updateFoodItem(@PathVariable int orderFoodId, @RequestBody OrderFood orderFood)
	{
		Optional<OrderFood> orderFoodFetched = orderFoodRepo.findById(orderFoodId);
		OrderFood updateOrderFood = orderFoodFetched.get();
		if(orderFoodFetched.isPresent())
		{
			updateOrderFood.setOrderType(orderFood.getOrderType());
			updateOrderFood.setTotalCost(orderFood.getTotalCost());
			orderFoodRepo.save(updateOrderFood);
		}
		else
			throw new UniquoNotFoundException("id: " + orderFoodId);
		
		return updateOrderFood;
	}
}