
public class LinkedBinaryTreeNode<E> implements BinaryTreePosition<E> {

	private E _Element;

	private BinaryTreePosition<E> _Left;
	
	private BinaryTreePosition<E> _Right;
	
	private BinaryTreePosition<E> _Parent;

	public LinkedBinaryTreeNode() { }

	public LinkedBinaryTreeNode(E element, BinaryTreePosition<E> parent, BinaryTreePosition<E> left, BinaryTreePosition<E> right) { 

		setElement(element);

		setParent(parent);

		setLeft(left);

		setRight(right);

	}

	public E element() {
		
		return _Element;
		
	}

	public void setElement(E element) {
		
		_Element = element;
		
	}

	public BinaryTreePosition<E> getLeft() {
		
		return _Left;
		
	}

	public void setLeft(BinaryTreePosition<E> left) {
		
		_Left = left;
		
	}

	public BinaryTreePosition<E> getRight() {
		
		return _Right;
		
	}

	public void setRight(BinaryTreePosition<E> right) {
		
		_Right = right;
		
	}

	public BinaryTreePosition<E> getParent() {
		
		return _Parent;
		
	}

	public void setParent(BinaryTreePosition<E> parent) {
		
		_Parent = parent;
		
	}
	
	public String toString() {
		
		if (element() == null) return "null";
		
		return element().toString();
		
	}

}
