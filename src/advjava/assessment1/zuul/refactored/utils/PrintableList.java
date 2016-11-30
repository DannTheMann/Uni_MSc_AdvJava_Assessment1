package advjava.assessment1.zuul.refactored.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Simple adaption of the ArrayList that allows for smart formatting and
 * printing of the list of objects
 * 
 * @author Daniel
 *
 * @param <E>
 *            Template for Elements to store
 */
public class PrintableList<E> extends ArrayList<E> {

	// Serialisation implementation if needed
	private static final long serialVersionUID = -8211249045502112361L;

	/**
	 * Overrided toString()
	 * 
	 * Prints the contents of the collection, if empty returns N/A
	 * 
	 * @return String of objects joined by ", "
	 */
	@Override
	public String toString() {
		if (isEmpty())
			return InternationalisationManager.im.getMessage("print.empty");
		return stream().map(o -> o.toString()).collect(Collectors.joining(", "));
	}

	public static <T> Collection<T> fromCollection(Collection<T> values) {
		return values.stream().collect(Collectors.toCollection(PrintableList::new));
	}

}
