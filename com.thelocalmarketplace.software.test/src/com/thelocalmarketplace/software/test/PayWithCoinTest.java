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
	private BigDecimal orderTotal;
	
	@Before 
	public void setup() {
        controller = new SelfCheckoutController(hardware);
        currentSession = new SelfCheckoutSession(hardware, controller);
        hardware = new SelfCheckoutStation();
        
        hardware.plugIn(PowerGrid.instance());
        hardware.turnOn();
    }
	
	@Test
	public void validPayWithCoinTest() { 
		currentSession.payingForOrder = true;
		currentSession.payWithCoin();
	}  
	
	@Test(expected = DisabledException.class)
	public void slotDisabledTest() {
		currentSession.payingForOrder = false;
		currentSession.payWithCoin();
	} 
}
