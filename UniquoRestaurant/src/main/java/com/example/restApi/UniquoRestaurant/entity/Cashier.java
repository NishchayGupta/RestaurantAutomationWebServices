package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Cashier implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cashierId;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_cashier", nullable = false)
	private Person personCashier;
	
	public Cashier()
	{
	}

	public Cashier(int cashierId) {
		this.cashierId = cashierId;
	}

	public int getCashierId() {
		return cashierId;
	}

	public void setCashierId(int cashierId) {
		this.cashierId = cashierId;
	}

	public Person getPersonCashier() {
		return personCashier;
	}

	public void setPersonCashier(Person personCashier) {
		this.personCashier = personCashier;
	}
}