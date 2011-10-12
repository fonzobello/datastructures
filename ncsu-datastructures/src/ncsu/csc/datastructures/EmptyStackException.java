package ncsu.csc.datastructures;

public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyStackException() {

		super();

	}
	
	public EmptyStackException(String err) {

		super(err);

	}
	
}
