package model.characters;

import java.awt.Point;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

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

	public boolean checkAdjacency(Character target) {
		int x = this.getLocation().x;
		int y = this.getLocation().y;
		int xt = target.getLocation().x;
		int yt = target.getLocation().y;
		if((xt==x&&yt==y+1)||(xt==x&&yt==y-1)||(xt==x-1&&yt==y-1)||(xt==x-1&&yt==y)||(xt==x-1&&yt==y+1)||(xt==x+1&&yt==y-1)||(xt==x+1&&yt==y)||(xt==x+1&&yt==y+1))
			return true;
		else
			return false;
		
	}
	
	public void attack() throws InvalidTargetException, NotEnoughActionsException  {
		this.dealDamage(true);
	}
	
	public void defend() throws InvalidTargetException, NotEnoughActionsException {
		this.dealDamage(false);
	}
	
	public void dealDamage(boolean attack) throws InvalidTargetException, NotEnoughActionsException {
		Character target = this.target;
		if (target != null && checkAdjacency(target)) {
			int damage = (attack)? this.attackDmg :  (this.attackDmg/2);
			target.currentHp = target.currentHp - damage;
			if(target.currentHp==0) {
				target.onCharacterDeath();
			}
			else {
				if(attack) {
					target.setTarget(this);
					target.defend();
				}
			}
				
		}
		else {
			throw new InvalidTargetException();
		}
	}

	public void onCharacterDeath() {
		Game.map[location.x][location.y]=null;
		
	}
	
}
