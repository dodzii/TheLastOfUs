package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class Zombie extends Character{
	private static int ZOMBIES_COUNT =1;
	
	//Constructors
	public Zombie() {
		super(("Zombie "+ZOMBIES_COUNT),40,10);
		ZOMBIES_COUNT++;
	}

	//Getters & Setters
	public static int getZOMBIES_COUNT() {
		return ZOMBIES_COUNT;
	}
	
	
	
	@Override
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		if(this.getTarget() instanceof Hero) {
			super.attack();
			}
			else {
				throw new InvalidTargetException();
			}
	}

	public void onCharacterDeath() {
		super.onCharacterDeath();
		Game.zombies.remove(this);
		Game.respawnZombie();
	}
}
