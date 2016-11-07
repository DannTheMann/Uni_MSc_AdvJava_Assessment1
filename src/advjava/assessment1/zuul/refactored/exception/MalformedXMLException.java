package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.InternationalisationManager;

/**
 * Exception to handle any invalid XML files used for
 * rooms, characters or items. Designed to be thrown
 * if an actual XML file is incorrectly formatted as
 * well as if the file does not contain correct nodes for certain
 * elements.
 * 
 * e.g Room must have a node called Name otherwise throw this exception
 * @author dja33
 *
 */
public class MalformedXMLException extends Exception {

	private static final long serialVersionUID = 5997800982833171060L;

	public MalformedXMLException(String fileName, String message) {
		super(String.format(InternationalisationManager.im.getMessage("mxe.msg2"), fileName, message));
	}
	
	public MalformedXMLException(String fileName) {
		super(String.format(InternationalisationManager.im.getMessage("mxe.msg2"), fileName));
	}

}
