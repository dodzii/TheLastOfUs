package model.world;

public class TrapCell extends Cell{
	
	private int trapDamage;
	
	//Constructors
	public TrapCell() {
		super();
		int[] arr = {10,20,30};
		this.trapDamage = arr[(int)Math.random()*2+1];
	}

	//Getters & Setters
	public int getTrapDamage() {
		return trapDamage;
	}

}
