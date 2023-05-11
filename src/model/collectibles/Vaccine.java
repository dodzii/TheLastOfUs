package model.collectibles;

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.*;
import model.characters.*;
import model.world.CharacterCell;

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
		if(h!=null && !h.getVaccineInventory().isEmpty()) {
			ArrayList<Vaccine> vaccineInventory=h.getVaccineInventory();
			if(vaccineInventory.contains(this)) {
				vaccineInventory.remove(this);
				Point location=h.getTarget().getLocation();
				Game.zombies.remove(h.getTarget());
				Hero hero=Game.availableHeroes.remove(0);
				((CharacterCell)Game.map[location.x][location.y]).setCharacter(hero);
				hero.setLocation(location);
				Game.heroes.add(hero);
			}
			else {
				throw new NoAvailableResourcesException();
			}
		}	
	}
	
	
}
