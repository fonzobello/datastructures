package ncsu.csc.homework;

import ncsu.csc.datastructures.Edge;
import ncsu.csc.datastructures.LinkedList;
import ncsu.csc.datastructures.List;
import ncsu.csc.datastructures.Vertex;
import ncsu.csc.search.SearchUninformedDynamic;

public class SearchUninformedDynamicUSA extends SearchUninformedDynamic<City, Integer, List<Vertex<City>>> {

	public LinkedList<Vertex<City>> _Expanded;
	
	public SearchUninformedDynamicUSA() {
		
		super();
		
		_Expanded = new LinkedList<Vertex<City>>();
		
	}
	
	protected void setup() {
		
		_Expanded = new LinkedList<Vertex<City>>();
		
	}
	
	protected void expand(Vertex<City> vertex) {
		
		_Expanded.insertLast(vertex);
		
	}
	
	protected Double distance(Edge<Integer> edge) {
		
		return edge.element() + 0.0;
		
	}
	
	protected List<Vertex<City>> result(LinkedList<Vertex<City>> path) {
		
		return path;
		
	}
	
}

