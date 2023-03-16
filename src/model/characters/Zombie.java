package model.characters;

public class Zombie extends Character{
	private static int ZOMBIES_COUNT;
	
	public Zombie() {
		super(("Zombie "+ZOMBIES_COUNT),40,10);
		ZOMBIES_COUNT++;
	}

	public static int getZOMBIES_COUNT() {
		return ZOMBIES_COUNT;
	}

	public static void setZOMBIES_COUNT(int zOMBIES_COUNT) {
		ZOMBIES_COUNT = zOMBIES_COUNT;
	}
	
	
}
