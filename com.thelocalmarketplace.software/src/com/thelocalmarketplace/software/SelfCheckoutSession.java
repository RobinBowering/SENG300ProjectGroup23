package com.thelocalmarketplace.software;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import com.jjjwelectronics.scanner.*;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import ca.ucalgary.seng300.simulation.*;
import java.math.BigDecimal;

import com.thelocalmarketplace.hardware.external.*;
import com.thelocalmarketplace.*;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScanner;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.*;
import com.tdc.*;
public class SelfCheckoutSession implements CoinSlotObserver, CoinValidatorObserver, CoinStorageUnitObserver, ElectronicScaleListener, BarcodeScannerListener {
	
	SelfCheckoutController controlller;
	
	BarcodeScanner scanner;
	CoinStorageUnit coinStorage;
	CoinValidator validator;
	CoinSlot coinslot;
	ElectronicScale scale;
	
	ArrayList<Item> order = new ArrayList<Item>();
	double total = 0;
	
	private boolean isBlocked = false;

	// Kelvin's Added variables
	private double expectedWeightOfCart = 0;
	private double actualWeightOfCart = 0;
	private ProductDatabases barcodeMap;
	private Bigdecimal actualWeightOfCart;
	private BigDecimal expectedWeightOfCart;
	private BigDecimal actualMass;
	private BigDecimal expectedMass;
	
	/**
	 * Instantiates a Self Checkout Session
	 * @param a Self Checkout Station with all hardware enabled
	 * @param The self checkout controller which called the constructor
	 */
	public SelfCheckoutSession(SelfCheckoutStation station, SelfCheckoutController instantiator) {
		scale = station.baggingArea;
		scanner = station.scanner;
		validator = station.coinValidator;
		coinslot = station.coinSlot;
		coinStorage = station.coinStorage;
		
	}
	
	//method to start the session
	public void StartSession() {
		return;
	}
	
	//method to add item to cart
	public void AddItem(BarcodedItem item) throws exception {
		try {scanner.scan(item);} // Try and scan an item
		catch {exception e} // Catches an exception
		throw new NullPointerExcption(); // Throws a null pointer exception from simulation package
		
		actualMass = item.getMass().inGrams(); //Actual mass of the item scanned in 
		Barcode itemBarcode = item.getBarcode(); //Gets the barcode of the scanned item
		
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(itemBarcode); // Gets the database of the barcode
		expectedMass = product.getExpectedWeight(); // Gets expected weight of item
		expectedWeightOfCart += expectedMass; //Update the expected weight that should be on the scale
		double price = barcodeMap.get(product); // get the price from the database
		total += price; // Add item to the total price of customer cart
		
		if (expectedWeightOfCart != actualWeightOfCart) { // If there is a difference between expected and actual weight that should 
			WeightDiscrepancyDetected(); // be on the scale then call WeightDiscrepancyDetected
		}
		
		return;
	}
	
	//method to pay with coin
	public void PayWithCoin(){
		coinslot.activate();
		double coinValue = 0;
		
		while(total > 0) {
			System.out.println("Total: " + total);
			System.out.print("Insert cash: ");
			coinValue = coinEntered.doubleValue();
		}
		
		total -= coinValue;
		
		if(total <= 0) {
			coinslot.disactivate();
			System.out.println("Payment completed");
		}
	}
	
	//method for when weight discrepancy is detected
	public void WeightDiscrepancyDetected() {
		isBlocked = true;
		System.out.println("Customer screen: Weight discrepancy detected.");
		System.out.println("Attendant screen: Weight discrepancy detected.");
	}

	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsFull(CoinStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinAdded(CoinStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsLoaded(CoinStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsUnloaded(CoinStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinInserted(CoinSlot slot) {
		// TODO Auto-generated method stub
		
	}

}
