/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

public interface Dictionary<K,V> {

	public int size();

	public boolean isEmpty();

	public Entry<K,V> find(K key) throws InvalidKeyException;

	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException;

	public Entry<K,V> insert(K key, V value) throws InvalidKeyException;

	public Iterable<Entry<K,V>> entries(); 

}
