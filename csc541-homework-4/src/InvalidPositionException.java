/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

public class InvalidPositionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPositionException() {

		super();

	}
	
	public InvalidPositionException(String err) {

		super(err);

	}

}
