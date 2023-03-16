package model.world;

public abstract class Cell {
	
	private boolean isVisible;
	
	//Constructors
	public Cell() {	
	}

	//Getters & Setters
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
