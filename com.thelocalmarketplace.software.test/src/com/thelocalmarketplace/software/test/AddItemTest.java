package com.thelocalmarketplace.software.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinSlot;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.SelfCheckoutController;
import com.thelocalmarketplace.software.SelfCheckoutSession;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import powerutility.PowerGrid;

public class AddItemTest {

	SelfCheckoutSession session;
	Coin coin;
	Barcode barcode;

	
	
	@Before 
	public void setup() {
		SelfCheckoutStation hardware = new SelfCheckoutStation();
        hardware.plugIn(PowerGrid.instance());
        hardware.turnOn();
        
     
        
        SelfCheckoutController controller = new SelfCheckoutController(hardware);
        
		session = new SelfCheckoutSession(hardware, controller);
	}
	
	
	//test for barcode scanner enabled, but invalid barcode (null)
	@Test(expected = NullPointerException.class)
	public void NullBarcode() {
		session.addItem(null);
	}
	
	
	//test for barcode scanner enabled, and valid barcode is scanned
	//since product database isn't populated yet, will get null pointer exception
	@Test(expected = NullPointerException.class)
	public void ValidBarcode() {
		session.addItem(barcode);
		
	}
	
	
	//test for barcode scanner is blocked when weight discrepancy detected
	@Test
	public void ScannerBlockedByWeightDiscrepancy() {
		session.weightDiscrepancyDetected();
		assertTrue(session.scanner.isDisabled());
	}
	
	//test for barcode scanner is blocked when pay with coin
	@Test
	public void ScannerBlockedByPayWithCoin() {
		session.payWithCoin();
		assertTrue(session.scanner.isDisabled());
	}
}
