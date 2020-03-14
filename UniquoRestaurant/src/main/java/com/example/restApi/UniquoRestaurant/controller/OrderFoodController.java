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
	
	public static final int ID_LENGTH = 4;
	
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
	
	// Check if the customer has current existing order 
	@GetMapping("/order/customer/{customerId}")
	public OrderFood getSingleOrderFromCustomerId(@PathVariable int customerId)
	{
		logger.info("Inside getSingleOrderFromCustomerId");
		OrderFood orderFoodFound = orderFoodRepo.findByCustomerId(customerId);
		logger.info("After getSingleOrderFromCustomerId");
		if(orderFoodFound == null)
		{
			throw new UniquoNotFoundException("Order doesn't exist for the customer: " +customerId);
		}
		return orderFoodFound;
	}
	
	// For Past orders 
	@GetMapping("/order/customer/allOrders/{customerId}")
	public List<OrderFood> getAllOrdersFromCustomerId(@PathVariable int customerId)
	{
		logger.info("Inside getAllOrderFromCustomerId: customerId: {}", customerId);
		List<OrderFood> orderFoodFound = orderFoodRepo.findByCustomerIdAllOrders(customerId);
		logger.info("After getAllOrderFromCustomerId");
		if(orderFoodFound == null)
		{
			throw new UniquoNotFoundException("Order doesn't exist for the customer: " +customerId);
		}
		return orderFoodFound;
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
		logger.info("Order food process started");
		int uniqueOrderId = Integer.parseInt(RandomStringUtils.randomNumeric(ID_LENGTH));
		OrderFood order_food = null;
		List<FoodItemOrder> foodItemOrders;
		
		// Customer to be fetched and set
		Optional<Customer> customerRetrieved = customerRepo.findById(custOrd.getCustomerId());
		Customer customerFetched = customerRetrieved.get();
		logger.info("customerId: {}, customerIdRequest: {}", customerFetched.getCustomerId(), custOrd.getCustomerId());
		logger.info("Customer is fetched from the customer id given in the request");
		
		// Table to be fetched and set
		TableRestaurant tableFetched = null;
		if(custOrd.getTableId() == customerFetched.getTablesRestaurant().getId())
		{
			Optional<TableRestaurant> table = tableRepo.findById(custOrd.getTableId());
			tableFetched = table.get();
			if(!table.isPresent())
			{
				throw new UniquoNotFoundException("id: " + custOrd.getTableId());
			}
			logger.info("Table is fetched from the table id given in the request");
			logger.info("TableIdRequest: {}, TableIdCustomer: {}", custOrd.getTableId(), customerFetched.getTablesRestaurant().getId());
		}
		else
		{
			logger.info("The table id sent in the request and the table id assigned in "
					+ "to the customer doesn't match. TableIdRequest: {}, TableIdCustomer: {}", custOrd.getTableId(), customerFetched.getTablesRestaurant().getId());
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
		logger.info("Chef is fetched");
		
		// set OrderType
				if(custOrd.getTableId() == 11)
				{
					customerFetched.setOrderType("Takeout");
					logger.info("Order type is set as Takeout");
				}
				else if(custOrd.getTableId() == 12)
				{
					customerFetched.setOrderType("xyz");
					logger.info("Order type is set as xyz for registered customers");
				}
				else
				{
					customerFetched.setOrderType("Dine In");
					logger.info("Order type is set as Dine-In");
				}
		
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
			logger.info("Food item added in the food item {} list which is to be passed during order food", food_item_order.getFoodItem().getFoodItemName());
			
			total_cost += foodItemOrders.get(m).getFoodItem().getFoodItemPrice() * cart.getQuantity();
			logger.info("total_cost: {}", total_cost);
		}
		//set order prepared by chef to false initially
		boolean orderPrepared = false;
		//set order is existing in the table for the customer
		boolean isExistingOrder = true;
		// Finally save the OrderFood object
		order_food = new OrderFood(total_cost, customerFetched, tableFetched, chefFetched, foodItemOrders, orderPrepared, isExistingOrder);
		logger.info("Order food constructor is called");
		order_food.setId(uniqueOrderId);
		logger.info("A unique order id {} is set to the order food", uniqueOrderId);
		for(FoodItemOrder fio: foodItemOrders)
		{
			fio.setOrderFood(order_food);
			logger.info("Order food is set in the association tables FoodItemOrder");
		}
		
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
		logger.info("Bill is set");
		
		OrderFood orderFoodCreated = orderFoodRepo.save(order_food);
		logger.info("Order food is saved using the JPA save() method");
		bill.setOrderFoodBill(orderFoodCreated);
		Bill billSaved = billRepo.save(bill);
		logger.info("Bill is saved using the JPA save() method");
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