package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.utils.resourcemanagers.InternationalisationManager;

/**
 * Exception designed to handle any invalid interactions
 * with characters and item
 * @author dja33
 *
 */
public class InvalidCharacterItemException extends IllegalArgumentException {

	private static final long serialVersionUID = -2995675081383414970L;

	public InvalidCharacterItemException() {
		super(InternationalisationManager.im.getMessage("icie.msg"));
	}

}
