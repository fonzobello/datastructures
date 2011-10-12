package ncsu.csc.datastructures;

import java.util.Comparator;

public class LinkedAVLTree<K,V> extends LinkedBinarySearchTree<K,V> implements Dictionary<K,V> {

	public LinkedAVLTree(Comparator<K> comparator)  {
		
		super(comparator);
		
	}

	public LinkedAVLTree() {
		
		super();
		
	}

	protected BinaryTreePosition<Entry<K,V>> createNode(Entry<K,V> element, BinaryTreePosition<Entry<K,V>> parent, BinaryTreePosition<Entry<K,V>> left, BinaryTreePosition<Entry<K,V>> right) {

		return new LinkedAVLTreeNode<K,V>(element, parent, left, right);

	}

	protected int height(Position<Entry<K,V>> position)  {

		return ((LinkedAVLTreeNode<K,V>) position).getHeight();

	}

	protected void setHeight(Position<Entry<K,V>> position) { 

		((LinkedAVLTreeNode<K,V>) position).setHeight(1 + Math.max(height(left(position)), height(right(position))));

	}

	protected boolean isBalanced(Position<Entry<K,V>> position)  {

		int balanceFactor = height(left(position)) - height(right(position));

		return ((-1 <= balanceFactor) &&  (balanceFactor <= 1));

	}

	protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> position)  {

		if (height(left(position)) > height(right(position))) return left(position);

		else if (height(left(position)) < height(right(position))) return right(position);

		if (isRoot(position)) return left(position);

		if (position == left(parent(position))) return left(position);

		else return right(position);

	}

	protected void rebalance(Position<Entry<K,V>> position) {

		if(isInternal(position)) setHeight(position);

		while (!isRoot(position)) {

			position = parent(position);

			setHeight(position);

			if (!isBalanced(position)) { 

				Position<Entry<K,V>> restructurePosition =  tallerChild(tallerChild(position));

				position = restructure(restructurePosition);

				setHeight(left(position));

				setHeight(right(position));

				setHeight(position);

			}

		}

	} 

	public Entry<K,V> insert(K key, V value) throws InvalidKeyException  {

		Entry<K,V> toReturn = super.insert(key, value);

		rebalance(actionPosition());

		return toReturn;

	}

	public Entry<K,V> remove(Entry<K,V> entry) throws InvalidEntryException {

		Entry<K,V> toReturn = super.remove(entry);

		if (toReturn != null) rebalance(actionPosition());

		return toReturn;

	}

}