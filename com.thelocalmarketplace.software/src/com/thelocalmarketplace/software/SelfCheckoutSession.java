package com.thelocalmarketplace.software;
import java.util.ArrayList;
import java.util.Map;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.*;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import ca.ucalgary.seng300.simulation.*;
import java.math.BigDecimal;
import com.thelocalmarketplace.hardware.external.*;


public class SelfCheckoutSession {
	ArrayList<Item> order = new ArrayList<Item>();
	private int totalCartPrice = 0;
	private double expectedWeightOfCart = 0;
	private double actualWeightOfCart = 0;
	private ProductDatabases barcodeMap;
	private Bigdecimal actualWeightOfCart;
	private BigDecimal expectedWeightOfCart;
	private BigDecimal actualMass;
	private BigDecimal expectedMass;
	
	
	private boolean isBlocked = false;
	
	//method to start the session
	public void StartSession() {
		return;
	}
	
	//method to add item to cart
	public void AddItem(BarcodedItem item) throws exception {
		new BarcodeScanner scanner; // Delete this when you added to main, I used it so I could call
		try {scanner.scan(item);} // Try and scan an item
		catch {exception e} // Catches an exception
		throw new NullPointerExcption(); // Throws a null pointer exception from simulation package
		
		actualMass = item.getMass().inGrams(); //Actual mass of the item scanned in 
		Barcode itemBarcode = item.getBarcode(); //Gets the barcode of the scanned item
		
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(itemBarcode); // Gets the database of the barcode
		expectedMass = product.getExpectedWeight(); // Gets expected weight of item
		expectedWeightOfCart += expectedMass; //Update the expected weight that should be on the scale
		double price = barcodeMap.get(product); // get the price from the database
		totalCartPrice += price; // Add item to the total price of customer cart
		
		if (expectedWeightOfCart != actualWeightOfCart) { // If there is a difference between expected and actual weight that should 
			WeightDiscrepancyDetected(); // be on the scale then call WeightDiscrepancyDetected
		}
		
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
