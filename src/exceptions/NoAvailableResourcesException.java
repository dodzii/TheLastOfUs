package exceptions;

public class NoAvailableResourcesException extends GameActionException{
	
	//Constructors
	public NoAvailableResourcesException() {
		super("YOU  DON'T  HAVE  ENOUGH  RESOURCES");
	}
	
	public NoAvailableResourcesException(String s){
		super(s);
	}

}
