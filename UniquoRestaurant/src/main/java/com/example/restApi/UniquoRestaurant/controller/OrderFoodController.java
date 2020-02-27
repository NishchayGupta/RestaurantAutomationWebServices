package com.example.restApi.UniquoRestaurant.controller;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.lang.RandomStringUtils;
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
import com.example.restApi.UniquoRestaurant.dao.CustomerOrderCart;
import com.example.restApi.UniquoRestaurant.entity.Bill;
import com.example.restApi.UniquoRestaurant.entity.Cashier;
import com.example.restApi.UniquoRestaurant.entity.Chef;
import com.example.restApi.UniquoRestaurant.entity.Customer;
import com.example.restApi.UniquoRestaurant.entity.FoodItem;
import com.example.restApi.UniquoRestaurant.entity.FoodItemOrder;
import com.example.restApi.UniquoRestaurant.entity.OrderFood;
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
	
	public static final int ID_LENGTH = 10;
	
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
		.buildAndExpand(orderFoodSaved.getId())
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
			//updateOrderFood.setOrderType(orderFood.getOrderType());
			updateOrderFood.setTotalCost(orderFood.getTotalCost());
			orderFoodRepo.save(updateOrderFood);
		}
		else
			throw new UniquoNotFoundException("id: " + orderFoodId);
		
		return updateOrderFood;
	}
	
	@PostMapping("/orderFood")
	public OrderFood orderFoodCust(@RequestBody CustomerOrder custOrd)
	{
		int uniqueOrderId = Integer.parseInt(RandomStringUtils.randomNumeric(ID_LENGTH));
		OrderFood order_food = null;
		List<FoodItemOrder> foodItemOrders;
		
		// Customer to be fetched and set
		Optional<Customer> customerRetrieved = customerRepo.findById(custOrd.getCustomerId());
		Customer customerFetched = customerRetrieved.get();
		logger.info("customerId: {}, customerIdRequest: {}", customerFetched.getCustomerId(), custOrd.getCustomerId());
		
		// Table to be fetched and set
		Optional<TableRestaurant> table = tableRepo.findById(custOrd.getTableId());
		TableRestaurant tableFetched = table.get();
		if(!table.isPresent())
		{
			throw new UniquoNotFoundException("id: " + custOrd.getTableId());
		}
		
		// Chef to be fetched and set
		List<Chef> chefList = chefRepo.findAll();
		Chef chefFetched = null;
		for(int i = 0; i< chefList.size(); i++)
		{
			if(chefList.get(i) == null)
			{
				throw new UniquoNotFoundException("No chef registered yet");
			}
			else if(chefList.get(i) != null)
			{
				chefFetched = chefList.get(i);
				logger.info("chefId: {}, personName: {}", chefFetched.getChefId(), chefFetched.getPersonChef().getName());
				break;
			}
			else
				continue;
		}
		
		// set OrderType
				if(custOrd.getTableId() == 11)
					customerFetched.setOrderType("Takeout");
				else if(custOrd.getTableId() == 12)
					customerFetched.setOrderType("xyz");
				else
					customerFetched.setOrderType("Dine In");
		
		/*
		 FoodItems to be fetched and set in OrderFood
		 set quantity in foodQuantity and calculate the total cost
		 */
		List<CustomerOrderCart> cartItems = custOrd.getOrderCart();
		double total_cost = 0;
		Optional<FoodItem> foodItemRetrieved = null;
		CustomerOrderCart cart = null;
		foodItemOrders = new ArrayList<>();
		FoodItem foodItemFetched = null;
		
		for(int m=0; m<cartItems.size(); m++)
		{
			System.out.println("Cart Size: " + cartItems.size());
			cart = cartItems.get(m);
			logger.info("foodItemId: {}", cart.getFoodItemId());
			foodItemRetrieved = foodItemRepo.findById(cart.getFoodItemId());
			foodItemFetched = foodItemRetrieved.get();
			logger.info("foodItemName: {}", foodItemFetched.getFoodItemName());
			FoodItemOrder food_item_order = new FoodItemOrder(foodItemFetched, cart.getQuantity());
			
			foodItemOrders.add(food_item_order);
			for(FoodItemOrder fio: foodItemOrders)
			{
				System.out.println(fio.getFoodItem().getFoodItemName());
				System.out.println(fio.getQuantity());
			}
			
			total_cost += foodItemOrders.get(m).getFoodItem().getFoodItemPrice() * cart.getQuantity();
			logger.info("total_cost: {}", total_cost);
		}

		// Finally save the OrderFood object
		order_food = new OrderFood(total_cost, customerFetched, tableFetched, chefFetched, foodItemOrders);
		order_food.setId(uniqueOrderId);
		for(FoodItemOrder fio: foodItemOrders)
			fio.setOrderFood(order_food);
		
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
		Bill bill = new Bill();
		bill.setCashier(cashierFetched);
		
		OrderFood orderFoodCreated = orderFoodRepo.save(order_food);
		bill.setOrderFoodBill(orderFoodCreated);
		Bill billSaved = billRepo.save(bill);
		if(billSaved.equals(null))
		{
			throw new UniquoNotFoundException("Error in creating the bill");
		}
		if(orderFoodCreated.equals(null))
		{
			throw new UniquoNotFoundException("Error in creating order");
		}
		Optional<OrderFood> retrieveOrderFood = orderFoodRepo.findById(orderFoodCreated.getId()); 
		return retrieveOrderFood.get();
	}
}