//Robin Bowering UCID 30123373
//Matt Gibson UCID 30117091
//Kelvin Jamila UCID 30117164
//Nikki Kim UCID 30189188
//Hillary Nguyen UCID 30161137

package com.thelocalmarketplace.software;
import java.util.ArrayList;
import java.util.Scanner;

import com.jjjwelectronics.Item;

public class SelfCheckoutSession {
	ArrayList<Item> order = new ArrayList<Item>();
	

	Scanner userInput = new Scanner(System.in);
	
	private boolean isBlocked = false;
	
	//method to start the session
	public void StartSession() {
		return;
	}
	
	//method to add item to cart
	public void AddItem() {
		return;
	}
	
	//method to pay with coin
	public void PayWithCoin(int amount) {
		return;
	}
	
	/**Precondition: StartSession() has already been called**/
	//method for when weight discrepancy is detected
	public void WeightDiscrepancyDetected() {
		//block everything
		isBlocked = true;
		
		//notify customer and attendant screen of weight discrepancy
		System.out.println("Customer screen: Weight discrepancy has been detected.");
		System.out.println("Attendant screen: Weight discrepancy has been detected.");
		
		//let customer choose the options
		System.out.println("Customer screen: Please do one of the following:");
		System.out.println("1. Add or remove the item you just changed.");
		System.out.println("2. Select the do-not-bag request.");
		System.out.println("3. Ask attendant to override weight discrepancy.");
		System.out.println("Please enter a single number as your choice: ");
		
		while(true) {
			
			String response = userInput.next();
		
			if (response.equals("1")) {
				//has to check if weight discrepancy is gone
				//need listeners
				System.out.println("Checking...");
				return;
			}
			
			else if(response.equals("2")) {
				//select the do-not-bag-request
				System.out.println("Do-not-bag request selected...");
				isBlocked = false;
				return;
			}
			
			else if(response.equals("3")) {
				System.out.println("Attendant screen: Approve overide of weight discrepancy? (yes/no)");
				String attendantResponse = userInput.next();
				
				if(attendantResponse.equals("no")) {
					System.out.println("Override request has been denied.");
					return;
				}
			    else if (attendantResponse.equals("yes")) {
	                System.out.println("Override request has been approved.");
	                isBlocked = false;
	                return;
	            } 
			    
			    else {
	                System.out.println("Invalid response. Please enter 'yes' or 'no'.");
	                System.out.println("Returning to main menu...\n");
	                
	                System.out.println("Customer screen: Please do one of the following:");
					System.out.println("1. Add or remove the item you just changed.");
					System.out.println("2. Select the do-not-bag request.");
					System.out.println("3. Ask attendant to override weight discrepancy.");
					System.out.println("Please enter a single number as your choice: ");
	            }
			}
			
			else {
				System.out.println("Please enter a valid number.\n");
				
				System.out.println("Customer screen: Please do one of the following:");
				System.out.println("1. Add or remove the item you just changed.");
				System.out.println("2. Select the do-not-bag request.");
				System.out.println("3. Ask attendant to override weight discrepancy.");
				System.out.println("Please enter a single number as your choice: ");
			}
		
		}
		
	}

}
