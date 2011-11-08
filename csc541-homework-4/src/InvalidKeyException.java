/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

public class InvalidKeyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidKeyException() {

		super();

	}
	
	public InvalidKeyException(String err) {

		super(err);

	}

}
