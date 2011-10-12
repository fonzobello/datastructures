package ncsu.csc.datastructures;

public interface CompleteBinaryTree<E> extends BinaryTree<E> {

	public Position<E> add(E element);

	public E remove();

}
