package ncsu.csc.datastructures;

import java.util.Iterator;

public class LinkedSequence<E> implements Sequence<E> {

	private List<E> _Collection;
	
	public LinkedSequence() {
		
		setCollection(new LinkedList<E>());
		
	}
	
	public LinkedSequence(List<E> collection) {
		
		setCollection(collection);
		
	}
	
	private void setCollection(List<E> collection) {
		
		_Collection = collection;
	
	}

	private List<E> collection() {
		
		return _Collection;
	
	}
	
	private void checkRank(int rank, int size) throws BoundaryViolationException {

		if (rank < 0 || rank >= size) throw new BoundaryViolationException();
		
	}
	
	@Override
	public int size() {

		return collection().size();
		
	}

	@Override
	public boolean isEmpty() {

		return collection().isEmpty();
		
	}

	@Override
	public void insertAtRank(int rank, E element) throws BoundaryViolationException {

		checkRank(rank, size() + 1);

		if (rank == size()) collection().insertLast(element);

		else collection().insertBefore(positionAtRank(rank), element);

	}

	@Override
	public E elementAtRank(int rank) throws BoundaryViolationException {

		return positionAtRank(rank).element();
		
	}

	@Override
	public E removeAtRank(int rank) throws BoundaryViolationException {

		return collection().remove(positionAtRank(rank));
		
	}

	@Override
	public E replaceAtRank(int rank, E element) throws BoundaryViolationException {

		return collection().replace(positionAtRank(rank), element);
		
	}

	@Override
	public Position<E> first() {

		return collection().first();
		
	}

	@Override
	public Position<E> last() {

		return collection().last();
		
	}

	@Override
	public Position<E> next(Position<E> position) throws InvalidPositionException, BoundaryViolationException {

		return collection().next(position);
		
	}

	@Override
	public Position<E> previous(Position<E> position) throws InvalidPositionException, BoundaryViolationException {

		return collection().previous(position);
		
	}
	
	@Override
	public boolean hasNext(Position<E> position) throws InvalidPositionException {

		return collection().hasNext(position);
		
	}

	@Override
	public boolean hasPrevious(Position<E> position) throws InvalidPositionException {
		
		return collection().hasPrevious(position);
	
	}

	@Override
	public void insertAfter(Position<E> position, E element) throws InvalidPositionException {

		collection().insertAfter(position, element);
		
	}

	@Override
	public void insertBefore(Position<E> position, E element) throws InvalidPositionException {

		collection().insertBefore(position, element);
		
	}
	
	@Override
	public void insertFirst(E element) {

		collection().insertFirst(element);
		
	}

	@Override
	public void insertLast(E element) {

		collection().insertLast(element);
		
	}

	@Override
	public E remove(Position<E> position) throws InvalidPositionException {

		return collection().remove(position);
		
	}
	
	@Override
	public E replace(Position<E> position, E element) throws InvalidPositionException {

		return collection().replace(position, element);
		
	}

	@Override
	public E set(Position<E> position, E element) throws InvalidPositionException {

		return collection().replace(position, element);
		
	}

	@Override
	public Iterable<Position<E>> positions() {

		return collection().positions();
		
	}

	@Override
	public Iterator<E> iterator() {

		return collection().iterator();
		
	}

	@Override
	public Position<E> positionAtRank(int rank) throws BoundaryViolationException {
	    
		checkRank(rank, size());
		
		Position<E> cursor;
	    
		if (rank <= (size() / 2)) {
			
			cursor = collection().first();

			for (int i = 0; i < rank; i++) cursor = collection().next(cursor);

		} else {

			cursor = collection().last();

			for (int i = 1; i < size() - rank; i++) cursor = collection().previous(cursor);

		}

		return cursor;

	}

	@Override
	public int rankOf(Position<E> position) throws InvalidPositionException {

		Position<E> cursor = collection().first();
	    
		int rank = 0;
		
		while (collection().hasNext(cursor)) {
	    
			if (position == cursor) return rank;
			
			cursor = collection().next(cursor);
			
			rank = rank + 1;
			
		}
		
		throw new InvalidPositionException();
	
	}

}
