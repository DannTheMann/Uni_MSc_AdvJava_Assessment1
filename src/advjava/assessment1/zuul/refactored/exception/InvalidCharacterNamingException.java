package advjava.assessment1.zuul.refactored.exception;

public class InvalidCharacterNamingException extends Exception {

	private static final long serialVersionUID = 5997800982833171060L;

	public InvalidCharacterNamingException() {
		super("Character was given a null or empty name.");
	}

}
