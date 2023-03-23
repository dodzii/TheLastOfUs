package model.world;


import model.characters.Character;

public class CharacterCell extends Cell {
	
	private Character character;
	private boolean isSafe;
	
	//Constructors
	public CharacterCell() {
		
	}
	
	public CharacterCell(Character character) {
		super();
		this.character = character;
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
