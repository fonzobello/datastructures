package ncsu.csc.search;

import ncsu.csc.datastructures.DecorablePosition;

import ncsu.csc.datastructures.Edge;

import ncsu.csc.datastructures.Graph;

import ncsu.csc.datastructures.Vertex;

public class SearchBacktrack<V, E, I, R> {

	protected Graph<V, E> _Graph;

	protected Vertex<V> _StartVertex;

	protected I _Info;

	protected R _VisitResult;

	protected static Object STATUS = new Object();

	protected static Object VISITED = new Object();

	protected static Object UNVISITED = new Object();

	public R execute(Graph<V, E> graph, Vertex<V> startVertex, I info) {

		_Graph = graph;

		_StartVertex = startVertex;

		_Info = info;

		for(Vertex<V> vertex: _Graph.vertices()) unVisit(vertex);

		for(Edge<E> edge: _Graph.edges()) unVisit(edge);

		setup();

		return finalResult(dfsTraversal(_StartVertex));

	}

	protected R dfsTraversal(Vertex<V> vertex) {

		initResult();

		if (!isDone()) startVisit(vertex);

		if (!isDone()) {

			visit(vertex);

			for (Edge<E> edge: _Graph.incidentEdges(vertex)) {

				if (!isVisited(edge)) {

					visit(edge);

					Vertex<V> oppositeVertex = _Graph.opposite(vertex, edge);

					if (!isVisited(oppositeVertex)) {

						traverseDiscovery(edge, vertex);

						if (isDone()) break;

						_VisitResult = dfsTraversal(oppositeVertex);

						if (isDone()) break;

					} else {

						traverseBack(edge, vertex);

						if (isDone()) break;

					}

				}

			}

		}

		if(!isDone()) finishVisit(vertex);

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