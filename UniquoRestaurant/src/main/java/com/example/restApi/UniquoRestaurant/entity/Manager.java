package com.example.restApi.UniquoRestaurant.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@Entity
public class Manager implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int manageId;

	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_manager")
	@JsonIgnore
	private Person personManager;
	
	
	public Manager()
	{
	}

	public Manager(Person personManager) {
		super();
		this.personManager = personManager;
	}

	public int getManageId() {
		return manageId;
	}

	public void setManageId(int manageId) {
		this.manageId = manageId;
	}

	public Person getPersonManager() {
		return personManager;
	}

	public void setPersonManager(Person personManager) {
		this.personManager = personManager;
	}
}