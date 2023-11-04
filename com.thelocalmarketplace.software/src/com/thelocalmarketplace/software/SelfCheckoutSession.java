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
import com.jjjwelectronics.OverloadedDevice;
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

/**
 * 
 * @author Robin Bowering UCID 30123373
 * @author Matt Gibson UCID 30117091
 * @author Kelvin Jamila UCID 30117164
 * @author Nikki Kim UCID 30189188
 * @author Hillary Nguyen UCID 30161137
 * 
 * Monolithic class representing and supporting a single session with the Self-Checkout System
 * 
 * Supports:
 *  -startup
 *  -automatic item addition to order upon barcode scan
 *  -automatic weight management, checked
 *  
 */
public class SelfCheckoutSession implements CoinSlotObserver, CoinValidatorObserver, CoinStorageUnitObserver, ElectronicScaleListener,
BarcodeScannerListener {
	
	/**
	 * Barcode Scanner of self checkout machine, representative of hardware
	 * component
	 */
	BarcodeScanner scanner;
	/**
	 * Coin Storage Unit of self checkout machine, representative of 
	 * hardware component
	 */
	CoinStorageUnit coinStorage;
	CoinValidator validator;
	CoinSlot coinslot;
	ElectronicScale scale;
	
	/**
	 * The controller which set up the current session
	 */
	SelfCheckoutController controller;
	
	/**
	 * The total cost of all items in order
	 */
	BigDecimal orderTotal = BigDecimal.ZERO;
	
	/**
	 * The amount which has been received against the customer's balance through processPayment()
	 */
	BigDecimal amountPaid = BigDecimal.ZERO;
	
	/**
	 * State variable, signaling if the session has an ongoing weight discrepancy
	 */
	private boolean weightDiscrepancy = false;
	
	/**
	 * State variable, signaling if the customer has entered the payment phase (and can no longer add items)
	 */
	private boolean payingForOrder = false;

	// Kelvin's Added variables
	/**
	 * Mass as reported by the scale through listener method
	 */
	private BigDecimal actualMassOnScale;
	
	/**
	 * Expected mass based on sum of all items in order's mass
	 */
	private BigDecimal expectedMassOnScale;
	private BigDecimal expectedMass;
	
	/**
	 * Instantiates a Self Checkout Session with all hardware enabled except coinslot
	 * Checks for discrepancy
	 * 
	 * @param a Self Checkout Station with all hardware enabled
	 * @param The self checkout controller which called the constructor
	 */
	public SelfCheckoutSession(SelfCheckoutStation station, SelfCheckoutController instantiator) {
		
		controller = instantiator;
		
		scale.enable();
		
		scanner = station.scanner;
		scanner.enable();
		
		validator = station.coinValidator;
		validator.enable();
		
		coinslot = station.coinSlot;
		coinslot.disable();
		
		coinStorage = station.coinStorage;
		coinStorage.enable();
		
		discrepancyCheck();
		
	}
	
	//method to start the session
	public void startSession() {
		return;
	}
	
	/**
	 * Looks up product associated with barcode passed to it and adds it to order
	 * Triggers discrepancyCheck() after updating expected weight
	 * @param barcode
	 */
	public void addItem(Barcode barcode) {
		
		if (payingForOrder) {return;}
		
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode); // Gets the database of the barcode
		expectedMass = BigDecimal.valueOf(product.getExpectedWeight()); // Gets expected weight of item
		BigDecimal price = BigDecimal.valueOf(product.getPrice()); // get the price from the database
		
		expectedMassOnScale = expectedMassOnScale.add(expectedMass); //Update the expected weight that should be on the scale

		orderTotal = orderTotal.add(price); // Add product price to the total price of customer cart
		
		discrepancyCheck();
		
		
	}
	
	//method to pay with coin
	public void payWithCoin() {
		
		scanner.disable();
		coinslot.enable();
		payingForOrder = true;
		
		System.out.println("Total: $" + orderTotal);
		System.out.print("Insert coins: ");
	}
	
	
	public void processPayment(BigDecimal currentPayment) {
		
		amountPaid = amountPaid.add(currentPayment);
		
		if (orderTotal.compareTo(amountPaid) >= 0) {
			
			coinslot.disable();
			System.out.println("Payment completed, ending session");
			controller.sessionEnded();
			
			//GUI scene would reset here
			
		}
		
	}
	
	//method for when weight discrepancy is detected
	public void weightDiscrepancyDetected() {
		
		if (payingForOrder) {coinslot.disable();}
		
		else {scanner.disable();}
		
		weightDiscrepancy = true;
		System.out.println("Customer screen: Weight discrepancy detected.");
		System.out.println("Attendant screen: Weight discrepancy detected.");
	}
	
	public void weightDiscrepancyEnded() {
		
		if (payingForOrder) {
			coinslot.enable();
		}
		else {
			
		}
		
		weightDiscrepancy = false;
		System.out.println("Customer screen: Weight discrepancy resolved.");
		System.out.println("Attendant screen: Weight discrepancy resolved.");
		
	}
	
	public void discrepancyCheck() {
		
		try {
			actualMassOnScale = scale.getCurrentMassOnTheScale().inGrams();
		} catch (OverloadedDevice sadScale) {
			weightDiscrepancyDetected();
			System.out.println("SCALE OVERLOADED. PLEASE REMOVE WEIGHT AND ALERT STAFF");
			return;
		}
		
		BigDecimal difference = actualMassOnScale.subtract(expectedMassOnScale).abs();
		BigDecimal sensitivity = scale.getSensitivityLimit().inGrams();
		
		
		if (!weightDiscrepancy) {
				
			if (difference.compareTo(sensitivity) > 0) {
					weightDiscrepancyDetected();
					return;
			}
		}
		
		if (difference.compareTo(sensitivity) < 0) {
					weightDiscrepancyEnded();
			}
		
	}

	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {}

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {}

	@Override
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		processPayment(value);
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
		addItem(barcode);
	}

	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		discrepancyCheck();
		
	}

	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {}

	@Override
	public void coinsFull(CoinStorageUnit unit) {}

	@Override
	public void coinAdded(CoinStorageUnit unit) {}

	@Override
	public void coinsLoaded(CoinStorageUnit unit) {}

	@Override
	public void coinsUnloaded(CoinStorageUnit unit) {}

	@Override
	public void coinInserted(CoinSlot slot) {}

}
