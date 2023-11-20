package com.luxoft.bankapp.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

public class Client {
	
	private String name;
	private Gender gender;

	//ToDo: task 1.1
	private String city;

	//ToDo: task 1.1
	private Set<Account> accounts = new HashSet<Account>();



	public Client(String name, Gender gender, String city) {
		this.name = name;
		this.gender = gender;
		this.city = city;
	}

	//ToDo: task 1.1? nu ar trebui verificat si aici daca mai exista account ul pe care vrem sa il adaugam?
	public void addAccount(final Account account) {
		accounts.add(account);
	}
	
	public String getName() {
		return name;
	}

	//ToDo: task 1.1
	public String getCity() {
		return city;
	}
	
	public Gender getGender() {
		return gender;
	}

	//ToDo: task 1.1
	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}
	
	public String getClientGreeting() {
		if (gender != null) {
			return gender.getGreeting() + " " + name;
		} else {
			return name;
		}
	}
	
	@Override
	public String toString() {
		return getClientGreeting();
	}

	//equals e pt seturi, sa vada daca apartine sau nu setului
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Client client = (Client) o;
		return Objects.equals(name, client.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
