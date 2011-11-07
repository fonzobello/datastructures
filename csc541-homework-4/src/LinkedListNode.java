
public class LinkedListNode<E> implements Position<E> {
	
	private LinkedListNode<E> _Previous;
	
	private LinkedListNode<E> _Next;
	
	private E _Element;

	public LinkedListNode(LinkedListNode<E> previous, LinkedListNode<E> next, E element) {

		setPrev(previous);

		setNext(next);

		setElement(element);

	}

	public LinkedListNode<E> getPrev() {
		
		return _Previous;
		
	}
	
	public LinkedListNode<E> getNext() {
		
		return _Next;
		
	}
	
	public E element() {

		return _Element;

	}

	public void setPrev(LinkedListNode<E> previous) {
		
		_Previous = previous;
		
	}
	
	public void setNext(LinkedListNode<E> next) {
		
		_Next = next;
		
	}

	public void setElement(E element) {
		
		_Element = element;
		
	}

}
