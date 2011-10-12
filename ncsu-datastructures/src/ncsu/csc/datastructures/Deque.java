package ncsu.csc.datastructures;
public interface Deque<E> {

	public int size();

	public boolean isEmpty();

	public E first() throws EmptyDequeException;

	public E last() throws EmptyDequeException;

	public void insertFirst (E element); 

	public void insertLast (E element); 

	public E removeFirst() throws EmptyDequeException;

	public E removeLast() throws EmptyDequeException;

}
