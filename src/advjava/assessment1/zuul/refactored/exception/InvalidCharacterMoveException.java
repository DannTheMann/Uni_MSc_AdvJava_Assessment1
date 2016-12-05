package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;

/**
 * Exception designed to handle any invalid interactions
 * with characters and movement between rooms
 * @author dja33
 *
 */
public class InvalidCharacterMoveException extends IllegalArgumentException {

	private static final long serialVersionUID = -1035827425896347317L;

	public InvalidCharacterMoveException() {
		super(InternationalisationManager.im.getMessage("icme.msg"));
	}

}
