package model.characters;

import java.awt.Point;
import java.util.*;

import engine.Game;
import exceptions.MovementException;
import model.collectibles.*;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public abstract class Hero extends Character{
	
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
    private ArrayList<Supply> supplyInventory ;
    
   //Constructors
    public Hero() {
    	
    }
    
    public Hero(String name, int maxHp, int attackDmg, int maxActions){
    	super(name,maxHp,attackDmg);
    	this.maxActions=maxActions;
    	this.actionsAvailable=maxActions;
    	this.vaccineInventory=new ArrayList<Vaccine>();
    	this.supplyInventory=new ArrayList<Supply>();
    }

    //Getters & Setters
	public int getActionsAvailable() {
		return actionsAvailable;
	}
	
	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public int getMaxActions() {
		return maxActions;
	}
	
	public boolean isSpecialAction() {
		return specialAction;
	}
	
	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}
	
	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}
	
	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}
	
	
	public void move(Direction d) throws MovementException {
		Point location = this.getLocation();
		Point tmp = new Point(location.x,location.y);
		switch(d) {
			case UP: tmp.y++; break;
			case DOWN: tmp.y--;break;
			case LEFT: tmp.x--;break;
			case RIGHT: tmp.x++;break;
		}
		if((tmp.x)<=14 &&(tmp.x)>=0&& (tmp.y)>=0&& (tmp.y<=14))  {
			if(Game.map[tmp.x][tmp.y] instanceof TrapCell) {
				int Damage = ((TrapCell)(Game.map[tmp.x][tmp.y])).getTrapDamage();
				this.setCurrentHp(this.getCurrentHp()-Damage);
				this.setLocation(tmp);
				this.actionsAvailable--;
			}
			else if(Game.map[tmp.x][tmp.y] instanceof CollectibleCell){
				Collectible want = ((CollectibleCell)(Game.map[tmp.x][tmp.y])).getCollectible();
				want.pickUp(this);
				this.setLocation(tmp);
				this.actionsAvailable--;
			}
			else if(Game.map[tmp.x][tmp.y] instanceof CharacterCell){
				throw new MovementException();
			}
		}
		else {
			throw new MovementException();
		}	
	}
}
