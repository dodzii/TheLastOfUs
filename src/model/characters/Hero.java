package model.characters;

import java.awt.Point;
import java.util.*;
import engine.*;
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
	
	//Methods
	public void move(Direction d) throws MovementException,NotEnoughActionsException {
		Point location = this.getLocation();
		Point tmp = new Point(location.x,location.y);
		if (this.getActionsAvailable() == 0) {
			throw new NotEnoughActionsException();
		}
		switch(d) {
			case UP: tmp.x++; break;
			case DOWN: tmp.x--;break;
			case LEFT: tmp.y--;break;
			case RIGHT: tmp.y++;break;
		}
		if((tmp.x)<=14 &&(tmp.x)>=0&& (tmp.y)>=0&& (tmp.y<=14))  {
			if(Game.map[tmp.x][tmp.y] instanceof TrapCell) {
				int Damage = ((TrapCell)(Game.map[tmp.x][tmp.y])).getTrapDamage();
				this.setCurrentHp(this.getCurrentHp()-Damage);
				if(this.getCurrentHp()==0) {
					this.onCharacterDeath();
				}
				else {
					Game.map[tmp.x][tmp.y] = new CharacterCell(this);
					((CharacterCell)Game.map[location.x][location.y]).setCharacter(null) ;
					this.setLocation(tmp);
					this.actionsAvailable--;
					this.assignVisibilityAround();
				}
			}
			else if(Game.map[tmp.x][tmp.y] instanceof CollectibleCell){
				Collectible want = ((CollectibleCell)(Game.map[tmp.x][tmp.y])).getCollectible();
				want.pickUp(this);
				Game.map[tmp.x][tmp.y] = new CharacterCell(this);
				((CharacterCell)Game.map[location.x][location.y]).setCharacter(null) ;
				this.setLocation(tmp);
				this.actionsAvailable--;
				this.assignVisibilityAround();
			}
			else if(Game.map[tmp.x][tmp.y] instanceof CharacterCell&&((CharacterCell)Game.map[tmp.x][tmp.y]).getCharacter() == null) {
				Game.map[tmp.x][tmp.y] = new CharacterCell(this);
				((CharacterCell)Game.map[location.x][location.y]).setCharacter(null) ;
				this.setLocation(tmp);
				this.actionsAvailable--;
				this.assignVisibilityAround();
			}
			else {
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
		
		if(xr>=0 && xr<=14 && Game.map[xr][tmpy] !=null) {
			Game.map[xr][tmpy].setVisible(true);
		}
		
		if(xl>=0 && xl<=14 && Game.map[xl][tmpy] !=null) {
			Game.map[xl][tmpy].setVisible(true);
		}
		
		if(yu>=0 && yu<=14 && Game.map[tmpx][yu]!=null) {
			Game.map[tmpx][yu].setVisible(true);
		}
		
		if(yd>=0 && yd<=14 && Game.map[tmpx][yd]!=null) {
			Game.map[tmpx][yd].setVisible(true);
		}
		
		if(xr>=0 && xr<=14 && yu>=0 && yu<=14 &&Game.map[xr][yu]!=null) {
			Game.map[xr][yu].setVisible(true);
		}
		
		if(xr>=0 && xr<=14 && yd>=0 && yd<=14 && Game.map[xr][yd]!=null) {
			Game.map[xr][yd].setVisible(true);
		}
		
		if(xl>=0 && xl<=14 && yu>=0 && yu<=14 && Game.map[xl][yu]!=null) {
			Game.map[xl][yu].setVisible(true);
		}
		
		if(xl>=0 && xl<=14 && yd>=0 && yd<=14 &&Game.map[xl][yd]!=null) {
			Game.map[xl][yd].setVisible(true);
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
		if(target!= null && target instanceof Zombie && this.checkAdjacency(target)) {
			if(!this.vaccineInventory.isEmpty()) {
				Vaccine vaccine=this.vaccineInventory.get(0);
					if(actionsAvailable > 0)
					{
						this.actionsAvailable--;
						vaccine.use(this);
						Game.zombies.remove(target);
						Point location=target.getLocation();
						Hero hero=Game.availableHeroes.remove(0);
						((CharacterCell)Game.map[location.x][location.y]).setCharacter(hero);
						hero.setLocation(location);
						hero.assignVisibilityAround();
					}
					else
						throw new NotEnoughActionsException();
			}
			else {
				throw new NoAvailableResourcesException();
			}
		}
		else {
			throw new InvalidTargetException();
		}
	}
	
	public void attack() throws InvalidTargetException , NotEnoughActionsException  {
		if(actionsAvailable > 0)
		{
			super.attack();
			actionsAvailable--;
		}
		else
			throw new NotEnoughActionsException();
	}
	
	public void onCharacterDeath() {
		super.onCharacterDeath();
		Game.heroes.remove(this);
		Game.availableHeroes.add((Hero)this);
	}	
}
