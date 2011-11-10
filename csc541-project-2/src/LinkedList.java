
import java.util.Iterator;

public class LinkedList<E> implements List<E> {

	private int _Size;

	protected LinkedListNode<E> _Header;
	
	protected LinkedListNode<E> _Trailer;

	public LinkedList() {

		setSize(0);

		setHeader(new LinkedListNode<E>(null, null, null));

		setTrailer(new LinkedListNode<E>(null, null, null));

		header().setNext(trailer());
		
		trailer().setPrev(header());

	}
	
	private LinkedListNode<E> header() {
		
		return _Header;
		
	}
	
	private LinkedListNode<E> trailer() {
		
		return _Trailer;
		
	}
	
	private void setSize(int size) {
		
		_Size = size;
	
	}
	
	private void setHeader(LinkedListNode<E> header) {
		
		_Header = header;
		
	}
	
	private void setTrailer(LinkedListNode<E> trailer) {
		
		_Trailer = trailer;
		
	}

	protected LinkedListNode<E> checkPosition(Position<E> position) throws InvalidPositionException {

		if (position == null) throw new InvalidPositionException();

		if (position == header()) throw new InvalidPositionException();

		if (position == trailer()) throw new InvalidPositionException();

		try {

			LinkedListNode<E> toReturn = (LinkedListNode<E>) position;

			if ((toReturn.getPrev() == null) || (toReturn.getNext() == null)) throw new InvalidPositionException();

			return toReturn;

		} catch (ClassCastException e) {

			throw new InvalidPositionException();

		}

	}

	public int size() {
		
		return _Size;
		
	}

	public boolean isEmpty() {
		
		return (size() == 0);
		
	}

	public Position<E> first() throws EmptyListException {

		if (isEmpty()) throw new EmptyListException();

		return header().getNext();

	}

	public Position<E> last()throws EmptyListException {

		if (isEmpty()) throw new EmptyListException();

		return trailer().getPrev();

	}

	public Position<E> previous(Position<E> position) throws InvalidPositionException, BoundaryViolationException {

		LinkedListNode<E> node = checkPosition(position);

		LinkedListNode<E> previous = node.getPrev();

		if (previous == header()) throw new BoundaryViolationException();

		return previous;

	}

	public Position<E> next(Position<E> position) throws InvalidPositionException, BoundaryViolationException {

		LinkedListNode<E> node = checkPosition(position);

		LinkedListNode<E> next = node.getNext();

		if (next == trailer()) throw new BoundaryViolationException();

		return next;

	}
	
	public boolean hasPrevious(Position<E> position) throws InvalidPositionException {

		LinkedListNode<E> node = checkPosition(position);
		
		LinkedListNode<E> previous = node.getPrev();
		
		if (previous == trailer()) return false;
		
		return true;
		
	}
	
	public boolean hasNext(Position<E> position) throws InvalidPositionException {

		LinkedListNode<E> node = checkPosition(position);
		
		LinkedListNode<E> next = node.getNext();
		
		if (next == trailer()) return false;
		
		return true;
		
	}

	public void insertBefore(Position<E> position, E element) throws InvalidPositionException {

		LinkedListNode<E> node = checkPosition(position);

		setSize(size() + 1);

		LinkedListNode<E> newNode = new LinkedListNode<E>(node.getPrev(), node, element);

		node.getPrev().setNext(newNode);

		node.setPrev(newNode);

	}

	public void insertAfter(Position<E> position, E element) throws InvalidPositionException {

		LinkedListNode<E> node = checkPosition(position);

		setSize(size() + 1);

		LinkedListNode<E> newNode = new LinkedListNode<E>(node, node.getNext(), element);

		node.getNext().setPrev(newNode);

		node.setNext(newNode);

	}

	public void insertFirst(E element) {

		setSize(size() + 1);

		LinkedListNode<E> node = new LinkedListNode<E>(header(), header().getNext(), element);

		header().getNext().setPrev(node);

		header().setNext(node);

	}

	public void insertLast(E element) {

		setSize(size() + 1);

		LinkedListNode<E> node = new LinkedListNode<E>(trailer().getPrev(), trailer(), element);

		trailer().getPrev().setNext(node);

		trailer().setPrev(node);

	}

	public E remove(Position<E> position) throws InvalidPositionException {

		LinkedListNode<E> node = checkPosition(position);

		setSize(size() - 1);

		LinkedListNode<E> previous = node.getPrev();

		LinkedListNode<E> next = node.getNext();

		previous.setNext(next);

		next.setPrev(previous);

		E toReturn = node.element();

		node.setNext(null);

		node.setPrev(null);

		return toReturn;

	}
	
	public E replace(Position<E> position, E element) throws InvalidPositionException {
		
		LinkedListNode<E> node = checkPosition(position);
		
		E toReturn = node.element();
		
		node.setElement(element);
		
		return toReturn;
		
	}

	public E set(Position<E> position, E element) throws InvalidPositionException {

		LinkedListNode<E> node = checkPosition(position);

		E toReturn = node.element();

		node.setElement(element);

		return toReturn;

	}

	public Iterator<E> iterator() {
		
		return new ElementIterator<E>(this);
		
	}

	public Iterable<Position<E>> positions() {

		List<Position<E>> positionList = new LinkedList<Position<E>>();

		if (!isEmpty()) {

			Position<E> position = first();

			while (true) {

				positionList.insertLast(position);

				if (position == last()) break;

				position = next(position);

			}

		}

		return positionList;

	}

	public boolean isFirst(Position<E> position) throws InvalidPositionException {  

		LinkedListNode<E> node = checkPosition(position);

		return node.getPrev() == header();

	}

	public boolean isLast(Position<E> position) throws InvalidPositionException {  

		LinkedListNode<E> node = checkPosition(position);

		return node.getNext() == trailer();

	}

	public void swapElements(Position<E> a, Position<E> b) throws InvalidPositionException {

		LinkedListNode<E> pA = checkPosition(a);

		LinkedListNode<E> pB = checkPosition(b);

		E temp = pA.element();

		pA.setElement(pB.element());

		pB.setElement(temp);

	}
	
	public LinkedList<E> clone() {
		
		LinkedList<E> toReturn = new LinkedList<E>();
		
		Iterator<E> iterator = iterator();

		while (iterator.hasNext()) {

			toReturn.insertLast(iterator.next());

		}
		
		return toReturn;
		
	}

	public static <E> String forEachToString(List<E> positionList) {

		String toReturn = "[";

		int size = positionList.size();

		for (E element : positionList) {

			toReturn += element;

			size--;

			if (size > 0)

				toReturn += ", ";

		}

		toReturn += "]";

		return toReturn;

	}

	public static <E> String toString(List<E> positionList) {

		Iterator<E> iterator = positionList.iterator();

		String toReturn = "[";

		while (iterator.hasNext()) {

			toReturn += iterator.next();

			if (iterator.hasNext())

				toReturn += ", ";

		}

		toReturn += "]";

		return toReturn;

	}

	public String toString() {

		return toString(this);

	}

}
