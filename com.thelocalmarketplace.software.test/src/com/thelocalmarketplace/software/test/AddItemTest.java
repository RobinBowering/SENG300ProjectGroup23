package com.thelocalmarketplace.software.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinSlot;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.SelfCheckoutController;
import com.thelocalmarketplace.software.SelfCheckoutSession;

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
	
	//Test if barcode scanner is disabled
	@Test
	public void ScannerDisabled() {
		session.payingForOrder = true;
		session.addItem(barcode);
		assertEquals(true, session.scanner.isDisabled());
	}
	
	//Test if barcode scanner is enabled, but invalid barcode (null)
	public void NullBarcode() {
		return;
	}
	
	//Test if barcode scanner is enabled, and valid barcode is scanned
	public void ValidBarcode() {
		return;
	}
	
	//Test if barcode scanner is enabled, and valid barcode is scanned, and Self checkout system is blocked/barcode scanner is blocked
	public void ScannerBlocked() {
		return;
	}
	
	//Test if system is back to start state after weight is updated
	public void ReturnToStartState() {
		return;
	}

	
}
