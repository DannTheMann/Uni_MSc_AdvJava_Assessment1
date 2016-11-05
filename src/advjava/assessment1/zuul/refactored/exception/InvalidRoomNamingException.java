package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.InternationalisationManager;

public class InvalidRoomNamingException extends Exception {

	private static final long serialVersionUID = -3239624199648283428L;

	public InvalidRoomNamingException() {
		super(InternationalisationManager.im.getMessage("irne.msg"));
	}

}
