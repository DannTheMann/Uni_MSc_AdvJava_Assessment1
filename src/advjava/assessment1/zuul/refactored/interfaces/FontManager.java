package advjava.assessment1.zuul.refactored.interfaces;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FontManager {
	
	private final String DEFAULT_FONT;
	private Map<String, Font> fonts;
	
	public FontManager(String defaultFont){
		fonts = new HashMap<String, Font>();
		DEFAULT_FONT = defaultFont;
		loadFonts();
	}
	
	private void loadFonts() {
		
		fonts.put("DEFAULT_FONT", Font.font(DEFAULT_FONT, FontWeight.NORMAL, 20));
		fonts.put("text", Font.font("Verdana", FontWeight.NORMAL, 20));
		fonts.put("text", Font.font("arial", FontWeight.NORMAL, 20));
	}

	public Font getFont(String key){
		return fonts.entrySet().stream().
					filter(k -> k.getKey().equals(key))
					.limit(1)
					.map(k->k.getValue())
					.findFirst()
					.orElse(getFont(DEFAULT_FONT));
	}

}
