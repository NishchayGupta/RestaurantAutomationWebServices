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

import com.example.restApi.UniquoRestaurant.dao.CustomerOrder;
import com.example.restApi.UniquoRestaurant.entity.Bill;
import com.example.restApi.UniquoRestaurant.entity.Cashier;
import com.example.restApi.UniquoRestaurant.entity.Chef;
import com.example.restApi.UniquoRestaurant.entity.Customer;
import com.example.restApi.UniquoRestaurant.entity.FoodItem;
import com.example.restApi.UniquoRestaurant.entity.OrderFood;
import com.example.restApi.UniquoRestaurant.entity.Person;
import com.example.restApi.UniquoRestaurant.entity.TableRestaurant;
import com.example.restApi.UniquoRestaurant.exception.EmptyListException;
import com.example.restApi.UniquoRestaurant.exception.UniquoNotFoundException;
import com.example.restApi.UniquoRestaurant.repository.BillRepository;
import com.example.restApi.UniquoRestaurant.repository.CashierRepository;
import com.example.restApi.UniquoRestaurant.repository.ChefRepository;
import com.example.restApi.UniquoRestaurant.repository.CustomerRepository;
import com.example.restApi.UniquoRestaurant.repository.FoodItemRepository;
import com.example.restApi.UniquoRestaurant.repository.OrderFoodRepository;
import com.example.restApi.UniquoRestaurant.repository.TableRepository;

@RestController
@RequestMapping("/uniquo")
public class OrderFoodController {
	static double total_cost = 0;
	@Autowired
	private OrderFoodRepository orderFoodRepo;
	
	@Autowired
	private TableRepository tableRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private ChefRepository chefRepo;
	
	@Autowired
	private FoodItemRepository foodItemRepo;
	
	@Autowired
	private CashierRepository cashierRepo;
	
	@Autowired
	private BillRepository billRepo;
	
	Logger logger = LoggerFactory.getLogger(OrderFoodController.class);
	
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
	
	@PostMapping("/orderFood/{tableId}")
	public OrderFood orderFoodCust(@PathVariable int tableId, @RequestBody CustomerOrder custOrd)
	{
		OrderFood order_food = new OrderFood();
		
		// Customer to be fetched and set
		Person personFetched = custOrd.getPerson();
		logger.info("personId: {},person email: {}", personFetched.getPersonId(), personFetched.getEmail());
		Customer customerFetched = customerRepo.findByEmailAddress(personFetched.getEmail());
		logger.info("person_PersonId: {}, customerPersonId: {}, customerId: {}", personFetched.getPersonId(), 
				customerFetched.getPersonCustomer().getPersonId(), customerFetched.getCustomerId());
		order_food.setCustomer(customerFetched);
		
		// Table to be fetched and set
		Optional<TableRestaurant> table = tableRepo.findById(tableId);
		TableRestaurant tableFetched = table.get();
		if(!table.isPresent())
		{
			throw new UniquoNotFoundException("id: " + tableId);
		}
		order_food.setTable(tableFetched);
		
		// Chef to be fetched and set
		List<Chef> chefList = chefRepo.findAll();
		Chef chefFetched = null;
		for(int i = 0; i< chefList.size(); i++)
		{
			if(chefList.get(i) != null)
			{
				chefFetched = chefList.get(i);
				logger.info("chefId: {}, personName: {}", chefFetched.getChefId(), chefFetched.getPersonChef().getName());
				order_food.setChef(chefFetched);
				break;
			}
		}
		
		/*
		// FoodItems to be fetched and set in OrderFood
		List<FoodItem> foodItemsList = custOrd.getItemFood();
		order_food.setFoodItems(foodItemsList); */
		
		// set quantity in foodQuantity and calculate the total cost
//		List<Integer> foodQty = custOrd.getFoodQuantity();
//		FoodItem foodItemRetrieved = null;
//		FoodQuantity foodQuantity = null;
//		
//		for(int m=0; m<custOrd.getItemFood().size(); m++)
//		{
//			for(int n=m; n<=m; n++)
//			{
//				foodItemRetrieved = custOrd.getItemFood().get(m);
//				foodQuantity = new FoodQuantity(foodQty.get(n), foodItemRetrieved);
//				total_cost += foodItemRetrieved.getFoodItemPrice() * foodQty.get(n);
//				//foodItemRetrieved.setFoodQuantity(foodQuantity);
//				//foodItemRepo.save(foodItemRetrieved);
//			}
//		}
//		order_food.setTotalCost(total_cost);
		
		// set OrderType
		if(tableId == 11)
		{
			order_food.setOrderType("Takeout");
		}
		else
			order_food.setOrderType("Dine In");
		
		
		
		// Finally save the OrderFood object
		OrderFood orderFoodCreated = orderFoodRepo.save(order_food);
		
		// Set the bill
		List<Cashier> cashierList = cashierRepo.findAll();
		Cashier cashierFetched = null;
		for(int i = 0; i< cashierList.size(); i++)
		{
			if(cashierList.get(i) != null)
			{
				cashierFetched = cashierList.get(i);
				break;
			}
		}
		Bill bill = new Bill(order_food, cashierFetched);
		Bill billSaved = billRepo.save(bill);
		if(billSaved.equals(null))
		{
			throw new UniquoNotFoundException("Error in creating the bill");
		}
		
		if(orderFoodCreated.equals(null))
		{
			throw new UniquoNotFoundException("Error in creating order");
		}
		return order_food;
	}
}