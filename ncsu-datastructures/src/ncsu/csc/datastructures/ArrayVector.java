package ncsu.csc.datastructures;
import java.util.Arrays;

import java.util.Iterator;

@SuppressWarnings("unchecked")

public class ArrayVector<E> implements Vector<E> {

	private E[] _Collection;

	private int _Capacity;

	private int _Size;
	
	public ArrayVector() { 
		
		setCapacity(16);
		
		setSize(0);
		
		setCollection((E[]) new Object[_Capacity]);

	}
	
	public ArrayVector(int capacity) { 
		
		setCapacity(capacity);
		
		setSize(0);
		
		setCollection((E[]) new Object[_Capacity]);

	}
	
	private E[] collection() {
		
		return _Collection;
		
	}
	
	private void setCollection(E[] collection) {
		
		_Collection = collection;
		
	}
	
	private int capacity() {
		
		return _Capacity;
		
	}
	
	private void setCapacity(int capacity) {
		
		_Capacity = capacity;
		
	}
	
	private void setSize(int size) {
		
		_Size = size;
	}

	public int size() {

		return _Size;

	}

	public boolean isEmpty() {

		return size() == 0; 

	}

	public E elementAtRank(int rank) throws BoundaryViolationException {

		checkRank(rank, size());

		return collection()[rank];

	}

	public E replaceAtRank(int rank, E element) throws BoundaryViolationException {

		checkRank(rank, size());

		E toReturn = collection()[rank];

		collection()[rank] = element;

		return toReturn;

	}
	
	public void insertAtRank(int rank, E element) throws BoundaryViolationException {

		checkRank(rank, size() + 1);

		if (size() == capacity()) {

			setCapacity(capacity() * 2);

			E[] newCollection = (E[]) new Object[capacity()];

			for (int i = 0; i < size(); i++) 

				newCollection[i] = collection()[i];

			setCollection(newCollection);

		}

		for (int i = size() - 1; i >= rank; i--)

			collection()[i+1] = collection()[i];

		collection()[rank] = element;

		setSize(size() + 1);

	}

	public E removeAtRank(int rank) throws BoundaryViolationException {

		checkRank(rank, size());

		E toReturn = collection()[rank];

		for (int i = rank; i < size() - 1; i++)

			collection()[i] = collection()[i+1];

		setSize(size() - 1);

		return toReturn;

	}

	protected void checkRank(int rank, int size) throws BoundaryViolationException {

		if (rank < 0 || rank >= size) throw new BoundaryViolationException();

	}

	@Override
	
	public Iterator<E> iterator() {

		return Arrays.asList(collection()).iterator();
	
	}
	
	public String toString() {
		
		String toReturn = "";
		
		for (int i = 0; i < size(); i++) {
			
			toReturn = toReturn + i + ": " + elementAtRank(i).toString() + "\n";
			
		}
		
		return toReturn;
		
	}

}