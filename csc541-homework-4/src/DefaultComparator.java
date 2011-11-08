/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

import java.util.Comparator;

public class DefaultComparator<E> implements Comparator<E> {

	@SuppressWarnings("unchecked")
	public int compare(E a, E b) throws ClassCastException { 

		return ((Comparable<E>) a).compareTo(b);

	}

}

