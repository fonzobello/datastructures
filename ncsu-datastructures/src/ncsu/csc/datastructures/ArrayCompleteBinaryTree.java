package ncsu.csc.datastructures;

import java.util.ArrayList;

import java.util.Iterator;

public class ArrayCompleteBinaryTree<E> implements CompleteBinaryTree<E>  {

	protected ArrayList<ArrayCompleteBinaryTreeNode<E>> _Collection;

	public ArrayCompleteBinaryTree() { 

		_Collection = new ArrayList<ArrayCompleteBinaryTreeNode<E>>();

		_Collection.add(0, null);

	}

	public int size() {
		
		return _Collection.size() - 1;
		
	} 

	public boolean isEmpty() {
		
		return (size() == 0);
		
	} 

	public boolean isInternal(Position<E> position) throws InvalidPositionException {

		return hasLeft(position);

	}

	public boolean isExternal(Position<E> position) throws InvalidPositionException {

		return !isInternal(position);

	}

	public boolean isRoot(Position<E> position) throws InvalidPositionException { 

		ArrayCompleteBinaryTreeNode<E> node = checkPosition(position);

		return node.index() == 1;

	}

	public boolean hasLeft(Position<E> position) throws InvalidPositionException { 

		ArrayCompleteBinaryTreeNode<E> node = checkPosition(position);

		return 2 * node.index() <= size();

	}

	public boolean hasRight(Position<E> position) throws InvalidPositionException { 

		ArrayCompleteBinaryTreeNode<E> node = checkPosition(position);

		return 2 * node.index() + 1 <= size();

	}

	public Position<E> root() throws EmptyTreeException {

		if (isEmpty()) throw new EmptyTreeException();

		return _Collection.get(1);

	} 

	public Position<E> left(Position<E> position) throws InvalidPositionException, BoundaryViolationException { 

		if (!hasLeft(position)) throw new BoundaryViolationException();

		ArrayCompleteBinaryTreeNode<E> node = checkPosition(position);

		return _Collection.get(2 * node.index());

	}

	public Position<E> right(Position<E> position) throws InvalidPositionException { 

		if (!hasRight(position)) throw new BoundaryViolationException();

		ArrayCompleteBinaryTreeNode<E> node = checkPosition(position);

		return _Collection.get(2 * node.index() + 1);

	}

	public Position<E> parent(Position<E> position) throws InvalidPositionException, BoundaryViolationException { 

		if (isRoot(position)) throw new BoundaryViolationException();

		ArrayCompleteBinaryTreeNode<E> node = checkPosition(position);

		return _Collection.get(node.index() / 2);

	}

	public Iterable<Position<E>> children(Position<E> position) throws InvalidPositionException { 

		List<Position<E>> children = new LinkedList<Position<E>>();

		if (hasLeft(position))

			children.insertLast(left(position));

		if (hasRight(position))

			children.insertLast(right(position));

		return children;

	}

	public Iterable<Position<E>> positions() {

		ArrayList<Position<E>> positions = new ArrayList<Position<E>>();

		Iterator<ArrayCompleteBinaryTreeNode<E>> iterator = _Collection.iterator();

		iterator.next();

		while (iterator.hasNext())

			positions.add(iterator.next());

		return positions;

	}

	public E replace(Position<E> position, E element) throws InvalidPositionException {

		ArrayCompleteBinaryTreeNode<E> node = checkPosition(position);

		return node.setElement(element);

	}

	public Position<E> add(E element) {

		int index = size() + 1;

		ArrayCompleteBinaryTreeNode<E> node = new ArrayCompleteBinaryTreeNode<E>(element,index);

		_Collection.add(index, node);

		return node;

	}

	public E remove() throws EmptyTreeException {

		if(isEmpty()) throw new EmptyTreeException();

		return _Collection.remove(size()).element(); 

	}

	protected ArrayCompleteBinaryTreeNode<E> checkPosition(Position<E> position) throws InvalidPositionException {

		if (position == null || !(position instanceof ArrayCompleteBinaryTreeNode)) throw new InvalidPositionException();

		return (ArrayCompleteBinaryTreeNode<E>) position;

	}

	public Position<E> sibling(Position<E> position) throws InvalidPositionException, BoundaryViolationException {

		try {

			Position<E> parent = parent(position);

			Position<E> left = left(parent);

			if (position == left)

				return right(parent);

			else

				return left;

		}

		catch(BoundaryViolationException e) {

			throw new BoundaryViolationException();

		}

	}

	public void swapElements(Position<E> positionX, Position<E> positionY) throws InvalidPositionException {

		ArrayCompleteBinaryTreeNode<E> nodeX = checkPosition(positionX);

		ArrayCompleteBinaryTreeNode<E> nodeY = checkPosition(positionY);

		E temp = nodeX.element();

		nodeX.setElement(nodeY.element());

		nodeY.setElement(temp);

	}

	public Iterator<E> iterator() { 

		ArrayList<E> toReturn = new ArrayList<E>();

		Iterator<ArrayCompleteBinaryTreeNode<E>> iterator = _Collection.iterator();

		iterator.next();

		while (iterator.hasNext())

			toReturn.add(iterator.next().element());

		return toReturn.iterator();

	} 

	public String toString() {
		
		return _Collection.toString();
		
	}

} 

