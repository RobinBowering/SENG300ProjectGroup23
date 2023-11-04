package com.thelocalmarketplace.software.test;

import org.junit.*;
import com.jjjwelectronics.*;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.tdc.*;
import powerutility.*;

public class TestClass {
	
	SelfCheckoutStation station = new SelfCheckoutStation();
	PowerGrid grid = PowerGrid.instance();

	@Before public void setup() {
		station.plugIn(grid);
		station.turnOn();
		
	}
	
	
	
}
