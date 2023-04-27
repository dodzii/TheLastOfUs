package model.characters;

import java.awt.Point;

public abstract class Character {
	
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;
	
	//Constructors
	public Character() {
		
	}
	public Character(String name, int maxHp, int attackDmg) {
		this.name = name;
		this.maxHp = maxHp;
		this.attackDmg = attackDmg;
		this.currentHp=maxHp;
	}
	
	//Getters & Setters
	public String getName() {
		return name;
	}
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public int getCurrentHp() {
		return currentHp;
	}
	public void setCurrentHp(int currentHp) {
		if(currentHp <= this.maxHp) {
			if(currentHp<0)
				this.currentHp = 0;
			else 
				this.currentHp = currentHp;
		}
		
	}
	public int getAttackDmg() {
		return attackDmg;
	}
	public Character getTarget() {
		return target;
	}
	public void setTarget(Character target) {
		this.target = target;
	}


	
	
}
