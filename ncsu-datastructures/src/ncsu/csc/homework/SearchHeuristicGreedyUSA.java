package ncsu.csc.homework;

import ncsu.csc.datastructures.Edge;
import ncsu.csc.datastructures.LinkedList;
import ncsu.csc.datastructures.List;
import ncsu.csc.datastructures.Vertex;
import ncsu.csc.search.SearchHeuristicGreedy;

public class SearchHeuristicGreedyUSA extends SearchHeuristicGreedy<City, Integer, List<Vertex<City>>> {

	public LinkedList<Vertex<City>> _Expanded;
	
	public SearchHeuristicGreedyUSA() {
		
		super();
		
		_Expanded = new LinkedList<Vertex<City>>();
		
	}
	
	protected Double heuristic(Vertex<City> current, Vertex<City> goal) {
		
		//   Heuristic estimate of D-2 (straight-line) distance between two cities.
		//   Procedure city/3 contains decimal degrees of latitude and longitude.
		//   
		//   A complication: a degree of arc length does NOT subtend one degree of
		//   longitude at this latitude, because the earth is, of course, not
		//   cylindrical.
		//
		//   Therefore, taking the circumference of the earth as 40032 km, one
		//   degree of latitude equals about 111.2 km (= 40032/360) = 69.5 mi,
		//   but one degree of longitude equals 111.2 km only at the equator.
		//   At other latitudes, one degree longitude equals 111.2 * cos(latitude) km.
		//
		//   For Romania, we can safely assume latitude is 45 degrees N, close enough for a heuristic.
		//   cos(45) = 0.707
		//   But for more generality, as in the US, we need to use average latitude of the two cities.
		
		Double deltaLatitude = Math.abs(current.element().getLatitude() - goal.element().getLatitude());
		
		Double deltaLongitude = Math.abs(current.element().getLongitude() - goal.element().getLongitude());
		
		Double distanceX = deltaLongitude * 69.5 * Math.cos((current.element().getLatitude() + goal.element().getLatitude()) / 2);
		
		Double distanceY = deltaLatitude * 69.5;
		
		return Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2)); 
		
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
