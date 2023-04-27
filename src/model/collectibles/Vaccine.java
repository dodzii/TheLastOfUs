package model.collectibles;

import java.util.ArrayList;
import exceptions.*;
import model.characters.*;

public class Vaccine implements Collectible{
	
	public Vaccine() {
		
	}

	public void pickUp(Hero h) {
		if(h!=null) {
			ArrayList<Vaccine> vaccineInventory=h.getVaccineInventory();
			vaccineInventory.add(this);
		}	
	}

	public void use(Hero h) throws NoAvailableResourcesException {
		if(h!=null) {
			ArrayList<Vaccine> vaccineInventory=h.getVaccineInventory();
			if(vaccineInventory.contains(this)) {
				vaccineInventory.remove(this);
			}
			else {
				throw new NoAvailableResourcesException();
			}
		}	
	}
	
	
}
