package com.thelocalmarketplace.software;
import java.util.ArrayList;

import com.jjjwelectronics.Item;

public class SelfCheckoutSession {
	ArrayList<Item> order = new ArrayList<Item>();
	
	
	private boolean isBlocked = false;
	
	//method to start the session
	public void StartSession() {
		return;
	}
	
	//method to add item to cart
	public void AddItem() {
		return;
	}
	
	//method to pay with coin
	public void PayWithCoin(int amount) {
		return;
	}
	
	//method for when weight discrepancy is detected
	public void WeightDiscrepancyDetected() {
		isBlocked = true;
		System.out.println("Customer screen: Weight discrepancy detected.");
		System.out.println("Attendant screen: Weight discrepancy detected.");
	}

}
