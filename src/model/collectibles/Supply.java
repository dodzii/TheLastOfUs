package model.collectibles;

import java.util.ArrayList;
import exceptions.*;
import model.characters.*;

public class Supply implements Collectible{
	
	public Supply() {
		
	}

	public void pickUp(Hero h) {
		if(h!= null) {
		ArrayList<Supply> supplyInventory =h.getSupplyInventory();
		supplyInventory.add(this);
		}
	}

	public void use(Hero h) throws NoAvailableResourcesException {
		if(h!= null && !h.getSupplyInventory().isEmpty()) {
			ArrayList<Supply> supplyInventory =h.getSupplyInventory();
		if(supplyInventory.contains(this)) {
			supplyInventory.remove(this);
		}
		else {
			throw new NoAvailableResourcesException();
		}
		}
	}
	
}
