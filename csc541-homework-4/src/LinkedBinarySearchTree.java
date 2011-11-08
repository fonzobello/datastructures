/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

import java.io.RandomAccessFile;

import java.text.DecimalFormat;

import java.util.Comparator;

import java.util.Date;

public class LinkedBinarySearchTree<K,V> extends LinkedBinaryTree<Entry<K,V>> implements Dictionary<K,V> {

	protected Comparator<K> _Comparator;

	protected Position<Entry<K,V>> _ActionPosition;

	protected int _NumberEntries = 0;

	private long _ElapsedTime;

	private int _TotalFinds;

	public LinkedBinarySearchTree(RandomAccessFile file)  { 

		super(file);
		
		setComparator(new DefaultComparator<K>()); 

		//addRoot(null);

	}

	public LinkedBinarySearchTree(Comparator<K> comparator, RandomAccessFile file)  { 

		super(file);
		
		setComparator(comparator); 

		//addRoot(null);

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

		//expandExternal(position, null, null);

		K currentKey = key(position);

		int comparison = comparator().compare(entry.getKey(), currentKey);

		if (comparison < 0) insertLeft(position, entry);

		else if (comparison > 0) insertRight(position, entry);

		return entry;

	}

	protected Position<Entry<K,V>> treeSearch(K key, Position<Entry<K,V>> position) {

		if (isExternal(position)) return position;

		else {

			K currentKey = key(position);

			int comparison = comparator().compare(key, currentKey);

			if (comparison < 0) {
				
				if (hasLeft(position)) return treeSearch(key, left(position));
				
				else return position;
				
			}

			else if (comparison > 0) {
				
				if (hasRight(position)) return treeSearch(key, right(position));
				
				else return position;
			}

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

		long lStartTime = new Date().getTime();
		
		checkKey(key);

		Position<Entry<K,V>> currentPosition = treeSearch(key, root());

		setActionPosition(currentPosition);
		
		long lEndTime = new Date().getTime();
	 	 
		_ElapsedTime = _ElapsedTime + (lEndTime - lStartTime);
		
		_TotalFinds ++;

		if (currentPosition.element().getKey().equals(key)) {
			
			System.out.println("Record " + key + " exists.");
			
			return entry(currentPosition);
		}

		System.out.println("Record " + key + " does not exist.");
		
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
		
		if (isEmpty()) {
			
			Entry<K,V> toReturn = new LinkedBinarySearchTreeEntry<K,V>(key, value);
			
			addRoot(toReturn);
			
			_NumberEntries++;

			return toReturn;
			
		}

		Position<Entry<K,V>> insertPosition = treeSearch(key, root());

		//while (!isExternal(insertPosition)) insertPosition = treeSearch(key, left(insertPosition));

		setActionPosition(insertPosition);

		return insertAtExternal(insertPosition, new LinkedBinarySearchTreeEntry<K,V>(key, value));

	}

	public Iterable<Entry<K,V>> entries() {

		List<Entry<K,V>> entries = new LinkedList<Entry<K,V>>();

		Iterable<Position<Entry<K,V>>> positions = positions();

		for (Position<Entry<K,V>> position: positions) if (isInternal(position)) entries.insertLast(position.element());

		return entries;
		
	}
	
	public void print() {
		
		super.print();
		
		DecimalFormat myFormatter = new DecimalFormat("0.000000");
		
		System.out.println("Sum: " + myFormatter.format(_ElapsedTime * 0.001) );
		
		System.out.println("Avg: " + myFormatter.format(((_ElapsedTime * 0.001) / _TotalFinds)));
		
	}

}