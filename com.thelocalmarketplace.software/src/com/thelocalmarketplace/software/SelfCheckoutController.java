package com.thelocalmarketplace.software;

import java.math.BigDecimal;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.*;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

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
		
		if (allEnabled()) {
			
			SelfCheckoutSession session = new SelfCheckoutSession(hardware,this);
			
			hardware.baggingArea.register(session);
			hardware.scanner.register(session);
			hardware.coinSlot.attach(session);
			hardware.coinValidator.attach(session);
			hardware.coinStorage.attach(session);
		}
		
	}
	private boolean allEnabled() {
		if ( !hardware.baggingArea.isDisabled() ) {
			if ( !hardware.baggingArea)
		}
			
	}
	
}
	
