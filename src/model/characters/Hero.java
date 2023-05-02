package model.characters;

import java.awt.Point;
import java.util.*;
import engine.Game;
import exceptions.*;
import model.characters.*;
import model.collectibles.*;
import model.world.*;

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
	
	public void assignVisibilityAround() {
		int tmpx = this.getLocation().x;
		int tmpy = this.getLocation().y;
		int xr = tmpx + 1;
		int yu = tmpy + 1;
		int xl = tmpx - 1;
		int yd = tmpy - 1;
		
		if(xr>=0 && xr<=14) {
			Game.map[xr][tmpy].setVisible(true);
		}
		
		if(xl>=0 && xl<=14) {
			Game.map[xl][tmpy].setVisible(true);
		}
		
		if(yu>=0 && yu<=14) {
			Game.map[tmpx][yu].setVisible(true);
		}
		
		if(yd>=0 && yd<=14) {
			Game.map[tmpx][yd].setVisible(true);
		}
		
		if(xr>=0 && xr<=14 && xl>=0 && xl<=1 && yu>=0 && yu<=14 && yd>=0 && yd<=14) {
			Game.map[xl][yd].setVisible(true);
			Game.map[xr][yd].setVisible(true);
			Game.map[xl][yu].setVisible(true);
			Game.map[xr][yu].setVisible(true);
		}
		
	}
	
	public void useSpecial() throws GameActionException {
		if(!this.supplyInventory.isEmpty()) {
			if(this.actionsAvailable>0) {
				supplyInventory.get(0).use(this);
				this.setSpecialAction(true);
			}
			else {
				throw new NotEnoughActionsException();
			}
		}
		else {
			throw new NoAvailableResourcesException();
		}
	}
	
	public void cure() throws GameActionException {
		Character target=this.getTarget();
		if(target!= null && target instanceof Zombie ) {
			if(!this.vaccineInventory.isEmpty()) {
				Vaccine vaccine=this.vaccineInventory.get(0);
				if(this.checkAdjacency(target)) { //throws exception>>??
					vaccine.use(this);
					Game.zombies.remove(target);
					Point location=target.getLocation();
					Hero hero=Game.availableHeroes.remove(0);
					((CharacterCell)Game.map[location.x][location.y]).setCharacter(hero);
					hero.setLocation(location);
					hero.assignVisibilityAround();
				}
			}
			else {
				throw new NoAvailableResourcesException();
			}
		}
		else {
			throw new InvalidTargetException();
		}
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
	
	
	
}
