package com.thelocalmarketplace.software.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.scale.ElectronicScale;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinSlot;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.SelfCheckoutController;
import com.thelocalmarketplace.software.SelfCheckoutSession;

import powerutility.PowerGrid;

public class WeightDiscrepancyTest {
	
	
	SelfCheckoutSession session;
	Coin coin;
	
	
	@Before 
	public void setup() {
		SelfCheckoutStation hardware = new SelfCheckoutStation();
        hardware.plugIn(PowerGrid.instance());
        hardware.turnOn();
        
        SelfCheckoutController controller = new SelfCheckoutController(hardware);
        
		SelfCheckoutSession session = new SelfCheckoutSession(hardware, controller);
	}
	
	@Test(expected = DisabledException.class)
	public void TestCoinSlotBlocked() throws DisabledException, CashOverloadException {
		session.payingForOrder = true;
		session.coinslot.receive(coin);
		session.weightDiscrepancyDetected();
	}
	
	@Test
	public void TestScannerBlocked() {
		session.payingForOrder = false;
		session.weightDiscrepancyDetected();
		assertEquals(true, session.scanner.isDisabled());
	}
	
}
