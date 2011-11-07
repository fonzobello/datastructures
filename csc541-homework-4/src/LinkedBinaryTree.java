
import java.io.RandomAccessFile;

import java.util.Iterator;

public class LinkedBinaryTree<E> implements BinaryTree<E> {

	protected BinaryTreePosition<E> _Root;

	protected int _Size;
	
	protected RandomAccessFile _File;

	public LinkedBinaryTree(RandomAccessFile file) { 		    

		setFile(file);
		
		setRoot(null);

		setSize(0);

	}
	
	public LinkedBinaryTree(E root, RandomAccessFile file) { 		    

		setFile(file);
		
		addRoot(root);

		setSize(0);

	}
	
	private void setFile(RandomAccessFile file) {
		
		_File = file;
		
	}
	
	private RandomAccessFile getFile() {
		
		return _File;
		
	}
	
	protected void setRoot(BinaryTreePosition<E> root) {
		
		_Root = root;
		
	}
	
	private void setSize(int size) {
		
		_Size = size;
		
	}
	
	public Position<E> root() throws EmptyTreeException {

		if (_Root == null) throw new EmptyTreeException();

		return _Root;

	} 

	public int size() {

		return _Size; 

	} 

	public boolean isEmpty() {

		return (size() == 0); 

	} 

	public boolean isInternal(Position<E> position) throws InvalidPositionException {

		checkPosition(position);

		return (hasLeft(position) || hasRight(position));

	}

	public boolean isExternal(Position<E> position) throws InvalidPositionException {

		return !isInternal(position);

	}

	public boolean isRoot(Position<E> position) throws InvalidPositionException { 

		checkPosition(position);

		return (position == root()); 

	}

	public boolean hasLeft(Position<E> position) throws InvalidPositionException { 

		BinaryTreePosition<E> node = checkPosition(position);

		return (node.getLeft() != null);

	}

	public boolean hasRight(Position<E> position) throws InvalidPositionException { 

		BinaryTreePosition<E> node = checkPosition(position);

		return (node.getRight() != null);

	}

	public Position<E> left(Position<E> position) throws InvalidPositionException, BoundaryViolationException { 

		BinaryTreePosition<E> node = checkPosition(position);

		Position<E> left = node.getLeft();

		if (left == null) throw new BoundaryViolationException();

		return left;

	}

	public Position<E> right(Position<E> position) throws InvalidPositionException, BoundaryViolationException { 

		BinaryTreePosition<E> node = checkPosition(position);

		Position<E> right = node.getRight();

		if (right == null) throw new BoundaryViolationException();

		return right;

	}

	public Iterable<Position<E>> children(Position<E> position) throws InvalidPositionException { 

		List<Position<E>> children = new LinkedList<Position<E>>();

		if (hasLeft(position)) children.insertLast(left(position));

		if (hasRight(position)) children.insertLast(right(position));

		return children;

	}

	public Iterable<Position<E>> positions() {

		List<Position<E>> positions = new LinkedList<Position<E>>();

		if (size() != 0) preorderPositions(root(), positions);

		return positions;

	} 

	public Iterator<E> iterator() {

		Iterable<Position<E>> positions = positions();

		List<E> elements = new LinkedList<E>();

		for (Position<E> position: positions) elements.insertLast(position.element());

		return elements.iterator();

	}

	public E replace(Position<E> position, E element) throws InvalidPositionException {

		BinaryTreePosition<E> node = checkPosition(position);

		E toReturn = position.element();

		node.setElement(element);

		return toReturn;

	}

	public Position<E> addRoot(E element) throws NonEmptyTreeException {

		if (!isEmpty()) throw new NonEmptyTreeException();

		setRoot(createNode(element, null, null));
		
		setSize(1);

		return root();

	}

	public Position<E>  insertLeft(Position<E> position, E element) throws InvalidPositionException {

		BinaryTreePosition<E> node = checkPosition(position);

		Position<E> left = node.getLeft();

		if (left != null) throw new InvalidPositionException();

		BinaryTreePosition<E> toReturn = createNode(element, null, null);

		node.setLeft(toReturn);

		setSize(size() + 1);

		return toReturn;

	}

	public Position<E>  insertRight(Position<E> position, E element) throws InvalidPositionException {

		BinaryTreePosition<E> node = checkPosition(position);

		Position<E> right = node.getRight();

		if (right != null) throw new InvalidPositionException();

		BinaryTreePosition<E> toReturn = createNode(element, null, null);

		node.setRight(toReturn);

		setSize(size() + 1);

		return toReturn;

	}

	public void swapElements(Position<E> firstPosition, Position<E> secondPosition) throws InvalidPositionException {

		BinaryTreePosition<E> firstNode = checkPosition(firstPosition);

		BinaryTreePosition<E> secondNode = checkPosition(secondPosition);

		E toReturn = secondPosition.element();

		secondNode.setElement(firstPosition.element());

		firstNode.setElement(toReturn);	

	}

	public void expandExternal(Position<E> position, E left, E right) throws InvalidPositionException {

		if (!isExternal(position)) throw new InvalidPositionException();

		insertLeft(position, left);

		insertRight(position, right);

	}

	protected BinaryTreePosition<E> checkPosition(Position<E> position) throws InvalidPositionException {

		if (position == null || !(position instanceof BinaryTreePosition)) throw new InvalidPositionException();

		return (BinaryTreePosition<E>) position;

	}

	protected BinaryTreePosition<E> createNode(E element, BinaryTreePosition<E> left, BinaryTreePosition<E> right) {

		return new LinkedBinaryTreeNode<E>(element, left, right, getFile());

	}

	protected void preorderPositions(Position<E> position, List<Position<E>> positions) throws InvalidPositionException {

		positions.insertLast(position);

		if (hasLeft(position)) preorderPositions(left(position), positions);

		if (hasRight(position)) preorderPositions(right(position), positions);

	}

	protected void inorderPositions(Position<E> position, List<Position<E>> positions) throws InvalidPositionException {

		if (hasLeft(position)) inorderPositions(left(position), positions);

		positions.insertLast(position);

		if (hasRight(position))

			inorderPositions(right(position), positions);

	}
	
	public String toString() {
		
		List<Position<E>> toReturn = new LinkedList<Position<E>>();
		
		preorderPositions(root(), toReturn);
		
		return toReturn.toString();
		
	}
	
	public void print() {
		
		int level = 1;
		
		LinkedList<Position<E>> currentList = new LinkedList<Position<E>>();
		
		currentList.insertLast(root());
		
		while (!currentList.isEmpty()) {
		
			System.out.print(level + ": ");
			
			LinkedList<Position<E>> nextList = new LinkedList<Position<E>>();
			
			while (!currentList.isEmpty()) {
				
				BinaryTreePosition<E> node = checkPosition(currentList.remove(currentList.first()));
				
				System.out.print(node + " ");
				
				if (hasLeft(node)) 
					
					if (isInternal(node.getLeft())) nextList.insertLast(node.getLeft());
				
				if (hasRight(node))
					
					if (isInternal(node.getRight())) nextList.insertLast(node.getRight());
				
			}
			
			System.out.print("\n");
			
			currentList = nextList;
			
			level ++;
			
		}
		
	}

}
