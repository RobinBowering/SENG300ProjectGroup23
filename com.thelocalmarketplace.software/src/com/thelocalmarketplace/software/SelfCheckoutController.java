package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class SelfCheckoutController {
	
	SelfCheckoutStation hardware;
	
	boolean activeSession = false;
	
	/**
	 * Takes a SelfCheckoutMachine that is plugged in and turned on
	 */
	public SelfCheckoutController(SelfCheckoutStation station) {
		hardware = station;
		
		disableAll();
	}
	

	
	/**
	 * Instantiates a SelfCheckoutSession and registers it as a listener for appropriate hardware
	 */
	public void startSession() {
		
		if (!activeSession) {
			
			activeSession = true;
			
			SelfCheckoutSession session = new SelfCheckoutSession(hardware,this);
			
			hardware.baggingArea.register(session);
			hardware.scanner.register(session);
			hardware.coinSlot.attach(session);
			hardware.coinValidator.attach(session);
			hardware.coinStorage.attach(session);
			
		}		
	}
	
	/**
	 * Changes controller and accessible hardware to non-active-session state
	 */
	public void sessionEnded() {
		activeSession = false;
		disableAll();
	}
	
	/**
	 * Disables non-touchscreen hardware until a session is started
	 */
	private void disableAll() {
		hardware.baggingArea.disable();
		hardware.scanner.disable();
		hardware.coinSlot.disable();
		hardware.coinValidator.disable();
		hardware.coinStorage.disable();
	}
	
}
	
