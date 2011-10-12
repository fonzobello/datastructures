package ncsu.csc.datastructures;

import java.util.Comparator;
import java.util.Iterator;

public class HeapPriorityQueue<K,V> implements PriorityQueue<K,V> {

	protected CompleteBinaryTree<Entry<K,V>> _Collection;

	protected Comparator<K> _Comparator;

	public HeapPriorityQueue() {

		_Collection = new ArrayCompleteBinaryTree<Entry<K,V>>();

		_Comparator = new DefaultComparator<K>();

	}

	public HeapPriorityQueue(Comparator<K> comparator) {

		_Collection = new ArrayCompleteBinaryTree<Entry<K,V>>();

		_Comparator = comparator;

	}

	public void setComparator(Comparator<K> comparator) throws IllegalStateException {

		if(!isEmpty()) throw new IllegalStateException();

		_Comparator = comparator;

	}

	public int size() {
		
		return _Collection.size();
		
	} 

	public boolean isEmpty() {
		
		return _Collection.size() == 0;
		
	}

	public Entry<K,V> min() throws EmptyPriorityQueueException {

		if (isEmpty()) throw new EmptyPriorityQueueException();

		return _Collection.root().element();

	}

	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {

		checkKey(key);

		Entry<K,V> entry = new HeapPriorityQueueEntry<K,V>(key, value);

		upHeap(_Collection.add(entry));

		return entry;

	}

	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {

		if (isEmpty()) throw new EmptyPriorityQueueException();

		Entry<K,V> min = _Collection.root().element();

		if (size() == 1)

			_Collection.remove();

		else {

			_Collection.replace(_Collection.root(), _Collection.remove());

			downHeap(_Collection.root());

		}

		return min;

	}

	protected void checkKey(K key) throws InvalidKeyException {

		try {

			_Comparator.compare(key,key);

		} catch(Exception e) {

			throw new InvalidKeyException();

		}

	}

	protected void upHeap(Position<Entry<K,V>> position) {

		Position<Entry<K,V>> parent;

		while (!_Collection.isRoot(position)) {

			parent = _Collection.parent(position);

			if (_Comparator.compare(parent.element().getKey(), position.element().getKey()) <= 0) break;

			swap(parent, position);

			position = parent;

		}

	}

	protected void downHeap(Position<Entry<K,V>> root) {

		while (_Collection.isInternal(root)) {

			Position<Entry<K,V>> smallerChild;

			if (!_Collection.hasRight(root)) smallerChild = _Collection.left(root);

			else if (_Comparator.compare(_Collection.left(root).element().getKey(), _Collection.right(root).element().getKey()) <= 0) smallerChild = _Collection.left(root);

			else smallerChild = _Collection.right(root);

			if (_Comparator.compare(smallerChild.element().getKey(), root.element().getKey()) < 0) {

				swap(root, smallerChild);

				root = smallerChild;

			} else break;

		}

	}

	protected void swap(Position<Entry<K,V>> positionX, Position<Entry<K,V>> positionY) {

		Entry<K,V> temp = positionX.element();

		_Collection.replace(positionX, positionY.element());

		_Collection.replace(positionY, temp);

	}

	public String toString() {

		return _Collection.toString();

	}

	@Override
	public boolean contains(V value) {
		
		if (isEmpty()) return false;
		
		Iterator<Entry<K, V>> iterator = _Collection.iterator();
		
		while (iterator.hasNext()) {
			
			Entry<K, V> currentEntry = iterator.next();
			
			if (currentEntry.getValue() == value) return true;
			
		}
		
		return false;
		
	}

}