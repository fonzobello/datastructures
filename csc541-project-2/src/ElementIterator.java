
import java.util.Iterator;

import java.util.NoSuchElementException;

import java.lang.UnsupportedOperationException;

public class ElementIterator<E> implements Iterator<E> {

	protected List<E> _List;

	protected Position<E> _Cursor;

	public ElementIterator(List<E> list) {
	
		setList(list);

		setCursor((list().isEmpty())? null : list().first());

	}
	
	private List<E> list() {
		
		return _List;
		
	}
	
	private Position<E> cursor() {
		
		return _Cursor;
		
	}
	
	private void setList(List<E> list) {
		
		_List = list;
		
	}
	
	private void setCursor(Position<E> cursor) {
		
		_Cursor = cursor;
		
	}

	public boolean hasNext() {
		
		return (cursor() != null);
		
	}

	public E next() throws NoSuchElementException {

		if (cursor() == null) throw new NoSuchElementException();

		E toReturn = cursor().element();

		setCursor((cursor() == list().last())? null : list().next(cursor()));

		return toReturn;

	}

	public void remove() throws UnsupportedOperationException {

		throw new UnsupportedOperationException();

	}

}

