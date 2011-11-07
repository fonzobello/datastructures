
public class LinkedBinarySearchTreeEntry<K,V> implements Entry<K,V> {

	protected K _Key;

	protected V _Value;

	public LinkedBinarySearchTreeEntry() {}

	public LinkedBinarySearchTreeEntry(K key, V value) { 

		setKey(key);
		
		setValue(value);

	}

	public K getKey() {
		
		return _Key;
		
	}

	public V getValue() {
		
		return _Value;
		
	}
	
	public void setKey(K key) {
		
		_Key = key;
		
	}

	public void setValue(V value) {
		
		_Value = value;
		
	}
	
	public String toString() {
		
		return getValue().toString();
		
	}

}