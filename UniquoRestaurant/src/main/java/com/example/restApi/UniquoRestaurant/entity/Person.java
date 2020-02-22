package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;


@Entity
public class Person implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int personId;
	@NotNull
	private String name;
	@Column(unique = true, nullable = false)
	private String email;
	@NotNull
	private long phoneNumber;
	@NotNull
	private String password;
	@NotNull
	private String userType;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "personManager", cascade = CascadeType.ALL)
	private Manager manager;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "personCashier", cascade = CascadeType.ALL)
	private Cashier cashier;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "personChef", cascade = CascadeType.ALL)
	private Chef chef;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "personCustomer", cascade = CascadeType.ALL)
	private Customer customer;
	
	public Person() {
	}
	
	public Person(String name, String email, long phoneNumber, String password, String userType) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.userType = userType;
	}

	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", password=" + password + ", userType=" + userType + ", manager=" + manager + ", cashier=" + cashier
				+ ", chef=" + chef + ", customer=" + customer + "]";
	}
}