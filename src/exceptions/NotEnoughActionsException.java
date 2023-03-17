package exceptions;

public class NotEnoughActionsException extends GameActionException{
	
	public NotEnoughActionsException() {
		super();
	}
	
	public NotEnoughActionsException(String s) {
		super(s);
	}

}
