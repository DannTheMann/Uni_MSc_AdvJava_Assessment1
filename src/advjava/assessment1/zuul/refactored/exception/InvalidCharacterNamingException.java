package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.InternationalisationManager;

public class InvalidCharacterNamingException extends Exception {

	private static final long serialVersionUID = 5997800982833171060L;

	public InvalidCharacterNamingException() {
		super(InternationalisationManager.im.getMessage("icne.msg"));
	}

}
