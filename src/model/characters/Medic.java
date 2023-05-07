package model.characters;

import exceptions.*;

public class Medic extends Hero{

	//Constructors
	public Medic() {
		
	}
	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	//Methods
	public void heal() throws GameActionException {
		if(this.isSpecialAction()) {
			if(!this.getSupplyInventory().isEmpty()) {
				if(this.getTarget()!=null && this.getTarget() instanceof Hero) {
					Hero target=(Hero) this.getTarget();
					this.getSupplyInventory().get(0).use(target);
					target.setCurrentHp(this.getMaxHp());
				}
				else {
					throw new InvalidTargetException("The target can't be zombie.");
				}
			}
			else {
				throw new NoAvailableResourcesException();
			}
		}
	}
	@Override
	public void useSpecial() throws GameActionException {
		if(this.getTarget() instanceof Hero) {
			if(!this.getSupplyInventory().isEmpty()) {
		super.useSpecial();
		this.heal();
			}
			else {
				throw new NoAvailableResourcesException();
			}
		}
		else {
			throw new InvalidTargetException();
		}
	}
	
	

}
