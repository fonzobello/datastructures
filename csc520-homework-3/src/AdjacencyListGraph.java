/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

import java.util.Iterator;

public class AdjacencyListGraph<V,E> implements Graph<V,E> {
	
	protected class AdjacencyListGraphEdge<E> extends AdjacencyListGraphNode<E> implements Edge<E> {
		
		protected AdjacencyListGraphVertex<V>[] _Vertices;
		
		protected Position<Edge<E>>[] _IncidencePositions;
		
		protected Position<Edge<E>> _Location;
		
		public AdjacencyListGraphEdge(Vertex<V> startVertex, Vertex<V> endVertex, E element) {
			
			_Element = element;
			
			_Vertices = (AdjacencyListGraphVertex<V>[]) new AdjacencyListGraphVertex[2];
			
			_Vertices[0] = (AdjacencyListGraphVertex<V>)startVertex;
			
			_Vertices[1] = (AdjacencyListGraphVertex<V>)endVertex;
			
			_IncidencePositions = (Position<Edge<E>>[]) new Position[2];
			
		}
		
		public AdjacencyListGraphVertex<V>[] endVertices() {
			
			return _Vertices;
			
		}
		
		public Position<Edge<E>>[] incidences() {
			
			return _IncidencePositions;
			
		}
		
		public void setIncidences(Position<Edge<E>> firstPosition, Position<Edge<E>> secondPosition) {
			
			_IncidencePositions[0] = firstPosition;
		      
			_IncidencePositions[1] = secondPosition;
			
		}
		
		public Position<Edge<E>> location() {
			
			return _Location;
			
		}
		
		public void setLocation(Position<Edge<E>> position) {
			
			_Location = position;
			
		}
		
		public String toString() {
			
			return element() + "(" + _Vertices[0].toString() + "," + _Vertices[1].toString() + ")";
			
		}
		
	}
	
	protected static class AdjacencyListGraphNode<T> extends HashTableMap<Object,Object> implements DecorablePosition<T> {
		
		protected T _Element;
		
		protected AdjacencyListGraphNode() {}
		
		@Override
		public T element() {

			return _Element;
			
		}
		
		public void setElement(T element) {
			
			_Element = element;
			
		}
	
	}
	
	protected class AdjacencyListGraphVertex<V> extends AdjacencyListGraphNode<V> implements Vertex<V> {

		protected List<Edge<E>> _IncidenceEdges;
		
		protected Position<Vertex<V>> _Location;
		
		public AdjacencyListGraphVertex(V vertex) {
			
			_Element = vertex;
			
			_IncidenceEdges = new LinkedList<Edge<E>>();
		}
		
		public int degree() {
			
			return _IncidenceEdges.size();
			
		}
		
		
		public Iterable<Edge<E>> incidentEdges() {
			
			return _IncidenceEdges;
			
		}
		
		public Position<Edge<E>> insertIncidence(Edge<E> edge) {
			
			_IncidenceEdges.insertLast(edge);
			
			return _IncidenceEdges.last();
			
		}
		
		public void removeIncidence(Position<Edge<E>> position) {
			
			_IncidenceEdges.remove(position);
			
		}
		
		public Position<Vertex<V>> location() {
			
			return _Location;
			
		}
		
		public void setLocation(Position<Vertex<V>> position) {
			
			_Location = position;
			
		}
		
		public String toString() {
		
			return _Element.toString();
			
		}
		
	}
	
	protected List<Vertex<V>> _VertexList;

	protected List<Edge<E>> _EdgeList;

	public AdjacencyListGraph() {

		_VertexList = new LinkedList<Vertex<V>>();

		_EdgeList = new LinkedList<Edge<E>>();

	}

	public Iterable<Vertex<V>> vertices() {

		return _VertexList;

	}

	public Iterable<Edge<E>> edges() {

		return _EdgeList;

	}

	public Object replace(Position position, Object element) throws InvalidPositionException {

		AdjacencyListGraphNode graphPosition = checkPosition(position);

		Object temp = position.element();

		graphPosition.setElement(element);

		return temp;

	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> vertex) throws InvalidPositionException {

		return checkVertex(vertex).incidentEdges();

	}

	public Vertex<V>[] endVertices(Edge<E> edge) throws InvalidPositionException {

		return checkEdge(edge).endVertices();

	}

	public Vertex<V> opposite(Vertex<V> vertex, Edge<E> edge) throws InvalidPositionException {

		checkVertex(vertex);

		Vertex<V>[] endVertices = checkEdge(edge).endVertices();

		if (vertex == endVertices[0]) return endVertices[1];

		else if (vertex == endVertices[1]) return endVertices[0];

		else throw new InvalidPositionException();

	}
	
	public Vertex<V> startVertex(Edge<E> edge) throws InvalidPositionException {
		
		Vertex<V>[] endVertices = checkEdge(edge).endVertices();

		if (endVertices.length == 2) return endVertices[0];

		else throw new InvalidPositionException();

	}
	
