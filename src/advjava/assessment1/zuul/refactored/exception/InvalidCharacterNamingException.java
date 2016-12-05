package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;

/**
 * Exception designed to handle any naming exceptions for
 * characters, such as null names or empty Strings
 * @author dja33
 *
 */
public class InvalidCharacterNamingException extends IllegalArgumentException {

	private static final long serialVersionUID = 5997800982833171060L;

	public InvalidCharacterNamingException() {
		super(InternationalisationManager.im.getMessage("icne.msg"));
	}

}
