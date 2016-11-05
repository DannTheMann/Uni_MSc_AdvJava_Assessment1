package advjava.assessment1.zuul.refactored;

import java.util.ArrayList;

public class PrintableList<E> extends ArrayList<E> {

	private static final long serialVersionUID = -8211249045502112361L;

	public String toString(){
		// Use stringbuilder, faster
		if(isEmpty())
			return InternationalisationManager.im.getMessage("print.empty");
		String str = "";
		for(Object e : toArray()){
			str = e + ", ";
		}
		return str.substring(0, str.length()-2);
		
	}
	
}
