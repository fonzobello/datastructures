/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

public interface BinaryTreePosition<E> extends Position<E> {

	public int getOffset();
	
	public void setElement(E element);

	public BinaryTreePosition<E> getLeft(); 

	public void setLeft(BinaryTreePosition<E> position); 

	public BinaryTreePosition<E> getRight(); 

	public void setRight(BinaryTreePosition<E> position); 

}
