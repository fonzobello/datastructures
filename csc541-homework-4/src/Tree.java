
import java.util.Iterator;

public interface Tree<E> {

	public int size();

	public boolean isEmpty();

	public Iterator<E> iterator();

	public Iterable<Position<E>> positions();

	public E replace(Position<E> position, E element) throws InvalidPositionException;

	public Position<E> root() throws EmptyTreeException;

	public Iterable<Position<E>> children(Position<E> position) throws InvalidPositionException;

	public boolean isInternal(Position<E> position) throws InvalidPositionException;

	public boolean isExternal(Position<E> position) throws InvalidPositionException;

	public boolean isRoot(Position<E> position) throws InvalidPositionException;

}
