package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;

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
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bill_orderFood", nullable = false)
	private OrderFood orderFoodBill;
	@ManyToOne(
	          fetch = FetchType.LAZY,
	          optional = false)
	@JoinColumn(
	          name = "cashierId",
	          nullable = false)
	@JsonIgnore
	private Cashier cashier;
	
	public Bill()
	{
	}

	public Bill(int billId) {
		super();
		this.billId = billId;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public OrderFood getOrderFood() {
		return orderFoodBill;
	}

	public void setOrderFood(OrderFood orderFood) {
		this.orderFoodBill = orderFood;
	}

	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
}