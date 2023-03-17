package exceptions;

public class NoAvailableResourcesException extends GameActionException{
	
	//Constructors
	public NoAvailableResourcesException() {
		super();
	}
	
	public NoAvailableResourcesException(String s){
		super(s);
	}

}
