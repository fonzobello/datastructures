package ncsu.csc.datastructures;

public class ArrayCompleteBinaryTreeNode<E> implements Position<E> {

	E _Element;

	int _Index;

	public ArrayCompleteBinaryTreeNode(E element, int index) { 

		_Element = element;

		_Index = index; 

	}

	public E element() {
		
		return _Element;
		
	}

	public int index() {
		
		return _Index;
		
	}

	public E setElement(E element) {

		E temp = _Element;

		_Element = element;

		return temp;

	}

	public String toString() {

		return("[" + _Element + "," + _Index + "]");

	}

}
