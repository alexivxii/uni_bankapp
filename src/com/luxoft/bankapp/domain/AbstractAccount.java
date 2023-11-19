package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.util.Objects;

public abstract class AbstractAccount implements Account {
	
	private int id;
	protected double balance;
	
	public AbstractAccount(int id, double balance) {
		this.id = id;
		this.balance = balance;
	}

	@Override
	public void deposit(final double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot deposit a negative amount");
		}
		this.balance += amount;
	}

	@Override
	public void withdraw(final double amount) throws NotEnoughFundsException {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot withdraw a negative amount");
		}
		
		if (amount > maximumAmountToWithdraw()) {
			throw new NotEnoughFundsException(id, balance, amount, "Requested amount exceeds the maximum amount to withdraw");
		}
		
		this.balance -= amount;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	//ToDo: task 1.1
	@Override
	public boolean equals(Object obj){
		if(this==obj) {
			return true;
		}
		else if(obj==null){
			return false;
		}
		else if(this.getClass()!=obj.getClass()){
			return false;
		}
		AbstractAccount aa = (AbstractAccount)obj;
		return this.id == aa.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, balance);
	}

}
