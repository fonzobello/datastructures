package ncsu.csc.datastructures;

public class EmptyTreeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyTreeException() {

		super();

	}
	
	public EmptyTreeException(String err) {

		super(err);

	}

}
