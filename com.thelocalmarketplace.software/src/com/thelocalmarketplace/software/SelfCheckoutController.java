//Robin Bowering UCID 30123373
//Matt Gibson UCID 30117091
//Kelvin Jamila UCID 30117164
//Nikki Kim UCID 30189188
//Hillary Nguyen UCID 30161137


package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;

/**
 * 
 * @author Robin Bowering UCID 30123373
 * @author Kelvin Jamila UCID 30117164
 * @author Nikki Kim UCID 30189188
 * @author Hillary Nguyen UCID 30161137
 * @author Matt Gibson UCID 30117091
 * 
 * Class responsible for starting sessions and managing hardware activity outside of sessions
 * 
 * Should be constructed with a configured Self-Checkout machine. Does not configure any hardware besides enabling and disabling where appropriate
 */
public class SelfCheckoutController {
	
	SelfCheckoutStation hardware;
	
	/**
	 * State variable indicating whether an active session is in progress
	 */
	boolean activeSession = false;
	
	/**
	 * Takes a SelfCheckoutMachine that is plugged in and turned on and associates it with a field in new controller
	 */
	public SelfCheckoutController(SelfCheckoutStation station) {
		hardware = station;
		
		disableAll();
	}
	

	
	/**
	 * If there is no session in progress, instantiates a SelfCheckoutSession and registers it as a listener for appropriate hardware
	 */
	public SelfCheckoutSession startSession() {
		
		if (!activeSession) {
			
			activeSession = true;
			
			SelfCheckoutSession session = new SelfCheckoutSession(hardware,this);
			
			hardware.baggingArea.register(session);
			hardware.scanner.register(session);
			hardware.coinSlot.attach(session);
			hardware.coinValidator.attach(session);
			hardware.coinStorage.attach(session);

			return session;
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
	
