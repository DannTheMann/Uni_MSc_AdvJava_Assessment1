package advjava.assessment1.zuul.refactored.exception;

public class MalformedXMLException extends Exception {

	private static final long serialVersionUID = 5997800982833171060L;

	public MalformedXMLException(String fileName, String message) {
		super("XML File '" + fileName + "' is malformed -> " + message);
	}
	
	public MalformedXMLException(String fileName) {
		super("XML File '" + fileName + "' is malformed, make sure you have the correct nodes and text values for the specific models.");
	}

}
