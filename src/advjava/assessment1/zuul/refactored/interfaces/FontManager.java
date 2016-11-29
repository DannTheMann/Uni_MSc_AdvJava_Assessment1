package advjava.assessment1.zuul.refactored.interfaces;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
		
                Font.getFamilies().stream()
                        .forEach(f->fonts.put(f, Font.font(f, FontWeight.NORMAL, 20)));
            
		fonts.put("DEFAULT_FONT", Font.font(DEFAULT_FONT, FontWeight.NORMAL, 20));
		fonts.put("text", Font.font("Verdana", FontWeight.NORMAL, 20));
	}

	public Font getFont(String key){
		Optional<Font> i =  fonts.entrySet().stream().
					filter(k -> k.getKey().equals(key))
					.map(k->k.getValue())
					.findFirst();
                return i.isPresent() ? i.get() : !i.isPresent() && !key.equals(DEFAULT_FONT) ? getFont(DEFAULT_FONT) : null;
					//.orElse(getFont(DEFAULT_FONT));
	}
}
