package model.characters;

import java.awt.Point;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

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

	public boolean checkAdjacency (Character target) {
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
//		this.dealDamage(true);
		Character tmp = this.getTarget();
		if (tmp != null && this.checkAdjacency(tmp)) {
			int damage =  this.getAttackDmg() ;
			tmp.setCurrentHp(tmp.getCurrentHp()- damage);
			tmp.defend(this);
			
			if(tmp.getCurrentHp()<=0) {
				tmp.onCharacterDeath();
			}
		
		}
		else {
			throw new InvalidTargetException();
		}
		
	}
	
	public void defend(Character c) throws InvalidTargetException, NotEnoughActionsException {
//		this.dealDamage(false);
		if (c != null && checkAdjacency(c)) {
			int damage =  (this.getAttackDmg()/2);
			c.setCurrentHp(c.getCurrentHp()- damage);
			if(c.getCurrentHp()<=0) {
				c.onCharacterDeath();
			}
		
		}
		else {
			throw new InvalidTargetException();
		}
		
	}
	
//	public void dealDamage(boolean attack) throws InvalidTargetException, NotEnoughActionsException {
//		Character target = this.getTarget();
//		if (target != null && checkAdjacency(target)) {
//			int damage = (attack)? this.attackDmg :  (this.attackDmg/2);
//			target.setCurrentHp(target.getCurrentHp()- damage);
//			Character tmp=this.getTarget();
//			if(attack) {
//				target.setTarget(this);
//				target.defend();
//			}
//			if(tmp.getCurrentHp()==0) {
//				tmp.onCharacterDeath();
//			}
//		
//		}
//		else {
//			throw new InvalidTargetException();
//		}
//	}

	public void onCharacterDeath() {
		Point p = this.getLocation();
		Game.map[p.x][p.y] = new CharacterCell(null);
		
	}
	
}
