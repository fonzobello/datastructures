

import java.util.Iterator;

public interface List<E> extends Iterable<E> {

	public int size();

	public boolean isEmpty();

	public Position<E> first();

	public Position<E> last();

	public Position<E> next(Position<E> position) throws InvalidPositionException, BoundaryViolationException;

	public Position<E> previous(Position<E> position) throws InvalidPositionException, BoundaryViolationException;

	public boolean hasNext(Position<E> position) throws InvalidPositionException;
	
	public boolean hasPrevious(Position<E> position) throws InvalidPositionException;
	
	public void insertFirst(E element);

	public void insertLast(E element);

	public void insertAfter(Position<E> position, E element) throws InvalidPositionException;

	public void insertBefore(Position<E> position, E element) throws InvalidPositionException;

	public E remove(Position<E> position) throws InvalidPositionException;
	
	public E replace(Position<E> position, E element) throws InvalidPositionException;

	public E set(Position<E> position, E element) throws InvalidPositionException;

	public Iterable<Position<E>> positions();

	public Iterator<E> iterator();

}
