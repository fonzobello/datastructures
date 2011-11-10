
import java.util.Comparator;

public class LinkedBinarySearchTree<K,V> extends LinkedBinaryTree<Entry<K,V>> implements Dictionary<K,V> {

	protected Comparator<K> _Comparator;

	protected Position<Entry<K,V>> _ActionPosition;

	protected int _NumberEntries = 0;

	public LinkedBinarySearchTree()  { 

		setComparator(new DefaultComparator<K>()); 

		addRoot(null);

	}

	public LinkedBinarySearchTree(Comparator<K> comparator)  { 

		setComparator(comparator); 

		addRoot(null);

	}
	
	private Comparator<K> comparator() {
		
		return _Comparator;
		
	}
	
	private void setComparator(Comparator<K> comparator) {
		
		_Comparator = comparator;
		
	}
	
	public Position<Entry<K,V>> actionPosition() {
		
		return _ActionPosition;
		
	}
	
	private int numberEntries() {
		
		return _NumberEntries;
		
	}
	
	private void setNumberEntries(int numberEntries) {
		
		_NumberEntries = numberEntries;
		
	}
	
	private void setActionPosition(Position<Entry<K,V>> actionPosition) {
		
		_ActionPosition = actionPosition;
		
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

	protected void replaceEntry(Position <Entry<K,V>> position, Entry<K,V> entry) {

		((LinkedBinarySearchTreeEntry<K,V>) entry).setPosition(position);

		replace(position, entry);

	}

	protected void checkKey(K key) throws InvalidKeyException {

		if(key == null) throw new InvalidKeyException();

	}

	protected void checkEntry(Entry<K,V> ent) throws InvalidEntryException {

		if(ent == null || !(ent instanceof LinkedBinarySearchTreeEntry)) throw new InvalidEntryException();

	}

	protected Entry<K,V> insertAtExternal(Position<Entry<K,V>> position, Entry<K,V> entry) {

		expandExternal(position, null, null);

		replace(position, entry);

		setNumberEntries(numberEntries() + 1);

		return entry;

	}

	protected void removeExternal(Position<Entry<K,V>> position) {

		removeAboveExternal(position);

		setNumberEntries(numberEntries() - 1);

	}

	protected Position<Entry<K,V>> treeSearch(K key, Position<Entry<K,V>> position) {

		if (isExternal(position)) return position;

		else {

			K currentKey = key(position);

			int comparison = comparator().compare(key, currentKey);

			if (comparison < 0) return treeSearch(key, left(position));

			else if (comparison > 0) return treeSearch(key, right(position));

			return position;

		}

	}

	protected void addAll(List<Entry<K,V>> list, Position<Entry<K,V>> position, K key) {

		if (isExternal(position)) return;

		Position<Entry<K,V>> pos = treeSearch(key, position);

		if (!isExternal(pos))  {

			addAll(list, left(pos), key);

			list.insertLast(pos.element());

			addAll(list, right(pos), key);

		}

	}

	public int size() {
		
		return _NumberEntries;
		
	}

	public boolean isEmpty() {
		
		return size() == 0;
		
	}

	public Entry<K,V> find(K key) throws InvalidKeyException {

		checkKey(key);

		Position<Entry<K,V>> currentPosition = treeSearch(key, root());

		setActionPosition(currentPosition);

		if (isInternal(currentPosition)) return entry(currentPosition);

		return null;

	}

	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {

		checkKey(key);

		List<Entry<K,V>> toReturn = new LinkedList<Entry<K,V>>();

		addAll(toReturn, root(), key);

		return toReturn;

	}

	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {

		checkKey(key);

		Position<Entry<K,V>> insertPosition = treeSearch(key, root());

		while (!isExternal(insertPosition)) insertPosition = treeSearch(key, left(insertPosition));

		setActionPosition(insertPosition);

		return insertAtExternal(insertPosition, new LinkedBinarySearchTreeEntry<K,V>(key, value, insertPosition));

	}

	public Entry<K,V> remove(Entry<K,V> entry) throws InvalidEntryException  {

		checkEntry(entry);

		Position<Entry<K,V>> removePosition = ((LinkedBinarySearchTreeEntry<K,V>) entry).position(); 

		Entry<K,V> toReturn = entry(removePosition);

		if (isExternal(left(removePosition))) removePosition = left(removePosition);

		else if (isExternal(right(removePosition))) removePosition = right(removePosition);

		else {

			Position<Entry<K,V>> swapPosition = removePosition;

			removePosition = right(swapPosition);

			do removePosition = left(removePosition);

			while (isInternal(removePosition));

			replaceEntry(swapPosition, (Entry<K,V>) parent(removePosition).element());

		}

		setActionPosition(sibling(removePosition));

		removeExternal(removePosition);

		return toReturn;

	}

	public Iterable<Entry<K,V>> entries() {

		List<Entry<K,V>> entries = new LinkedList<Entry<K,V>>();

		Iterable<Position<Entry<K,V>>> positions = positions();

		for (Position<Entry<K,V>> position: positions) if (isInternal(position)) entries.insertLast(position.element());

		return entries;
		
	}

	protected Position<Entry<K,V>> restructure(Position<Entry<K,V>> x) { 

		BinaryTreePosition<Entry<K,V>> a, b, c, t1, t2, t3, t4;

		Position<Entry<K,V>> y = parent(x);

		Position<Entry<K,V>> z = parent(y);

		boolean xLeft = (x == left(y));

		boolean yLeft = (y == left(z));

		BinaryTreePosition<Entry<K,V>> xx = (BinaryTreePosition<Entry<K,V>>)x, 

		yy = (BinaryTreePosition<Entry<K,V>>)y, zz = (BinaryTreePosition<Entry<K,V>>)z;

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