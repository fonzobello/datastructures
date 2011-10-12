package ncsu.csc.search;

import ncsu.csc.datastructures.DecorablePosition;

import ncsu.csc.datastructures.LinkedList;

import ncsu.csc.datastructures.LinkedStack;

import ncsu.csc.datastructures.Edge;

import ncsu.csc.datastructures.Graph;

import ncsu.csc.datastructures.Stack;

import ncsu.csc.datastructures.Vertex;

public class SearchUninformedDepthFirst<V, E, I, R> {

	protected Graph<V, E> _Graph;
	
	protected Stack<LinkedList<Vertex<V>>> _Frontier;

	protected I _Info;

	protected R _VisitResult;

	protected static Object STATUS = new Object();

	protected static Object VISITED = new Object();

	protected static Object UNVISITED = new Object();

	public R execute(Graph<V, E> graph, Vertex<V> startVertex, I info) {

		_Graph = graph;

		_Frontier = new LinkedStack<LinkedList<Vertex<V>>>();
		
		LinkedList<Vertex<V>> initialPath = new LinkedList<Vertex<V>>();
		
		initialPath.insertFirst(startVertex);
		
		_Frontier.push(initialPath);

		_Info = info;

		for(Vertex<V> vertex: _Graph.vertices()) unVisit(vertex);

		for(Edge<E> edge: _Graph.edges()) unVisit(edge);

		setup();

		return finalResult(dfsTraversal());

	}

	protected R dfsTraversal() {

		initResult();

		while (!_Frontier.isEmpty()) {
			
			LinkedList<Vertex<V>> path = _Frontier.pop();
			
			Vertex<V> vertex = path.last().element();
			
			visit(vertex);
			
			if (isDone()) break;
			
			for (Edge<E> edge: _Graph.incidentEdges(vertex)) {
				
				if (!isVisited(edge)) {

					visit(edge);

					Vertex<V> oppositeVertex = _Graph.opposite(vertex, edge);

					if (!isVisited(oppositeVertex)) {
						
						traverseDiscovery(edge, vertex);
						
						LinkedList<Vertex<V>> successorPath = path.clone();
						
						successorPath.insertLast(oppositeVertex);
						
						_Frontier.push(successorPath);
						
					}
					
				}
					
			}
			
		}
		
		return result();

	}

	protected void visit(DecorablePosition<?> position) { position.put(STATUS, VISITED); }

	protected void unVisit(DecorablePosition<?> position) { position.put(STATUS, UNVISITED); }

	protected boolean isVisited(DecorablePosition<?> position) {

		return (position.get(STATUS) == VISITED);

	}

	protected void setup() {}

	protected void initResult() {}

	protected void startVisit(Vertex<V> vertex) {}

	protected void finishVisit(Vertex<V> vertex) {}

	protected void traverseDiscovery(Edge<E> edge, Vertex<V> from) {}

	protected void traverseBack(Edge<E> edge, Vertex<V> from) {}

	protected boolean isDone() { return false; }

	protected R result() { return null; }

	protected R finalResult(R result) { return result; }

}