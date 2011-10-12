package ncsu.csc.datastructures;

public class LinkedStack<E> implements Stack<E> {

	protected List<E> _Collection;

	public LinkedStack() {

		_Collection = new LinkedList<E>();

	}

	public int size() {
		
		return _Collection.size();
		
	}

	public boolean isEmpty() {

		return _Collection.isEmpty();

	}

	public void push(E element) {

		_Collection.insertFirst(element);

	}

	public E top() throws EmptyStackException {

		if (isEmpty()) throw new EmptyStackException();

		return _Collection.first().element();

	}

	public E pop() throws EmptyStackException {

		if (isEmpty()) throw new EmptyStackException();

		return _Collection.remove(_Collection.first());

	}

}

