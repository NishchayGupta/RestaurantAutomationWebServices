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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cashier implements Serializable{
	
	private int id;
	
	private Person personCashier;
	
	private List<Bill> bill;
	
	public Cashier()
	{
	}

	public Cashier(Person personCashier) {
		super();
		this.personCashier = personCashier;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_cashier")
	@JsonIgnore
	public Person getPersonCashier() {
		return personCashier;
	}

	public void setPersonCashier(Person personCashier) {
		this.personCashier = personCashier;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cashier", cascade = CascadeType.ALL)
	public List<Bill> getBill() {
		return bill;
	}

	public void setBill(List<Bill> bill) {
		this.bill = bill;
	}

	@Override
	public String toString() {
		return "Cashier [id=" + id + ", personCashier=" + personCashier + ", bill=" + bill + "]";
	}
}