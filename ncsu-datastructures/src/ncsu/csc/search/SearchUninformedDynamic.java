package ncsu.csc.search;

import java.util.HashMap;

import java.util.HashSet;

import ncsu.csc.datastructures.HeapPriorityQueue;

import ncsu.csc.datastructures.LinkedList;

import ncsu.csc.datastructures.Edge;

import ncsu.csc.datastructures.Graph;

import ncsu.csc.datastructures.Vertex;

public class SearchUninformedDynamic<V, E, R> {

	protected Graph<V, E> _Graph;
	
	protected Vertex<V> _Start;
	
	protected Vertex<V> _Goal;
	
	protected HeapPriorityQueue<Double, Vertex<V>> _OpenSet;
	
	protected HashSet<Vertex<V>> _ClosedSet;
	
	protected HashMap<Vertex<V>, Vertex<V>> _CameFrom;
	
	protected HashMap<Vertex<V>, Double> _GScore;

	protected R _VisitResult;

	public R execute(Graph<V, E> graph, Vertex<V> startVertex, Vertex<V> endVertex) {

		//Initialize Collections
		
			_ClosedSet = new HashSet<Vertex<V>>();
			
			_CameFrom = new HashMap<Vertex<V>, Vertex<V>>();
			
			_GScore = new HashMap<Vertex<V>, Double>();
	
			_OpenSet = new HeapPriorityQueue<Double, Vertex<V>>();
		
		//Set Instance Variables
			
			_Graph = graph;
	
			_Goal = endVertex;
			
			_Start = startVertex;

		//Initialize a priority queue of paths with the one-node path consisting of the initial state
		
			_GScore.put(_Start, 0.0);
			
			_OpenSet.insert(_GScore.get(_Start), _Start);
			
			setup();

		return finalResult(AStarTraversal());

	}

	protected R AStarTraversal() {
		
		//While (queue not empty)
		
		while (!_OpenSet.isEmpty()) {
			
			//Remove path at root (which will be of minimal cost)
			
			Vertex<V> vertex = _OpenSet.removeMin().getValue();
			
			expand(vertex);
			
			//If goal-match, return path
			
			if (isDone(vertex)) return result(reconstructPath(vertex));
			
			//Else extend the path by one node in all possible ways,
			
			else {
				
				_ClosedSet.add(vertex);
				
				//By generating successors of the last node on the path
				
				for (Edge<E> edge: _Graph.incidentEdges(vertex)) {

					Vertex<V> oppositeVertex = _Graph.opposite(vertex, edge);
					
					if (_ClosedSet.contains(oppositeVertex)) continue;
					
					Double tentative_g_score = _GScore.get(vertex) + distance(edge);
					
					if (!_OpenSet.contains(oppositeVertex)) {
						
						_CameFrom.put(oppositeVertex, vertex);

						_GScore.put(oppositeVertex, tentative_g_score);
						
						_OpenSet.insert(_GScore.get(oppositeVertex), oppositeVertex);
					
					} else if (tentative_g_score > _GScore.get(oppositeVertex)) {
						
						_CameFrom.put(oppositeVertex, vertex);

						_GScore.put(oppositeVertex, tentative_g_score);

					}
										
				}
				
			}
			
		}
		
		return null;

	}

	private LinkedList<Vertex<V>> reconstructPath(Vertex<V> vertex) {

		LinkedList<Vertex<V>> toReturn;
		
		if (_CameFrom.containsKey(vertex)) {
			
			toReturn = reconstructPath(_CameFrom.get(vertex));
			
			toReturn.insertLast(vertex);
		
		} else {
			
			toReturn = new LinkedList<Vertex<V>>();
			
			toReturn.insertLast(vertex);
			
		}
		
		return toReturn;
        		
	}

	protected void setup() {}
	
	protected void expand(Vertex<V> vertex) {}
	
	protected Double distance(Edge<E> edge) { return -1.0; }

	protected boolean isDone(Vertex<V> vertex) {
		
		if (vertex.equals(_Goal)) return true;
		
		return false;
		
	}

	protected R result(LinkedList<Vertex<V>> path) { return null; }

	protected R finalResult(R result) { return result; }
	
}
