package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;

import powerutility.PowerGrid;

import com.thelocalmarketplace.software.*;

/**
 * 
 * @author Robin Bowering UCID 30123373
 * @author Kelvin Jamila UCID 30117164
 * @author Nikki Kim UCID 30189188
 * @author Hillary Nguyen UCID 30161137
 * @author Matt Gibson UCID 30117091
 * 
 * Test class for the start session 
 * 
 */
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
