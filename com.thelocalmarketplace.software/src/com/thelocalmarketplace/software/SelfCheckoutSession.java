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
public class SelfCheckoutSession implements CoinSlotObserver, CoinValidatorObserver, CoinStorageUnitObserver, ElectronicScaleListener, BarcodeScannerListener {
	
	SelfCheckoutController controlller;
	
	
	BarcodeScanner scanner;
	CoinStorageUnit coinStorage;
	CoinValidator validator;
	CoinSlot coinslot;
	ElectronicScale scale;
	
	ArrayList<Item> order = new ArrayList<Item>();
	BigDecimal total = BigDecimal.ZERO;
	BigDecimal coinEntered = BigDecimal.ZERO;
	
	private boolean weightDiscrepancy = false;
	private boolean payingForOrder = false;

	// Kelvin's Added variables
	private ProductDatabases barcodeMap;
	private BigDecimal actualMassOnScale;
	private BigDecimal expectedMassOnScale;
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
	public void startSession() {
		return;
	}
	
	//method to add item to cart
	public void addItem(Barcode barcode) {
		
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode); // Gets the database of the barcode
		expectedMass = BigDecimal.valueOf(product.getExpectedWeight()); // Gets expected weight of item
		BigDecimal price = BigDecimal.valueOf(product.getPrice()); // get the price from the database
		
		expectedMassOnScale = expectedMassOnScale.add(expectedMass); //Update the expected weight that should be on the scale

		total = total.add(price); // Add product price to the total price of customer cart
		
		if (expectedMassOnScale != actualMassOnScale) { // If there is a difference between expected and actual weight that should 
			weightDiscrepancyDetected(); // be on the scale then call WeightDiscrepancyDetected
		}
		
		return;
	}
	
	//method to pay with coin
	public void payWithCoin(){
		coinslot.activate();
		BigDecimal coinValue = BigDecimal.ZERO;
		
		while(total.doubleValue() > 0) {
			System.out.println("Total: " + total);
			System.out.print("Insert cash: ");
			coinValue = BigDecimal.valueOf(coinEntered.doubleValue());
		}
		
		total = total.subtract(coinValue);
		
		if(total.compareTo((BigDecimal.ZERO)) >= 0) {
			coinslot.disactivate();
			System.out.println("Payment completed");
		}
	}
	
	//method for when weight discrepancy is detected
	public void weightDiscrepancyDetected() {
		scanner.disable();
		if (payingForOrder) {
			coinslot.disable();
		}
		
		weightDiscrepancy = true;
		System.out.println("Customer screen: Weight discrepancy detected.");
		System.out.println("Attendant screen: Weight discrepancy detected.");
	}
	
	public void weightDiscrepancyEnded() {
		scanner.enable();
		if (payingForOrder) {
			coinslot.enable();
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
		
		BigDecimal difference = actualMassOnScale.subtract(expectedMassOnScale);
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
		coinEntered = coinEntered.add(value);
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
		addItem(barcode);
		
	}

	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		discrepancyCheck();
		
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
