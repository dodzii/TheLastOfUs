package model.world;

import model.collectibles.*;

public class CollectibleCell extends Cell {
	
	private Collectible collectible;
	
	//Constructors
	public CollectibleCell() {
		
	}
	
	public CollectibleCell(Collectible collectible) {
		super();
		this.collectible = collectible;
	}

	//Getters & Setters
	public Collectible getCollectible() {
		return collectible;
	}

}
