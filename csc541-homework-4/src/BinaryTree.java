/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

public interface BinaryTree<E> extends Tree<E> {

	public Position<E> left(Position<E> position) throws InvalidPositionException, BoundaryViolationException;

	public Position<E> right(Position<E> position) throws InvalidPositionException, BoundaryViolationException;

	public boolean hasLeft(Position<E> position) throws InvalidPositionException;

	public boolean hasRight(Position<E> position) throws InvalidPositionException;

}

