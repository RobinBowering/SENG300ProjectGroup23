package com.thelocalmarketplace.software;

import java.math.BigDecimal;


import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class SelfCheckoutController {
	
	SelfCheckoutStation hardware;
	
	/**
	 * Takes a SelfCheckoutMachine that is plugged in and turned on
	 */
	public SelfCheckoutController(SelfCheckoutStation station) {
		hardware = station;
	}
	
	/**
	 * Confirms that all needed hardware is enabled, and instantiates a SelfCheckoutSession if so
	 */
	public void startSession() {
		
		
			
		SelfCheckoutSession session = new SelfCheckoutSession(hardware,this);
			
		hardware.baggingArea.register(session);
		hardware.scanner.register(session);
		hardware.coinSlot.attach(session);
		hardware.coinValidator.attach(session);
		hardware.coinStorage.attach(session);
		
	}
	private boolean allEnabled() {
		if ( !hardware.baggingArea.isDisabled() ) {
			if ( !hardware.scanner.isDisabled() {
				
			}
		}
			
	}
	
}
	
