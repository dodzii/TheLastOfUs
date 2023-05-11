package model.characters;

import exceptions.*;

public class Medic extends Hero {

	// Constructors
	public Medic() {

	}

	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}

	// Methods
	public void heal() throws GameActionException {
		Hero target = (Hero) this.getTarget();
		target.setCurrentHp(target.getMaxHp());
	}

	@Override
	public void useSpecial() throws GameActionException {
		if (this.getTarget() instanceof Hero && this.getTarget() != null
				&& (this.checkEqual() || this.checkAdjacency(this.getTarget()))) {
			super.useSpecial();
			this.heal();
		} else {
			throw new InvalidTargetException();
		}
	}

}
