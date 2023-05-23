package exceptions;

public class InvalidTargetException extends GameActionException {
	
	public InvalidTargetException() {
		super("INVALID  TARGET!!");
	}
	
	public InvalidTargetException(String s) {
		super(s);
	}

}
