/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

public class InvalidEntryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidEntryException() {

		super();

	}
	
	public InvalidEntryException(String err) {

		super(err);

	}

}
