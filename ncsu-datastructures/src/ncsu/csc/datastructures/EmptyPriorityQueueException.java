package ncsu.csc.datastructures;

public class EmptyPriorityQueueException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyPriorityQueueException() {

		super();

	}
	
	public EmptyPriorityQueueException(String err) {

		super(err);

	}
	
}
