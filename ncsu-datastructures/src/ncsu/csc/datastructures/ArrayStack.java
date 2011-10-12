package ncsu.csc.datastructures;

import java.util.ArrayList;

public class ArrayStack<E> implements Stack<E> {

	protected ArrayList<E> _Collection;

	protected int _Top = -1;

	public ArrayStack() {}

	public int size() { 

		return (_Top + 1);

	}

	public boolean isEmpty() {

		return (_Top < 0);

	}

	public void push(E element) {

		_Collection.add(_Top++, element);

	}

	public E top() throws EmptyStackException {

		if (isEmpty()) throw new EmptyStackException();

		return _Collection.get(_Top);

	}

	public E pop() throws EmptyStackException {

		if (isEmpty()) throw new EmptyStackException();

		_Top --;
		
		return _Collection.remove(_Top + 1);

	}

}
