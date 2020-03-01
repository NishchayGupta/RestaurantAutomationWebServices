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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderFood implements Serializable{
	
	
	private int id;
	
	private double totalCost;
	
    private Customer customerOrder;
	
	private TableRestaurant table;
	
	private boolean orderPrepared;
	
	private boolean isExistingOrder;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "orderFoodBill", cascade = CascadeType.ALL)
	private Bill bill;
	
	private List<FoodItemOrder> foodItemOrder;
	
	private Chef chef;
	
	public OrderFood()
	{
	}
	
	public OrderFood(double totalCost, Customer customerOrder, TableRestaurant table,
			Chef chef, List<FoodItemOrder> foodItemOrder, boolean orderPrepared, boolean isExisting) {
		super();
		this.totalCost = totalCost;
		this.customerOrder = customerOrder;
		this.table = table;
		this.foodItemOrder = foodItemOrder;
		this.chef = chef;
		this.orderPrepared = orderPrepared;
		this.isExistingOrder = isExisting;
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonIgnore
	public Customer getCustomer() {
		return customerOrder;
	}

	public void setCustomer(Customer customerOrder) {
		this.customerOrder = customerOrder;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderFood_table")
	public TableRestaurant getTable() {
		return table;
	}

	public void setTable(TableRestaurant table) {
		this.table = table;
	}

	@ManyToOne(
	          fetch = FetchType.LAZY,
	          optional = false)
	@JoinColumn
	@JsonIgnore
	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	@OneToMany(mappedBy = "orderFood", cascade = CascadeType.ALL)
	@JsonIgnore
	public List<FoodItemOrder> getFoodItemOrder() {
		return foodItemOrder;
	}

	public void setFoodItemOrder(List<FoodItemOrder> foodItemOrder) {
		this.foodItemOrder = foodItemOrder;
	}

	public boolean isOrderPrepared() {
		return orderPrepared;
	}

	public void setOrderPrepared(boolean orderPrepared) {
		this.orderPrepared = orderPrepared;
	}

	public boolean isExistingOrder() {
		return isExistingOrder;
	}

	public void setExistingOrder(boolean isExistingOrder) {
		this.isExistingOrder = isExistingOrder;
	}
}