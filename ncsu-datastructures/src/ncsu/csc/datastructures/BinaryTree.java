package ncsu.csc.datastructures;
public interface BinaryTree<E> extends Tree<E> {

	public Position<E> left(Position<E> position) throws InvalidPositionException, BoundaryViolationException;

	public Position<E> right(Position<E> position) throws InvalidPositionException, BoundaryViolationException;

	public boolean hasLeft(Position<E> position) throws InvalidPositionException;

	public boolean hasRight(Position<E> position) throws InvalidPositionException;

}

