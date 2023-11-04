package com.thelocalmarketplace.software;
import java.util.ArrayList;
import java.util.Map;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.*;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import ca.ucalgary.seng300.simulation.*;
import java.math.BigDecimal;



public class SelfCheckoutSession {
	ArrayList<Item> order = new ArrayList<Item>();
	private int totalCartPrice = 0;
	private double expectedWeightOfCart = 0;
	private double actualWeightOfCart = 0;
	private Map<Barcode, BarcodedProduct> barcodeMap;
	private Bigdecimal value;
	
	
	private boolean isBlocked = false;
	
	//method to start the session
	public void StartSession() {
		return;
	}
	
	//method to add item to cart
	public void AddItem(BarcodedItem item) throws exception {
		new BarcodeScanner scanner;
		try {scanner.scan(item);}
		catch {exception e}
		throw new NullPointerExcption();
		
		value = item.getMass().inGrams();
		Barcode itemBarcode = item.getBarcode();
		
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(itemBarcode);
		double expectedMass = product.getExpectedWeight();
		expectedWeightOfCart += itemActualMass;
		double price = barcodeMap.get(product);
		totalCartPrice += price;
		
		WeightDiscrepancyDetected()	
		
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
