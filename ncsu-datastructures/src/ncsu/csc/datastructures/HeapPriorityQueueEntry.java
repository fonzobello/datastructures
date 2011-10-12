package ncsu.csc.datastructures;

public class  HeapPriorityQueueEntry<K,V> implements Entry<K,V> {

	protected K _Key;

	protected V _Value;

	public HeapPriorityQueueEntry(K key, V value) {
		
		_Key = key; _Value = value;
		
	}

	public K getKey() {
		
		return _Key;
		
	}

	public V getValue() {
		
		return _Value;
		
	}

	public String toString() {
		
		return "(" + _Key  + "," + _Value + ")";
		
	}

}
