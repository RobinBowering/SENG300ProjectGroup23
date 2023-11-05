package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;

import powerutility.PowerGrid;

import com.thelocalmarketplace.software.*;



public class StartSessionTestController {
	
	@Before
	public void setup() {
		SelfCheckoutStation hardware = new SelfCheckoutStation();
		hardware.plugIn(PowerGrid.instance());
		hardware.turnOn();
		
		SelfCheckoutController controller = new SelfCheckoutController(hardware);
		
		
		
		
	}

	@Test
	public void startSessionDuringSession() {
		controller.start
		
	}

}
