package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;
import java.util.Set;

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
	private int quantity;

	private Customer customer;
	
	private TableRestaurant table;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "orderFoodBill", cascade = CascadeType.ALL)
	private Bill bill;
	
	private Set<FoodItem> foodItems;
	
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
	
	public OrderFood(double totalCost, String orderType, int quantity) {
		super();
		this.totalCost = totalCost;
		this.orderType = orderType;
		this.quantity = quantity;
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
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderFood_table", nullable = false)
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

	@ManyToMany
	@JoinTable(name="order_food_Item", joinColumns = @JoinColumn(name="orderFood_id"), inverseJoinColumns = @JoinColumn(name="foodItem_id"))
	public Set<FoodItem> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(Set<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}