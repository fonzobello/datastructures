import java.io.RandomAccessFile;

import java.util.Comparator;

public class LinkedBinarySearchTree<K,V> extends LinkedBinaryTree<Entry<K,V>> implements Dictionary<K,V> {

	protected Comparator<K> _Comparator;

	protected Position<Entry<K,V>> _ActionPosition;

	protected int _NumberEntries = 0;

	public LinkedBinarySearchTree(RandomAccessFile file)  { 

		super(file);
		
		setComparator(new DefaultComparator<K>()); 

		addRoot(null);

	}

	public LinkedBinarySearchTree(Comparator<K> comparator, RandomAccessFile file)  { 

		super(file);
		
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

		return insertAtExternal(insertPosition, new LinkedBinarySearchTreeEntry<K,V>(key, value));

	}

	public Iterable<Entry<K,V>> entries() {

		List<Entry<K,V>> entries = new LinkedList<Entry<K,V>>();

		Iterable<Position<Entry<K,V>>> positions = positions();

		for (Position<Entry<K,V>> position: positions) if (isInternal(position)) entries.insertLast(position.element());

		return entries;
		
	}
	
}