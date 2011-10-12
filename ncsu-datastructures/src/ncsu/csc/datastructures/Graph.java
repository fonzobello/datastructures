package ncsu.csc.datastructures;
public interface Graph<V, E> {

	public int numVertices();

	public int numEdges();

	public Iterable<Vertex<V>> vertices();

	public Iterable<Edge<E>> edges();

	public V replace(Vertex<V> vertex, V element) throws InvalidPositionException;

	public E replace(Edge<E> edge, E element) throws InvalidPositionException;

	public Iterable<Edge<E>> incidentEdges(Vertex<V> vertex) throws InvalidPositionException;

	public Vertex<V>[] endVertices(Edge<E> edge) throws InvalidPositionException;

	public Vertex<V> opposite(Vertex<V> vertex, Edge<E> edge) throws InvalidPositionException;

	public boolean areAdjacent(Vertex<V> firstVertex, Vertex<V> secondVertex) throws InvalidPositionException;

	public Vertex<V> insertVertex(V element);

	public Edge<E> insertEdge(Vertex<V> firstVertex, Vertex<V> secondVertex, E element) throws InvalidPositionException;

	public V removeVertex(Vertex<V> vertex) throws InvalidPositionException;

	public E removeEdge(Edge<E> edge) throws InvalidPositionException;
}