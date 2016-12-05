package advjava.assessment1.zuul.refactored.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FontManager {
	
	private static FontManager fm;
	private static final double DEFAULT_FONT_SIZE = 12;
	private final String DEFAULT_FONT;
	private Map<String, Font> fonts;
	
	public FontManager(String defaultFont){
		fonts = new HashMap<String, Font>();
		DEFAULT_FONT = defaultFont;
		loadFonts();
		fm = this;
	}
	
	/**
	 * Load all default fonts
	 */
	private void loadFonts() {
		
		Font.getFamilies().stream()
			.forEach(f->fonts.put(f, Font.font(f, FontWeight.NORMAL, 12)));
				
		fonts.put("DEFAULT_FONT", Font.font(DEFAULT_FONT, FontWeight.NORMAL, DEFAULT_FONT_SIZE));
		fonts.put("EMPTY_FONT", Font.font(DEFAULT_FONT, FontWeight.NORMAL, 20));

	}

	public Font getFont(String key){
		Optional<Font> res = fonts.entrySet().stream().
					filter(k -> k.getKey().equals(key))
					.limit(1)
					.map(k->k.getValue())
					.findFirst();
		return res.isPresent() ? res.get() : getFont(DEFAULT_FONT);
	}
	
	public Font getFont(String key, FontWeight weight, int size){
		return Font.font(key, weight, size);
	}
	
	public static FontManager getFontManager(){
		return fm;
	}

}
