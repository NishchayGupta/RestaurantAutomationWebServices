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
public class Bill implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int billId;
	
	private OrderFood orderFoodBill;
	
	private Cashier cashier;
	
	public Bill()
	{
	}

	public Bill(OrderFood orderFoodBill, Cashier cashier) {
		super();
		this.orderFoodBill = orderFoodBill;
		this.cashier = cashier;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "bill")
	@JsonIgnore
	public OrderFood getOrderFood() {
		return orderFoodBill;
	}

	public void setOrderFood(OrderFood orderFood) {
		this.orderFoodBill = orderFood;
	}
	@ManyToOne(
	          fetch = FetchType.LAZY,
	          optional = false)
	@JoinColumn(
	          name = "cashierId")
	@JsonIgnore
	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
}