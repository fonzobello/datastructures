package ncsu.csc.search;

import ncsu.csc.datastructures.Graph;
import ncsu.csc.datastructures.LinkedList;
import ncsu.csc.datastructures.List;
import ncsu.csc.datastructures.TreePosition;
import ncsu.csc.datastructures.Vertex;

public class SearchPathBacktrack<V, E> extends SearchDepthFirst <V, E, Object, List<V>> {

	protected LinkedList<V> _SolutionPath;
	
	protected TreePosition<E> _ActionPosition;

	protected Vertex<V> _Goal;
	
	public List<V> execute(Graph<V, E> graph, Vertex<V> startVertex, Vertex<V> goal) {
		
		_Goal = goal;
		
		return super.execute(graph, startVertex, null);
		
	}
	
	protected void setup() {
		
		_SolutionPath = new LinkedList<V>();
		
	}
	
	protected void startVisit(Vertex<V> vertex) {
		
		_SolutionPath.insertLast(vertex.element());
		
	}

	protected void finishVisit(Vertex<V> vertex)  {
		
		_SolutionPath.remove(_SolutionPath.last());
		
	}
	
	protected boolean isDone() {
		
		if (_SolutionPath.isEmpty()) return false;
				
		return (_SolutionPath.last().element().equals(_Goal.element()));
		
	}

	protected List<V> finalResult(List<V> result) { 
		
		return _SolutionPath;
	
	}

}