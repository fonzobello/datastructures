package ncsu.csc.datastructures;

public class EmptyListException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyListException() {

		super();

	}
	
	public EmptyListException(String err) {

		super(err);

	}

}
