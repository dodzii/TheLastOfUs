package model.world;

public class CharacterCell extends Cell {
	
	private Character character;
	private boolean isSafe;
	
	//Constructors
	public CharacterCell(Character character, boolean isSafe) {
		super();
		this.character = character;
		this.isSafe = isSafe;
	}
	
	//Getters & Setters
	public Character getCharacter() {
		return character;
	}
	public void setCharacter(Character character) {
		this.character = character;
	}
	public boolean isSafe() {
		return isSafe;
	}
	public void setSafe(boolean isSafe) {
		this.isSafe = isSafe;
	}
	
	
	
	

}
