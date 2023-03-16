package model.world;

public class TrapCell extends Cell{
	private int trapDamage;
	

	public TrapCell(int trapDamage) {
		super();
		int[] arr = {10,20,30};
		this.trapDamage = arr[(int)Math.random()*2+1];
	}

	public int getTrapDamage() {
		return trapDamage;
	}

	
	

}
