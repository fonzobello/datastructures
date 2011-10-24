public class HashTableMap<K,V> implements Map<K,V> {

	public static class HashEntry<K,V> implements Entry<K,V> {

		protected K _Key;

		protected V _Value;

		public HashEntry(K key, V value) {
			
			_Key = key;
			
			_Value = value;
		
		}

		public V getValue() {
			
			return _Value;
			
		}

		public K getKey() {
			
			return _Key;
			
		}

		public V setValue(V value) {

			V toReturn = _Value;

			_Value = value;

			return toReturn;

		}

		public boolean equals(Object object) {

			HashEntry<K,V> entry;

			try {
				
				entry = (HashEntry<K,V>) object;

			} catch (ClassCastException e) {
				
				return false;
				
			}

			return (entry.getKey() == _Key) && (entry.getValue() == _Value);

		}

		public String toString() {

			return "(" + _Key + "," + _Value + ")";

		}

	}

	protected Entry<K,V> _Available = new HashEntry<K,V>(null, null);

	protected int _Size = 0;

	protected int _Prime;
	
	protected int _Capacity;

	protected Entry<K,V>[] _Bucket;

	protected long _Scale;
	
	protected long _Shift;

	public HashTableMap() {
		
		this(109345121,1000);
		
	}

	public HashTableMap(int capacity) {
		
		this(109345121, capacity);
		
	}

	public HashTableMap(int prime, int capacity) {

		_Prime = prime;

		_Capacity = capacity;

		_Bucket = (Entry<K,V>[]) new Entry[_Capacity];

		java.util.Random rand = new java.util.Random();

		_Scale = rand.nextInt(_Prime - 1) + 1;

		_Shift = rand.nextInt(_Prime);

	}

	protected void checkKey(K key) {

		if (key == null) throw new InvalidKeyException();

	}

	public int hashValue(K key) {

		return (int) ((Math.abs(key.hashCode() * _Scale + _Shift) % _Prime) % _Capacity);

	}

	public int size() {
		
		return _Size;
		
	}

	public boolean isEmpty() {
		
		return (_Size == 0);
		
	}

	public Iterable<K> keySet() {

		List<K> keys = new LinkedList<K>();

		for (int i = 0; i < _Capacity; i++) 

			if ((_Bucket[i] != null) && (_Bucket[i] != _Available)) 

				keys.insertLast(_Bucket[i].getKey());

		return keys;

	}

	protected int findEntry(K key) throws InvalidKeyException {

		int available = -1;

		checkKey(key);

		int i = hashValue(key);

		int j = i;

		do {

			Entry<K,V> e = _Bucket[i];

			if ( e == null) {

				if (available < 0)

					available = i;

				break;

			}

			if (key.equals(e.getKey()))

				return i;

			if (e == _Available) {

				if (available < 0)

					available = i;

			}

			i = (i + 1) % _Capacity;

		} while (i != j);

		return -(available + 1);

	}

	public V get (K key) throws InvalidKeyException {

		int i = findEntry(key);

		if (i < 0) return null;

		return _Bucket[i].getValue();

	}

	public V put (K key, V value) throws InvalidKeyException {

		int i = findEntry(key);

		if (i >= 0)

			return ((HashEntry<K,V>) _Bucket[i]).setValue(value);

		if (_Size >= _Capacity/2) {

			rehash();

			i = findEntry(key);

		}

		_Bucket[-i-1] = new HashEntry<K,V>(key, value);

		_Size++;

		return null;

	}

	protected void rehash() {

		_Capacity = 2*_Capacity;

		Entry<K,V>[] old = _Bucket;

		_Bucket = (Entry<K,V>[]) new Entry[_Capacity];

		java.util.Random rand = new java.util.Random();

		_Scale = rand.nextInt(_Prime-1) + 1;

		_Shift = rand.nextInt(_Prime);

		for (int i=0; i<old.length; i++) {

			Entry<K,V> e = old[i];

			if ((e != null) && (e != _Available)) {

				int j = - 1 - findEntry(e.getKey());

				_Bucket[j] = e;

			}

		}

	}

	public V remove (K key) throws InvalidKeyException {

		int i = findEntry(key);

		if (i < 0) return null;

		V toReturn = _Bucket[i].getValue();

		_Bucket[i] = _Available;

		_Size--;

		return toReturn;

	}

	public Iterable<Entry<K,V>> entrySet() {

		List<Entry<K,V>> entries = new LinkedList<Entry<K,V>>();

		for (int i=0; i<_Capacity; i++) 

			if ((_Bucket[i] != null) && (_Bucket[i] != _Available)) 

				entries.insertLast(_Bucket[i]);

		return entries;

	}

	public Iterable<V> values() {

		List<V> values = new LinkedList<V>();

		for (int i=0; i<_Capacity; i++) 

			if ((_Bucket[i] != null) && (_Bucket[i] != _Available)) 

				values.insertLast(_Bucket[i].getValue());

		return values;

	}

} 