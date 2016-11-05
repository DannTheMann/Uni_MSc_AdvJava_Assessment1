package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.InternationalisationManager;

public class InvalidCharacterMoveException extends Exception {

	private static final long serialVersionUID = -1035827425896347317L;

	public InvalidCharacterMoveException() {
		super(InternationalisationManager.im.getMessage("icme.msg"));
	}

}
