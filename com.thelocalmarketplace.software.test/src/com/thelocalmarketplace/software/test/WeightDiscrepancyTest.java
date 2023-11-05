package com.thelocalmarketplace.software.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinSlot;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.SelfCheckoutController;
import com.thelocalmarketplace.software.SelfCheckoutSession;

import powerutility.PowerGrid;

public class WeightDiscrepancyTest {
	

	Numeral[] testNumeralArray1 = {Numeral.one} ;
	Barcode testBarcode1 = new Barcode(testNumeralArray1);
	BarcodedItem testItem1 = new BarcodedItem(testBarcode1, Mass.ONE_GRAM);
	BarcodedProduct testProduct1 = new BarcodedProduct(testBarcode1, "1 ml of Water", 1, Mass.ONE_GRAM.inGrams().doubleValue());
	
	Numeral[] testNumeralArray2 = {Numeral.two} ;
	Barcode testBarcode2 = new Barcode(testNumeralArray2);
	BarcodedItem testItem2 = new BarcodedItem(testBarcode2, Mass.ONE_GRAM.sum(Mass.ONE_GRAM));
	BarcodedProduct testProduct2 = new BarcodedProduct(testBarcode2, "1 ml of Milk", 2, Mass.ONE_GRAM.sum(Mass.ONE_GRAM).inGrams().
			doubleValue());
	
		
	
	SelfCheckoutStation hardware;
	SelfCheckoutSession session;
	Coin coin;
	

	
	
	
	
	
	@Before 
	public void setup() {
		hardware = new SelfCheckoutStation();
        hardware.plugIn(PowerGrid.instance());
        hardware.turnOn();
        
        SelfCheckoutController controller = new SelfCheckoutController(hardware);
        
		session = new SelfCheckoutSession(hardware, controller);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(testBarcode1, testProduct1);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(testBarcode2, testProduct2);
		

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
	
	@Test
	public void triggerDiscrepancyUnexpectedItem() {
		hardware.baggingArea.addAnItem(testItem1);
		
		assertTrue(session.weightDiscrepancy);
	}
	@Test
	public void resolveDiscrepancyUnexpectedItem() {
		hardware.baggingArea.addAnItem(testItem1);
		hardware.baggingArea.removeAnItem(testItem1);
		assertFalse(session.weightDiscrepancy);
	}
	@Test
	public void triggerDiscrepancyItemRemoved() {
		
	}
	
	@After
	public void clearDatabase() {
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
	}
	
}
