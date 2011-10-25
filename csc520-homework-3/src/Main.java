import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Main {
	
	private Graph<String, String> _DiabeticSemanticNetwork;
	
	private HashMap<String, Edge<String>> _Values;
	
	public Main() {
		
		_DiabeticSemanticNetwork = new AdjacencyListGraph<String, String>();
		
		 _Values = new HashMap<String, Edge<String>>();
		
		Vertex<String> david = _DiabeticSemanticNetwork.insertVertex("david");
		Vertex<String> diabetics = _DiabeticSemanticNetwork.insertVertex("diabetics");
		Vertex<String> sugar = _DiabeticSemanticNetwork.insertVertex("sugar");
		Vertex<String> candy = _DiabeticSemanticNetwork.insertVertex("candy");
		Vertex<String> snickers = _DiabeticSemanticNetwork.insertVertex("snickers");

		_DiabeticSemanticNetwork.insertEdge(david, diabetics, "isa");
		_DiabeticSemanticNetwork.insertEdge(diabetics, sugar, "shouldAvoid");
		_DiabeticSemanticNetwork.insertEdge(candy, sugar, "contains");
		_DiabeticSemanticNetwork.insertEdge(snickers, candy, "ako");
		
		expand();
		
		_DiabeticSemanticNetwork = new AdjacencyListGraph<String, String>();
		
		Iterator<Edge<String>> edges = _Values.values().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
			
			_DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(edge), _DiabeticSemanticNetwork.endVertex(edge), edge.element());
			
		}
		
	}
	
	private void expand() {
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
			
			_Values.put(edge.toString(), edge);
			
		}
		
		int previousSize = -1;
		
		while (previousSize != _Values.size()) {
			
			edges = _DiabeticSemanticNetwork.edges().iterator();
			
			while (edges.hasNext()) {
				
				Edge<String> edge = edges.next();
				
				if (edge.element().equals("isa")) {
					
					Iterator<Edge<String>> incidentEdges = _DiabeticSemanticNetwork.incidentEdges(_DiabeticSemanticNetwork.endVertex(edge)).iterator();
					
					while (incidentEdges.hasNext()) {
						
						Edge<String> incidentEdge = incidentEdges.next();
						
						if (_DiabeticSemanticNetwork.startVertex(incidentEdge).equals(_DiabeticSemanticNetwork.endVertex(edge)))
							
							_Values.put(_DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(edge), _DiabeticSemanticNetwork.endVertex(incidentEdge), incidentEdge.element()).toString(), _DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(edge), _DiabeticSemanticNetwork.endVertex(incidentEdge), incidentEdge.element()));
	
					}
					
				}
				
				else if (edge.element().equals("ako")) {
					
					Iterator<Edge<String>> incidentEdges = _DiabeticSemanticNetwork.incidentEdges(_DiabeticSemanticNetwork.endVertex(edge)).iterator();
					
					while (incidentEdges.hasNext()) {
						
						Edge<String> incidentEdge = incidentEdges.next();
						
						if (_DiabeticSemanticNetwork.startVertex(incidentEdge).equals(_DiabeticSemanticNetwork.endVertex(edge)))
	
							_Values.put(_DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(edge), _DiabeticSemanticNetwork.endVertex(incidentEdge), incidentEdge.element()).toString(), _DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(edge), _DiabeticSemanticNetwork.endVertex(incidentEdge), incidentEdge.element()));
						
					}
					
				}
				
				if (edge.element().equals("contains")) {
					
					Iterator<Edge<String>> incidentEdges = _DiabeticSemanticNetwork.incidentEdges(_DiabeticSemanticNetwork.endVertex(edge)).iterator();
					
					while (incidentEdges.hasNext()) {
						
						Edge<String> incidentEdge = incidentEdges.next();
						
						if (_DiabeticSemanticNetwork.endVertex(edge).equals(_DiabeticSemanticNetwork.endVertex(incidentEdge)))
						
						if (incidentEdge.element().equals("shouldAvoid"))
							
							_Values.put(_DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(incidentEdge), _DiabeticSemanticNetwork.startVertex(edge), "shouldAvoid").toString(), _DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(incidentEdge), _DiabeticSemanticNetwork.startVertex(edge), "shouldAvoid"));
							
					}
					
				}
				
			}
			
			previousSize = _Values.size();
			
		}
		
	}
	
	public void executeQuery(String query) throws IOException {
		
		Iterable<String> toReturn = null;
		
		query = query.replaceAll("[\\s]", "");
		
		String[] result = query.split("\\W");
		
		if ((result.length != 4) || (!result[0].equals("value"))) {
			
			System.err.println("ERROR: toplevel: Undefined procedure: " + result[0]);
			
			return;
			
		}
		
		if ((!result[1].substring(0, 1).matches("[A-Z]")) && (!result[2].substring(0, 1).matches("[A-Z]")) && (!result[3].substring(0, 1).matches("[A-Z]"))) {

			toReturn =  query1(result[1], result[2], result[3]);
			
		} else if ((result[1].substring(0, 1).matches("[A-Z]")) && (!result[2].substring(0, 1).matches("[A-Z]")) && (!result[3].substring(0, 1).matches("[A-Z]"))) {
			
			toReturn = query2(result[1], result[2], result[3]);
			
		} else if ((!result[1].substring(0, 1).matches("[A-Z]")) && (result[2].substring(0, 1).matches("[A-Z]")) && (!result[3].substring(0, 1).matches("[A-Z]"))) {
		
			toReturn = query3(result[1], result[2], result[3]);
			
		} else if ((!result[1].substring(0, 1).matches("[A-Z]")) && (!result[2].substring(0, 1).matches("[A-Z]")) && (result[3].substring(0, 1).matches("[A-Z]"))) {
	
			toReturn = query4(result[1], result[2], result[3]);
			
		} else if ((!result[1].substring(0, 1).matches("[A-Z]")) && (result[2].substring(0, 1).matches("[A-Z]")) && (result[3].substring(0, 1).matches("[A-Z]"))) {
		
			toReturn = query5(result[1], result[2], result[3]);
			
		} else if ((result[1].substring(0, 1).matches("[A-Z]")) && (!result[2].substring(0, 1).matches("[A-Z]")) && (result[3].substring(0, 1).matches("[A-Z]"))) {
		
			toReturn = query6(result[1], result[2], result[3]);
			
		} else if ((result[1].substring(0, 1).matches("[A-Z]")) && (result[2].substring(0, 1).matches("[A-Z]")) && (!result[3].substring(0, 1).matches("[A-Z]"))) {
			
			toReturn = query7(result[1], result[2], result[3]);
			
		} else if ((result[1].substring(0, 1).matches("[A-Z]")) && (result[2].substring(0, 1).matches("[A-Z]")) && (result[3].substring(0, 1).matches("[A-Z]"))) {
			
			toReturn = query8(result[1], result[2], result[3]);
			
		}
		
		if (toReturn != null) {
			
			InputStreamReader converter = new InputStreamReader(System.in);

			BufferedReader in = new BufferedReader(converter);
			
			Iterator<String> results = toReturn.iterator();
			
			String currentLine = "";
			
			while (results.hasNext()) {
				
				System.out.print(results.next() + " ");
				
				currentLine = in.readLine();
						
				if (currentLine.equals(".")) break;
				
				else if (currentLine.equals(";")) ;
				
				else break;
				
			}
			
		}
		
	}
	
	public Iterable<String> query1(String node, String slot, String value) {
	
		List<String> toReturn = new LinkedList<String>();
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
			
			if ((_DiabeticSemanticNetwork.startVertex(edge).element().equals(node) &&
					
					_DiabeticSemanticNetwork.endVertex(edge).element().equals(value) &&
					
					(edge.element().equals(slot)))) {
				
				toReturn.insertLast("true");
				
				break;
				
			}
			
		}
		
		toReturn.insertLast("false");
		
		return toReturn;
		
	}
	
	public Iterable<String> query2(String node, String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
			
			if ((_DiabeticSemanticNetwork.endVertex(edge).element().equals(value) &&
					
					(edge.element().equals(slot)))) {
				
				toReturn.insertLast(node + " = " + _DiabeticSemanticNetwork.startVertex(edge));
				
			}
			
		}
		
		toReturn.insertLast("false");
		
		return toReturn;
		
	}
	
	public Iterable<String> query3(String node, String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
			
			if ((_DiabeticSemanticNetwork.startVertex(edge).element().equals(node) &&
					
					(_DiabeticSemanticNetwork.endVertex(edge).element().equals(value)))) {
				
				toReturn.insertLast(slot + " = " + edge.element());
				
			}
			
		}
		
		toReturn.insertLast("false");
		
		return toReturn;
		
	}
	
	public Iterable<String> query4(String node, String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
			
			if ((_DiabeticSemanticNetwork.startVertex(edge).element().equals(node) &&
					
					(edge.element().equals(slot)))) {
				
				toReturn.insertLast(value + " = " + _DiabeticSemanticNetwork.endVertex(edge).element());
				
			}
			
		}
		
		toReturn.insertLast("false");
		
		return toReturn;
		
	}
	
	public Iterable<String> query5(String node, String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
			
			if (_DiabeticSemanticNetwork.startVertex(edge).element().equals(node)) {
				
				toReturn.insertLast(slot + " = " + edge.element() + ",\n" + value + " = " + _DiabeticSemanticNetwork.endVertex(edge).element());
				
			}
			
		}
		
		toReturn.insertLast("false");
		
		return toReturn;
		
	}
	
	public Iterable<String> query6(String node, String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
			
			if ((edge.element().equals(slot))) {
				
				toReturn.insertLast(node + " = " + _DiabeticSemanticNetwork.startVertex(edge).element() + ",\n" + value + " = " + _DiabeticSemanticNetwork.endVertex(edge).element());
				
			}
			
		}
		
		toReturn.insertLast("false");
		
		return toReturn;
		
	}
	
	public Iterable<String> query7(String node, String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
			
			if ( _DiabeticSemanticNetwork.endVertex(edge).element().equals(value)) {
				
				toReturn.insertLast(node + " = " + _DiabeticSemanticNetwork.startVertex(edge).element() + ",\n" + slot + " = " + edge.element());
				
			}
			
		}
		
		toReturn.insertLast("false");
		
		return toReturn;
		
	}
	
	public Iterable<String> query8(String node, String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			Edge<String> edge = edges.next();
				
				toReturn.insertLast(node + " = " + _DiabeticSemanticNetwork.startVertex(edge).element() + ",\n" + slot + " = " + edge.element() + ",\n" + value + " = " + _DiabeticSemanticNetwork.endVertex(edge).element());
			
		}
		
		toReturn.insertLast("false");
		
		return toReturn;
		
	}
	
	public static void main(String[] args) {
		
		Main main = new Main();
		
		InputStreamReader converter = new InputStreamReader(System.in);

		BufferedReader in = new BufferedReader(converter);

		String currentLine = "";
		
		while (!(currentLine.equals("end"))){
			
			try {
				
				System.out.print("?- ");
				
				currentLine = in.readLine();
				
				main.executeQuery(currentLine);
				
			} catch (IOException e) {}
		
		}
		
	}
	
}
