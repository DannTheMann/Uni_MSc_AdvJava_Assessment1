package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.utils.InternationalisationManager;

/**
 * Exception designed to handle any naming exceptions for
 * rooms, such as null names or empty Strings
 * @author dja33
 *
 */
public class InvalidRoomNamingException extends IllegalArgumentException {

	private static final long serialVersionUID = -3239624199648283428L;

	public InvalidRoomNamingException() {
		super(InternationalisationManager.im.getMessage("irne.msg"));
	}

}
