package ncsu.csc.datastructures;
import java.util.Iterator;

public interface Vector<E> {

	public int size();

	public boolean isEmpty();

	public void insertAtRank(int rank, E element) throws BoundaryViolationException;

	public E elementAtRank(int rank) throws BoundaryViolationException;

	public E removeAtRank(int rank) throws BoundaryViolationException;

	public E replaceAtRank(int rank, E element) throws BoundaryViolationException;
	
	public Iterator<E> iterator();

}
