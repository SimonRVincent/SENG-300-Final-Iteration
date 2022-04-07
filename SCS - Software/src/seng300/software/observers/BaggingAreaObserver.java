package seng300.software.observers;

import java.util.ArrayList;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import seng300.software.SelfCheckoutSystemLogic;

public class BaggingAreaObserver implements ElectronicScaleObserver
{
	private SelfCheckoutSystemLogic logic;
	private double weightAtLastEvent;
	private boolean currentItemBagged = true;
	private boolean currentItemRemoved = false;
	
	private boolean baggingItems = true; //false means we are removing items

	private Thread checkProductBagggedby5Thread;
	private BarcodedProduct currentScannedProduct;
	private ArrayList<BarcodedProduct> scannedProducts = new ArrayList<>();
	private ArrayList<BarcodedProduct> baggedProducts = new ArrayList<>();
	private boolean timedOut = false;
	
	public boolean isBaggingItems() {
		return baggingItems;
	}

	public void setBaggingItems(boolean baggingItems) {
		this.baggingItems = baggingItems;
	}
	
	public boolean isTimedOut() {
		return timedOut;
	}

	public void setTimedOut(boolean timedOut) {
		this.timedOut = timedOut;
	}

	public ArrayList<BarcodedProduct> getScannedProducts() {
		return scannedProducts;
	}

	public ArrayList<BarcodedProduct> getBaggedProducts() {
		return baggedProducts;
	}

	public BaggingAreaObserver(SelfCheckoutSystemLogic logic)
	{
		this.logic = logic;
		weightAtLastEvent = 0;
	}
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams) {
		
		if(weightAtLastEvent < weightInGrams && baggingItems)	
		{
			if(currentItemBagged == true) {
				// there is no scanned item waiting to be bagged so
				blockScs();	
			}else {
				double itemWeight = (weightInGrams - weightAtLastEvent );
				
				weightAtLastEvent = weightInGrams;
				
				double difference =  Math.abs(currentScannedProduct.getExpectedWeight() - itemWeight);
				
				//double sensitivity = scale.getSensitivity();
				
				if (difference < 1E-10)  {
					
					baggedProducts.add(currentScannedProduct);
					currentItemBagged = true;
					
					if(weightAtLastEvent <= scale.getWeightLimit()) {
						// if scale is not overloaded enable scanners again 
						unBlocsScs();
					}
					
				}else {
					// unknown item placed in bagging area
					 blockScsUnexpected();

				}
			}	
			
		} //to test: this else if
		else if (weightAtLastEvent > weightInGrams && !baggingItems) { //customer wants to remove an item from the baggedArea
			if (this.currentItemRemoved) {
				// there is no item waiting to be removed
				blockScs();
			}
			else {
				double itemWeight = (weightInGrams - weightAtLastEvent );
				
				weightAtLastEvent = weightInGrams;
				
				double difference =  Math.abs(currentScannedProduct.getExpectedWeight() - itemWeight);
				
				if (difference < 1E-10)  {
					
					baggedProducts.remove(currentScannedProduct);
					currentItemRemoved = true;
					
					unBlocsScs();
					
				}else {
					// unknown item removed from bagging area
					blockScs();

				}
			}
		} 
		
		else {
			blockScs();
		}

		
	}

	@Override
	public void overload(ElectronicScale scale) {
		// weight on scale has exceeded limit
		blockScs();

	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		// TODO Auto-generated method stub

	}
	
	public void notifiedItemAdded(BarcodedProduct scannedProduct)
	{

		// wait 5 seconds -- Threads
		// if not notified weight change, block system
					
		if (checkProductBagggedby5Thread != null && checkProductBagggedby5Thread.isAlive()) {
			checkProductBagggedby5Thread.interrupt();
		}

		if(scannedProduct.getExpectedWeight() > logic.getBaggingAreaSensitivity()) {
			// disable scanners until item placed in bagging area
			blockScs();
			
			currentScannedProduct = scannedProduct;
			scannedProducts.add(scannedProduct);
			currentItemBagged = false;
			
			Runnable  checkProductBaggged = new CheckBaggedProduct(scannedProduct, this);
			checkProductBagggedby5Thread = new Thread(checkProductBaggged);
			checkProductBagggedby5Thread.setDaemon(true);
			checkProductBagggedby5Thread.start();	
			
			
		}else {				
			// does not need to be placed in the bagging area
		}		
	}
	//to test
	public void wishesToRemoveItem(BarcodedProduct scannedProduct) { //only removes item from bagging area, not checkout of stuff to pay for
		// wait 5 seconds -- Threads
		// if not notified weight change, block system
		
		if (checkProductBagggedby5Thread != null && checkProductBagggedby5Thread.isAlive()) {
			checkProductBagggedby5Thread.interrupt();
		}

		if(scannedProduct.getExpectedWeight() > logic.getBaggingAreaSensitivity()) {
			// disable scanners until item placed in bagging area
			blockScs();
			
			currentScannedProduct = scannedProduct;
			//scannedProducts.add(scannedProduct);  if we want to remove the item from cost as well, uncomment
			currentItemRemoved = false;
			
			Runnable  checkProductBaggged = new CheckBaggedProduct(scannedProduct, this, false);
			checkProductBagggedby5Thread = new Thread(checkProductBaggged);
			checkProductBagggedby5Thread.setDaemon(true);
			checkProductBagggedby5Thread.start();	
			
			
		}else {				
			// if the item weighs less than the scale's sensitivity, it is ignored
			// does not need to be placed in the bagging area
		}
	}
	
	public boolean isCurrentItemBagged() {
		return currentItemBagged;
	}
	
	public boolean isCurrentItemRemoved() {
		return this.currentItemRemoved;
	}

	public void blockScs() {
		logic.block();
		
	}
	
	public void unBlocsScs() {
		logic.unblock();
	}
  public void blockScsUnexpected() {
		logic.blockUnexpectedWeight();
		
	}
}

