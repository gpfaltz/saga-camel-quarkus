package com.gpfaltz;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Header;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreditService {

	private int totalCredit;
	private Map<Long, Integer> order_value = new HashMap<>();

	public CreditService() {
		this.totalCredit = 100;
	}

	public void newOrderValue(@Header("id") Long orderId, @Header("value") int value) {
		if (value > totalCredit) {
			this.totalCredit = 100; //for the sake of keep testing...
			throw new IllegalStateException("insufficient funds");
		}

		totalCredit = totalCredit - value;
		order_value.put(orderId, value);
	}

	public void cancelOrderValue(@Header("id") Long id) {
		System.out.println("OrderValue failed. Starting order cancel...");
	}

	public int getTotalCredit() {
		return totalCredit;
	}
}