	public Vertex<V> endVertex(Edge<E> edge) throws InvalidPositionException {
		
		Vertex<V>[] endVertices = checkEdge(edge).endVertices();

		if (endVertices.length == 2) return endVertices[1];

		else throw new InvalidPositionException();

	}

	public boolean areAdjacent(Vertex<V> firstVertex, Vertex<V> secondVertex) throws InvalidPositionException {

		Iterable<Edge<E>> iterable;

		if (degree(firstVertex) < degree(secondVertex)) iterable = incidentEdges(firstVertex);

		else iterable = incidentEdges(secondVertex);

		for (Edge<E> edge: iterable ) {

			Vertex<V>[] endVertices = endVertices(edge);

			if ((endVertices[0] == firstVertex && endVertices[1] == secondVertex) || (endVertices[0] == secondVertex && endVertices[1] == firstVertex)) return true;

		}

		return false;

	}

	public Vertex<V> insertVertex(V vertex) {

		AdjacencyListGraphVertex<V> toInsert =  new AdjacencyListGraphVertex<V>(vertex);

		_VertexList.insertLast(toInsert);

		Position<Vertex<V>> position = _VertexList.last();

		toInsert.setLocation(position);

		return toInsert;

	}

	public Edge<E> insertEdge(Vertex<V> startVertex, Vertex<V> endVertex, E element) throws InvalidPositionException {

		AdjacencyListGraphEdge<E> toInsert = new AdjacencyListGraphEdge<E>(startVertex, endVertex, element);

		Position<Edge<E>> startVertexPosition = checkVertex(startVertex).insertIncidence(toInsert);

		Position<Edge<E>> endVertexPosition = checkVertex(endVertex).insertIncidence(toInsert);

		toInsert.setIncidences(startVertexPosition, endVertexPosition);

		_EdgeList.insertLast(toInsert);

		Position<Edge<E>> edgePosition = _EdgeList.last();

		toInsert.setLocation(edgePosition);

		return toInsert;

	}

	public V removeVertex(Vertex<V> vertex) throws InvalidPositionException {

		AdjacencyListGraphVertex<V> graphVertex = checkVertex(vertex);
		
		Iterator<Edge<E>> iterator = incidentEdges(vertex).iterator();

		while (iterator.hasNext()) {

			AdjacencyListGraphEdge<E> edge = (AdjacencyListGraphEdge<E>) iterator.next();

			if (edge.location() != null)

				removeEdge(edge);

		}

		_VertexList.remove(graphVertex.location());

		return vertex.element();

	}

	public E removeEdge(Edge<E> edge) throws InvalidPositionException {

		AdjacencyListGraphEdge<E> graphEdge = checkEdge(edge);

		AdjacencyListGraphVertex<V>[] endVertices = graphEdge.endVertices();

		Position<Edge<E>>[] inc = graphEdge.incidences();

		endVertices[0].removeIncidence(inc[0]);

		endVertices[1].removeIncidence(inc[1]);

		_EdgeList.remove(graphEdge.location());

		graphEdge.setLocation(null);

		return edge.element();

	}

	public int degree(Vertex<V> vertex) {

		return checkVertex(vertex).degree();

	}

	protected AdjacencyListGraphNode checkPosition(Position position) throws InvalidPositionException {

		if (position == null || !(position instanceof AdjacencyListGraphNode)) throw new InvalidPositionException();

		return (AdjacencyListGraphNode) position;

	}

	protected AdjacencyListGraphVertex<V> checkVertex(Vertex<V> vertex) throws InvalidPositionException {

		if (vertex == null || !(vertex instanceof AdjacencyListGraphVertex)) throw new InvalidPositionException();

		return (AdjacencyListGraphVertex<V>) vertex;

	}

	protected AdjacencyListGraphEdge<E> checkEdge(Edge<E> edge) throws InvalidPositionException {

		if (edge == null || !(edge instanceof AdjacencyListGraphEdge)) throw new InvalidPositionException();

		return (AdjacencyListGraphEdge<E>) edge;

	}

	public String toString() {

		return _VertexList.toString() + "\n" +  _EdgeList.toString();

	}

	public int numVertices() {

		return _VertexList.size();

	}

	public int numEdges() {

		return _EdgeList.size();

	}

	public V replace(Vertex<V> vertex, V element) throws InvalidPositionException {

		V toReturn = vertex.element();

		AdjacencyListGraphVertex<V> graphVertex = checkVertex(vertex);

		graphVertex.setElement(element);

		return toReturn;

	}
 
	public E replace(Edge<E> edge, E element) throws InvalidPositionException {

		E toReturn = edge.element();

		AdjacencyListGraphEdge<E> graphEdge = checkEdge(edge);

		graphEdge.setElement(element);

		return toReturn;

	}	

}