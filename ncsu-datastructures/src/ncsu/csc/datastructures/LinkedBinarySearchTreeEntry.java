package ncsu.csc.datastructures;
public class LinkedBinarySearchTreeEntry<K,V> implements Entry<K,V> {

	protected K _Key;

	protected V _Value;

	protected Position<Entry<K,V>> _Position;

	public LinkedBinarySearchTreeEntry() {}

	public LinkedBinarySearchTreeEntry(K key, V value, Position<Entry<K,V>> position) { 

		setKey(key);
		
		setValue(value);
		
		setPosition(position);

	}

	public K getKey() {
		
		return _Key;
		
	}

	public V getValue() {
		
		return _Value;
		
	}
	
	public Position<Entry<K,V>> position() {
		
		return _Position;
		
	}
	
	public void setKey(K key) {
		
		_Key = key;
		
	}

	public void setValue(V value) {
		
		_Value = value;
		
	}
	
	public void setPosition(Position<Entry<K,V>> position) {
		
		_Position = position;
		
	}

}