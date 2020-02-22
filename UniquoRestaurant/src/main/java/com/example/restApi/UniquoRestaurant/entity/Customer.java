package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Customer implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_customer")
	@JsonIgnore
	private Person personCustomer;
	
	private OrderFood orderFood;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "table_customer", unique = false)
	@JsonIgnore
	private TableRestaurant tablesRestaurant;
	
	public Customer()
	{
	}

	public Customer(Person personCustomer, TableRestaurant tablesRestaurant) {
		super();
		this.personCustomer = personCustomer;
		this.tablesRestaurant = tablesRestaurant;
	}

	public int getCustomerId() {
		return id;
	}

	public void setCustomerId(int customerId) {
		this.id = customerId;
	}

	public Person getPersonCustomer() {
		return personCustomer;
	}

	public void setPersonCustomer(Person personCustomer) {
		this.personCustomer = personCustomer;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
	@JsonIgnore
	public OrderFood getOrderFood() {
		return orderFood;
	}

	public void setOrderFood(OrderFood orderFood) {
		this.orderFood = orderFood;
	}

	public TableRestaurant getTablesRestaurant() {
		return tablesRestaurant;
	}

	public void setTablesRestaurant(TableRestaurant tablesRestaurant) {
		this.tablesRestaurant = tablesRestaurant;
	}
}