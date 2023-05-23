package exceptions;

public class MovementException extends GameActionException{
	
	public MovementException(){
		super("INVALID  MOVEMENT!!");
	}
	
	public MovementException(String s) {
		super(s);
	}
}
