package com.thelocalmarketplace.software.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tdc.DisabledException;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.SelfCheckoutController;
import com.thelocalmarketplace.software.SelfCheckoutSession;

import powerutility.PowerGrid;

/**
 * 
 * @author Robin Bowering UCID 30123373
 * @author Kelvin Jamila UCID 30117164
 * @author Nikki Kim UCID 30189188
 * @author Hillary Nguyen UCID 30161137
 * @author Matt Gibson UCID 30117091
 * 
 * Test class for paying with coin
 * 
 */
public class PayWithCoinTest {
	private SelfCheckoutController controller;
	private SelfCheckoutStation hardware;
	private SelfCheckoutSession currentSession;

	
	@Before 
	public void setup() {
        hardware = new SelfCheckoutStation();
        hardware.plugIn(PowerGrid.instance());
        hardware.turnOn(); 
        controller = new SelfCheckoutController(hardware);
        currentSession = new SelfCheckoutSession(hardware, controller);
        currentSession.orderTotal = BigDecimal.valueOf(25);
    }
	
	
	@Test
	public void validPayWithCoinTest() { 
		currentSession.payWithCoin();
		Assert.assertEquals(true, currentSession.payingForOrder);
	}  
	
	@Test
	public void payWhileWeightDiscrepancy() {
		currentSession.weightDiscrepancy = true;
		currentSession.payWithCoin();
		
	}
	
	@Test
	public void amountPaidMoreTotal() {
		currentSession.payWithCoin();
		BigDecimal payment = new BigDecimal(30);
		currentSession.processPayment(payment);
		
		Assert.assertTrue(hardware.coinSlot.isDisabled());
	} 
	
	@Test
	public void amountPaidZero() {
		currentSession.payWithCoin();
		BigDecimal payment = new BigDecimal(0);
		currentSession.processPayment(payment);
		
		Assert.assertTrue(hardware.coinSlot.isDisabled());
	}
	
	@Test
	public void amountPaidLessTotal() {
		currentSession.payWithCoin();
		BigDecimal payment = new BigDecimal(10);
		currentSession.processPayment(payment);

		Assert.assertFalse(hardware.coinSlot.isDisabled());
	} 
}
