package advjava.assessment1.zuul.refactored;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class that creates it's own instance to handle
 * all forms of languages to be used within the game
 * 
 * Provides methods to get respective strings based on 
 * user language, will use the language provided by the locale
 * 
 * If the locale isn't supported, defaults to English-GB
 * @author dja33
 *
 */
public class InternationalisationManager {
	
	// Singleton reference
	public static final InternationalisationManager im = new InternationalisationManager(Locale.getDefault());
	// Resourcebundle to handle access to the language files
	private ResourceBundle bundle;
	
	/**
	 * Private constructor that creates the singleton 
	 * required for the InternationalisationManager
	 * @param locale The users default locale
	 */
	private InternationalisationManager(Locale locale){
		System.out.println("LOCALE: " + locale.toString() + " - " + locale.getDisplayCountry());
		bundle = ResourceBundle.getBundle("Zuul_Messages", locale);
	}
	
	/**
	 * Return a message based on a key to reference a string
	 * @param messageKey The string to look for
	 * @return The translated text
	 */
	public String getMessage(String messageKey){
		return bundle.getString(messageKey);
	}

}
