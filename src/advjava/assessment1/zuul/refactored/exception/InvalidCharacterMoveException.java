package advjava.assessment1.zuul.refactored.exception;

public class InvalidCharacterMoveException extends Exception {

	private static final long serialVersionUID = -1035827425896347317L;

	public InvalidCharacterMoveException() {
		super("Character attempted to move to a room that is null.");
	}

}
