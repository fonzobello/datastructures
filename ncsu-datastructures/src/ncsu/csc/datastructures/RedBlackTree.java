package ncsu.csc.datastructures;

import java.util.Comparator;

public class RedBlackTree<K,V> extends LinkedBinarySearchTree<K,V> implements Dictionary<K,V> {

	public RedBlackTree() { super(); }

	public RedBlackTree(Comparator<K> C) { super(C); }

	protected BinaryTreePosition<Entry<K,V>> createNode(Entry<K,V> element, BinaryTreePosition<Entry<K,V>> parent, BinaryTreePosition<Entry<K,V>> left, BinaryTreePosition<Entry<K,V>> right) {

		return new RedBlackTreeNode<K,V>(element,parent,left,right);

	}

	public Entry<K,V> insert(K key, V value) throws InvalidKeyException  {

		Entry<K,V> toReturn = super.insert(key, value); 

		Position<Entry<K,V>> posZ = actionPosition();

		setRed(posZ);

		if (isRoot(posZ)) setBlack(posZ);

		else remedyDoubleRed(posZ);

		return toReturn;

	}

	protected void remedyDoubleRed(Position<Entry<K,V>> posZ)  {

		Position<Entry<K,V>> posV = parent(posZ);

		if (isRoot(posV)) return;

		if (!isPosRed(posV)) return;

		if (!isPosRed(sibling(posV)))  {

			posV = restructure(posZ);

			setBlack(posV);

			setRed(left(posV));

			setRed(right(posV));

		} else {

			setBlack(posV);

			setBlack(sibling(posV));

			Position<Entry<K,V>> posU = parent(posV);

			if (isRoot(posU)) return;

			setRed(posU);

			remedyDoubleRed(posU);

		}

	}

	public Entry<K,V> remove(Entry<K,V> ent) throws InvalidEntryException {

		Entry<K,V> toReturn = super.remove(ent);

		Position<Entry<K,V>> posR = actionPosition();

		if (toReturn != null) {	

			if (wasParentRed(posR) || isRoot(posR) || isPosRed(posR)) setBlack(posR);

			else remedyDoubleBlack(posR);

		}

		return toReturn;

	}

	protected void remedyDoubleBlack(Position<Entry<K,V>> posR) {

		Position<Entry<K,V>> posX, posY, posZ;

		boolean oldColor;

		posX = parent(posR);

		posY = sibling(posR);

		if (!isPosRed(posY))  {

			posZ = redChild(posY);

			if (hasRedChild(posY))  {

				oldColor = isPosRed(posX);

				posZ = restructure(posZ);

				setColor(posZ, oldColor);

				setBlack(posR);

				setBlack(left(posZ));

				setBlack(right(posZ));

				return;

			}

			setBlack(posR);

			setRed(posY);

			if (!isPosRed(posX))  {

				if (!isRoot(posX)) remedyDoubleBlack(posX);

				return;

			}

			setBlack(posX);

			return;

		}

		if (posY == right(posX)) posZ = right(posY);

		else posZ = left(posY);

		restructure(posZ);

		setBlack(posY);

		setRed(posX);

		remedyDoubleBlack(posR);

	}

	protected boolean isPosRed(Position<Entry<K,V>> position)  {

		return ((RedBlackTreeNode) position).isRed();

	}

	private boolean wasParentRed(Position<Entry<K,V>> position){

		if (!isRoot(position)) {

			if(!isPosRed(position) && !isPosRed(parent(position))) {

				if (isExternal(sibling(position)) || (hasTwoExternalChildren(sibling(position)) && isPosRed(sibling(position)))) return true;

			}

		}

		return false;

	}

	private boolean hasTwoExternalChildren(Position<Entry<K,V>> position){

		if (isExternal(left(position)) && isExternal(right(position))) return true;

		else return false;

	}

	protected void setRed(Position<Entry<K,V>> position)  {

		((RedBlackTreeNode) position).makeRed();

	}

	protected void setBlack(Position<Entry<K,V>> position)  {

		((RedBlackTreeNode) position).makeBlack();

	}

	protected void setColor(Position<Entry<K,V>> position, boolean color)  {

		((RedBlackTreeNode) position).setColor(color);

	}

	protected Position<Entry<K,V>> redChild(Position<Entry<K,V>> position)  {

		Position<Entry<K,V>> child = left(position);

		if (isPosRed(child)) return child;

		child = right(position);

		if (isPosRed(child)) return child;

		return null;

	}

	protected boolean hasRedChild(Position<Entry<K,V>> position){

		if (isPosRed(left(position)) || isPosRed(right(position))) return true;

		else return false;

	}

	protected boolean swapColor(Position<Entry<K,V>> a, Position<Entry<K,V>> b){

		boolean wasRed = false;

		if (isPosRed(a) && !isPosRed(b)){

			wasRed = true;

			setBlack(a);

			setRed(b);

		} else if (!isPosRed(a) && isPosRed(b)){

			setBlack(b);

			setRed(a);

		}

		return wasRed;

	}

	protected void swap(Position<Entry<K,V>> swapPos, Position<Entry<K,V>> remPos){

		swapColor(remPos, swapPos);

		swapElements(swapPos, remPos);

	}

}