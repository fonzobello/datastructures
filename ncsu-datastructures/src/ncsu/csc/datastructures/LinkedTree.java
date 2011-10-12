package ncsu.csc.datastructures;
import java.util.Iterator;

public class LinkedTree<E> implements Tree<E> {

	protected TreePosition<E> _Root;

	protected int _Size;

	public LinkedTree() { 		    

		_Root = null;

		_Size = 0;

	}

	public int size() {

		return _Size; 

	} 

	public boolean isEmpty() {

		return (_Size == 0); 

	} 

	public boolean isInternal(Position<E> position) throws InvalidPositionException {

		return !isExternal(position);
		
	}

	public boolean isExternal(Position<E> position) throws InvalidPositionException {

		TreePosition<E> node = checkPosition(position);

		return (node.getChildren() == null) || node.getChildren().isEmpty();

	}

	public boolean isRoot(Position<E> position) throws InvalidPositionException { 

		checkPosition(position);

		return (position == root()); 

	}

	public Position<E> root() throws EmptyTreeException {

		if (_Root == null) throw new EmptyTreeException();

		return _Root;

	} 

	public Position<E> parent(Position<E> position) throws InvalidPositionException, BoundaryViolationException { 

		TreePosition<E> node = checkPosition(position);

		Position<E> parentPos = node.getParent();

		if (parentPos == null) throw new BoundaryViolationException();

		return parentPos; 

	}
	
	public Iterable<Position<E>> children(Position<E> position) throws InvalidPositionException { 

		TreePosition<E> node = checkPosition(position);

		if (isExternal(position)) throw new InvalidPositionException(); 

		return node.getChildren();

	}

	public Iterable<Position<E>> positions() {

		List<Position<E>> positions = new LinkedList<Position<E>>();

		if (!isEmpty()) preorderPositions(root(), positions);
	
		return positions;

	}
	
	public Iterator<E> iterator() {
		
		Iterable<Position<E>> positions = positions();

		List<E> elements = new LinkedList<E>();

		for (Position<E> position : positions) elements.insertLast(position.element());

		return elements.iterator();

	}

	public E replace(Position<E> position, E element) throws InvalidPositionException {

		TreePosition<E> node = checkPosition(position);

		E toReturn = position.element();

		node.setElement(element);

		return toReturn;

	}

	public Position<E> addRoot(E element) throws NonEmptyTreeException {

		if(!isEmpty()) throw new NonEmptyTreeException();

		_Size = 1;

		_Root = createNode(element, null, null);

		return _Root;

	}

	public void swapElements(Position<E> positionX, Position<E> positionY) throws InvalidPositionException {

		TreePosition<E> nodeX = checkPosition(positionX);

		TreePosition<E> nodeY = checkPosition(positionY);

		E temp = nodeY.element();

		nodeY.setElement(positionX.element());

		nodeX.setElement(temp);	

	}

	protected TreePosition<E> checkPosition(Position<E> position) throws InvalidPositionException {

		if (position == null || !(position instanceof TreePosition)) throw new InvalidPositionException();

		return (TreePosition<E>) position;

	}

	protected TreePosition<E> createNode(E element, TreePosition<E> parent, List<Position<E>> children) {

		return new TreeNode<E>(element,parent,children); 

	}

	protected void preorderPositions(Position<E> start, List<Position<E>> positions) throws InvalidPositionException {

		positions.insertLast(start);

		for (Position<E> w : children(start)) preorderPositions(w, positions);

	}

}