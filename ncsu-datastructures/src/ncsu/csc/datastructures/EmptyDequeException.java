package ncsu.csc.datastructures;
public class EmptyDequeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyDequeException() {

		super();

	}
	
	public EmptyDequeException(String err) {

		super(err);

	}

}
