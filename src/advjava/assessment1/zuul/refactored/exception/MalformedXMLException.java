package advjava.assessment1.zuul.refactored.exception;

import advjava.assessment1.zuul.refactored.InternationalisationManager;

public class MalformedXMLException extends Exception {

	private static final long serialVersionUID = 5997800982833171060L;

	public MalformedXMLException(String fileName, String message) {
		super(String.format(InternationalisationManager.im.getMessage("xme.msg2"), fileName, message));
	}
	
	public MalformedXMLException(String fileName) {
		super(String.format(InternationalisationManager.im.getMessage("xme.msg2"), fileName));
	}

}
