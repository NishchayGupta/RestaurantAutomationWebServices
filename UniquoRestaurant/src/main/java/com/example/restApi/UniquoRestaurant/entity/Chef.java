package com.example.restApi.UniquoRestaurant.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Chef implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int chefId;
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_chef")
	@JsonIgnore
	private Person personChef;
	
	public Chef()
	{
	}

	public Chef(Person person) {
		this.personChef = person;
	}

	public int getChefId() {
		return chefId;
	}

	public void setChefId(int chefId) {
		this.chefId = chefId;
	}

	public Person getPersonChef() {
		return personChef;
	}

	public void setPersonChef(Person personChef) {
		this.personChef = personChef;
	}
}