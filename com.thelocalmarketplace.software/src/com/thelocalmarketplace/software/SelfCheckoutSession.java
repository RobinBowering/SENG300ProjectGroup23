package com.thelocalmarketplace.software;
import java.util.ArrayList;
import java.util.Map;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.*;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;




public class SelfCheckoutSession {
	ArrayList<Item> order = new ArrayList<Item>();
	private double weightOfCart = 0;
	private Map<Barcode, BarcodedProduct> barcodeMap;
	
	
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
		throw new NullPointException;
		
		double itemActualMass = item.getMass().inGrams().doubleValue();
		Barcode itemBarcode = item.getBarcode();
		
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(itemBarcode);
		double expectedMass = product.getExpectedWeight();
		weightOfCart += itemActualMass;
		
		if (weightOfCart )
		
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
