package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.InternationalisationManager;

public class InvalidCharacterItemException extends Exception {

	private static final long serialVersionUID = -2995675081383414970L;

	public InvalidCharacterItemException() {
		super(InternationalisationManager.im.getMessage("icie.msg"));
	}

}
