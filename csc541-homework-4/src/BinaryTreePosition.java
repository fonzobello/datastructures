
public interface BinaryTreePosition<E> extends Position<E> {

	public long getOffset();
	
	public void setElement(E element);

	public BinaryTreePosition<E> getLeft(); 

	public void setLeft(BinaryTreePosition<E> position); 

	public BinaryTreePosition<E> getRight(); 

	public void setRight(BinaryTreePosition<E> position); 

}
