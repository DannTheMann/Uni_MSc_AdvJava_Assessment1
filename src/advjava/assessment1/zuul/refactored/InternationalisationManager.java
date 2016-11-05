package advjava.assessment1.zuul.refactored;

import java.util.Locale;
import java.util.ResourceBundle;

public class InternationalisationManager {
	
	public static final InternationalisationManager im = new InternationalisationManager(Locale.getDefault());	
	private ResourceBundle bundle;
	
	private InternationalisationManager(Locale locale){
		System.out.println("LOCALE: " + locale.toString() + " - " + locale.getDisplayCountry());
		bundle = ResourceBundle.getBundle("Zuul_Messages");
	}
	
	public String getMessage(String messageKey){
		return bundle.getString(messageKey);
	}

}
