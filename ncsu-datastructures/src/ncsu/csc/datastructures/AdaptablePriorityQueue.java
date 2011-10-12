package ncsu.csc.datastructures;

public interface AdaptablePriorityQueue<K, V> extends PriorityQueue<K, V> {

	public Entry<K,V> remove(Entry<K,V> entry);

	public K replaceKey(Entry<K,V> entry, K key);

	public V replaceValue(Entry<K,V> entry, V value); 

}
