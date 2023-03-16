package model.characters;

public class Zombie extends Character{
	private static int ZOMBIES_COUNT;
	
	//Constructors
	public Zombie() {
		super(("Zombie "+ZOMBIES_COUNT),40,10);
		ZOMBIES_COUNT++;
	}

	//Getters & Setters
	public static int getZOMBIES_COUNT() {
		return ZOMBIES_COUNT;
	}
	public static void setZOMBIES_COUNT(int zOMBIES_COUNT) {
		ZOMBIES_COUNT = zOMBIES_COUNT;
	}
	
}
