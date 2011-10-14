package ncsu.csc.datastructures;

import java.util.Comparator;

public class BinarySearchTreeMap<K,V> extends LinkedBinaryTree<Entry<K,V>> implements Map<K,V> {

	protected Comparator<K> _Comparator;

	protected Position<Entry<K,V>> _ActionPosition;

	protected int _NumberEntries = 0;

	public BinarySearchTreeMap()  { 

		_Comparator = new DefaultComparator<K>(); 

		addRoot(null);

	}

	public BinarySearchTreeMap(Comparator<K> comparator)  { 

		_Comparator = comparator; 

		addRoot(null);

	}

	protected K key(Position<Entry<K,V>> position)  {

		return position.element().getKey();

	}

	protected V value(Position<Entry<K,V>> position)  { 

		return position.element().getValue(); 

	}

	protected Entry<K,V> entry(Position<Entry<K,V>> position)  { 

		return position.element();

	}

	protected V replaceEntry(Position <Entry<K,V>> position, Entry<K,V> entry) {

		((LinkedBinarySearchTreeEntry<K,V>) entry).setPosition(position);

		return replace(position, entry).getValue();

	}

	protected void checkKey(K key) throws InvalidKeyException {

		if(key == null) throw new InvalidKeyException();

	}

	protected void checkEntry(Entry<K,V> entry) throws InvalidEntryException {

		if(entry == null || !(entry instanceof LinkedBinarySearchTreeEntry)) throw new InvalidEntryException();

	}

	protected Entry<K,V> insertAtExternal(Position<Entry<K,V>> position, Entry<K,V> entry) {

		expandExternal(position,null,null);

		replace(position, entry);

		_NumberEntries ++;

		return entry;

	}

	protected void removeExternal(Position<Entry<K,V>> position) {

		removeAboveExternal(position);

		_NumberEntries --;

	}

	protected Position<Entry<K,V>> treeSearch(K key, Position<Entry<K,V>> position) {

		if (isExternal(position)) return position;

		else {

			K currentKey = key(position);

			int comparator = _Comparator.compare(key, currentKey);

			if (comparator < 0) return treeSearch(key, left(position));

			else if (comparator > 0)

				return treeSearch(key, right(position));

			return position;

		}

	}

	public int size() {
		
		return _NumberEntries;
		
	}

	public boolean isEmpty() {
		
		return size() == 0;
		
	}

	public V get(K key) throws InvalidKeyException {

		checkKey(key);

		Position<Entry<K,V>> currentPosition = treeSearch(key, root());

		_ActionPosition = currentPosition;

		if (isInternal(currentPosition)) return value(currentPosition);

		return null;

	}

	public V put(K key, V value) throws InvalidKeyException {

		checkKey(key);

		Position<Entry<K,V>> insertPosition = treeSearch(key, root());

		LinkedBinarySearchTreeEntry<K,V> entry = new LinkedBinarySearchTreeEntry<K,V>(key, value, insertPosition);

		_ActionPosition = insertPosition;

		if (isExternal(insertPosition)) {

			insertAtExternal(insertPosition, entry).getValue();

			return null;

		}

		return replaceEntry(insertPosition, entry);
  
	}

	public V remove(K key) throws InvalidKeyException  {

		checkKey(key);

		Position<Entry<K,V>> removePosition = treeSearch(key, root());

		if (isExternal(removePosition)) return null;

		Entry<K,V> toReturn = entry(removePosition);

		if (isExternal(left(removePosition))) removePosition = left(removePosition);

		else if (isExternal(right(removePosition))) removePosition = right(removePosition);

		else {

			Position<Entry<K,V>> swapPosition = removePosition;

			removePosition = right(swapPosition);

			do

				removePosition = left(removePosition);

			while (isInternal(removePosition));

			replaceEntry(swapPosition, (Entry<K,V>) parent(removePosition).element());

		}

		_ActionPosition = sibling(removePosition);

		removeExternal(removePosition);

		return toReturn.getValue();

	}

	public Iterable<K> keySet() {

		List<K> keys = new LinkedList<K>();

		Iterable<Position<Entry<K,V>>> positer = positions();

		for (Position<Entry<K,V>> currentPosition: positer) if (isInternal(currentPosition)) keys.insertLast(key(currentPosition));

		return keys;

	}

	public Iterable<V> values() {

		List<V> values = new LinkedList<V>();

		Iterable<Position<Entry<K,V>>> positer = positions();

		for (Position<Entry<K,V>> currentPosition: positer) if (isInternal(currentPosition)) values.insertLast(value(currentPosition));

		return values;

	}

	public Iterable<Entry<K,V>> entrySet() {

		List<Entry<K,V>> entries = new LinkedList<Entry<K,V>>();

		Iterable<Position<Entry<K,V>>> positer = positions();

		for (Position<Entry<K,V>> currentPosition: positer) if (isInternal(currentPosition)) entries.insertLast(currentPosition.element());

		return entries;

	}

	protected Position<Entry<K,V>> restructure(Position<Entry<K,V>> x) { 

		BinaryTreePosition<Entry<K,V>> a, b, c, t1, t2, t3, t4;

		Position<Entry<K,V>> y = parent(x);

		Position<Entry<K,V>> z = parent(y);

		boolean xLeft = (x == left(y));

		boolean yLeft = (y == left(z));

		BinaryTreePosition<Entry<K,V>> xx = (BinaryTreePosition<Entry<K,V>>)x; 

		BinaryTreePosition<Entry<K,V>> yy = (BinaryTreePosition<Entry<K,V>>)y;
		
		BinaryTreePosition<Entry<K,V>> zz = (BinaryTreePosition<Entry<K,V>>)z;

		if (xLeft && yLeft) { 

			a = xx; b = yy; c = zz; 

			t1 = a.getLeft(); t2 = a.getRight(); t3 = b.getRight(); t4 = c.getRight();

		} else if (!xLeft && yLeft) { 

			a = yy; b = xx; c = zz; 

			t1 = a.getLeft(); t2 = b.getLeft(); t3 = b.getRight(); t4 = c.getRight();

		} else if (xLeft && !yLeft) { 

			a = zz; b = xx; c = yy; 

			t1 = a.getLeft(); t2 = b.getLeft(); t3 = b.getRight(); t4 = c.getRight();

		} else {

			a = zz; b = yy; c = xx; 

			t1 = a.getLeft(); t2 = b.getLeft(); t3 = c.getLeft(); t4 = c.getRight();

		}

		if (isRoot(z)) {

			setRoot(b);

			b.setParent(null);

		} else {

			BinaryTreePosition<Entry<K,V>> zParent = (BinaryTreePosition<Entry<K,V>>)parent(z);

			if (z == left(zParent)) {

				b.setParent(zParent);

				zParent.setLeft(b);

			} else {

				b.setParent(zParent);

				zParent.setRight(b);

			}

		}

		b.setLeft(a);

		a.setParent(b);

		b.setRight(c);

		c.setParent(b);

		a.setLeft(t1);

		t1.setParent(a);

		a.setRight(t2);

		t2.setParent(a);

		c.setLeft(t3);

		t3.setParent(c);

		c.setRight(t4);

		t4.setParent(c);

		((LinkedBinarySearchTreeEntry<K,V>) a.element()).setPosition(a);

		((LinkedBinarySearchTreeEntry<K,V>) b.element()).setPosition(b);

		((LinkedBinarySearchTreeEntry<K,V>) c.element()).setPosition(c);

		return b;

	}

} 	
