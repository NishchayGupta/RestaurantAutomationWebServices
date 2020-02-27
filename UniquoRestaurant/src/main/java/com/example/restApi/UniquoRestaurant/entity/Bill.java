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
	
	private int id;
	
	private OrderFood orderFoodBill;
	
	private Cashier cashier;
	
	public Bill()
	{
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(referencedColumnName = "id")
	@JsonIgnore
	public OrderFood getOrderFoodBill() {
		return orderFoodBill;
	}

	public void setOrderFoodBill(OrderFood orderFoodBill) {
		this.orderFoodBill = orderFoodBill;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(referencedColumnName = "id")
	@JsonIgnore
	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
}