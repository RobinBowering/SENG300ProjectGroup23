package com.thelocalmarketplace.software;
import java.util.ArrayList;
import java.util.Scanner;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodeScanner;
import com.tdc.coin.CoinSlot;

public class SelfCheckoutSession {
	ArrayList<Item> order = new ArrayList<Item>();
	Scanner userInput = new Scanner(System.in);
	
	CoinSlot coinslot = new CoinSlot();
	BarcodeScanner scanner = new BarcodeScanner();
	
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
	
	//simple WeightDiscrepancyDetected to change isBlocked, possibly to make it false when WeightDiscrepancyDetected is no longer true
	public void WeightDiscrepancyDetected(boolean value) {
		isBlocked = value;
	}
	
	//just called when WeightDiscrepancyDetected
	public void WeightDiscrepancyDetected() {
		isBlocked = true;
		coinslot.disable();
		scanner.disable();
	}
	
	//another method to actually remove weight discrepancy, instead of using the first one
	public void WeightDiscrepancyRemoved() {
		isBlocked = false;
		coinslot.enable();
		scanner.enable();
	
	}
		
}
