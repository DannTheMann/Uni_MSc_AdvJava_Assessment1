package advjava.assessment1.zuul.refactored.exception;

public class InvalidRoomNamingException extends Exception {

	private static final long serialVersionUID = -3239624199648283428L;

	public InvalidRoomNamingException() {
		super("Room was given a null or empty String for direction.");
	}

}
