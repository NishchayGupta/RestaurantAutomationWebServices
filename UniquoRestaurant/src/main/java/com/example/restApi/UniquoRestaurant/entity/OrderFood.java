package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderFood implements Serializable{
	
	private int id;
	private double totalCost;
	private String orderType;

	private Customer customer;
	private List<Integer> quantity;
	private TableRestaurant table;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "orderFoodBill", cascade = CascadeType.ALL)
	private Bill bill;
	
	private List<FoodItem> foodItems;
	
	@ManyToOne(
	          fetch = FetchType.LAZY,
	          optional = false)
	@JoinColumn(
	          name = "orderFood_chef",
	          nullable = false)
	@JsonIgnore
	private Chef chef;
	
	public OrderFood()
	{
	}
	
	public OrderFood(double totalCost, String orderType, Customer customer, TableRestaurant table, Bill bill,
			List<FoodItem> foodItems, Chef chef) {
		super();
		this.totalCost = totalCost;
		this.orderType = orderType;
		this.customer = customer;
		this.table = table;
		this.bill = bill;
		this.foodItems = foodItems;
		this.chef = chef;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getOrderId() {
		return id;
	}

	public void setOrderId(int orderId) {
		this.id = orderId;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderFood_customer", nullable = false)
	@JsonIgnore
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderFood_table", nullable = false)
	@JsonIgnore
	public TableRestaurant getTable() {
		return table;
	}

	public void setTable(TableRestaurant table) {
		this.table = table;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	@ManyToMany(targetEntity = FoodItem.class)
	@JoinTable(name="order_food_Item", joinColumns = @JoinColumn(name="orderFood_id"), inverseJoinColumns = @JoinColumn(name="foodItem_id"))
	public List<FoodItem> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}
}