package advjava.assessment1.zuul.refactored.exception;

public class InvalidCharacterItemException extends Exception {

	private static final long serialVersionUID = -2995675081383414970L;

	public InvalidCharacterItemException() {
		super("Character interacted with an item that is null.");
	}

}
