package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderFood implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	private double totalCost;
	private String orderType;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderFood_customer", nullable = false)
	private Customer customer;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderFood_table", nullable = false)
	private TableRestaurant table;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "orderFoodBill", cascade = CascadeType.ALL)
	private Bill bill;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "orderFood", cascade = CascadeType.ALL)
	private OrderFoodItem orderItemFood;
	
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
	
	public OrderFood(double totalCost, String orderType) {
		super();
		this.totalCost = totalCost;
		this.orderType = orderType;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

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

	public OrderFoodItem getOrderItemFood() {
		return orderItemFood;
	}

	public void setOrderItemFood(OrderFoodItem orderItemFood) {
		this.orderItemFood = orderItemFood;
	}
}