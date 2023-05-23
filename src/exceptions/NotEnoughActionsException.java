package exceptions;

public class NotEnoughActionsException extends GameActionException{
	
	public NotEnoughActionsException() {
		super("NOT   ENOUGH   ACTIONS!!");
	}
	
	public NotEnoughActionsException(String s) {
		super(s);
	}

}
