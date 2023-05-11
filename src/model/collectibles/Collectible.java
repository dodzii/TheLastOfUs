package model.collectibles;
import exceptions.*;
import model.characters.*;

public interface Collectible {
	
	
	public void pickUp(Hero h);
	public void use(Hero h) throws NoAvailableResourcesException, InvalidTargetException;

}
