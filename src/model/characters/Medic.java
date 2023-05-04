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
	public void heal() throws InvalidTargetException ,NoAvailableResourcesException {
		if(this.isSpecialAction()) {
			if(!this.getSupplyInventory().isEmpty()) {
				if(this.getTarget()!=null && this.getTarget() instanceof Hero) {
					Hero target=(Hero) this.getTarget();
					this.getSupplyInventory().get(0).use(target);
					target.setActionsAvailable(target.getMaxActions());
				}
				else {
					throw new InvalidTargetException();
				}
			}
			else {
				throw new NoAvailableResourcesException();
			}
		}
	}
	@Override
	public void useSpecial() throws GameActionException {
		super.useSpecial();
		this.heal();
	}
	
	

}
