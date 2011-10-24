import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

public class Main {
	
	private Graph<String, String> _DiabeticSemanticNetwork;
	
	public Main() {
		
		_DiabeticSemanticNetwork = new AdjacencyListGraph<String, String>();
		
		Vertex<String> david = _DiabeticSemanticNetwork.insertVertex("david");
		Vertex<String> diabetics = _DiabeticSemanticNetwork.insertVertex("diabetics");
		Vertex<String> sugar = _DiabeticSemanticNetwork.insertVertex("sugar");
		Vertex<String> candy = _DiabeticSemanticNetwork.insertVertex("candy");
		Vertex<String> snickers = _DiabeticSemanticNetwork.insertVertex("snickers");

		_DiabeticSemanticNetwork.insertEdge(david, diabetics, "isa");
		_DiabeticSemanticNetwork.insertEdge(diabetics, sugar, "shouldAvoid");
		_DiabeticSemanticNetwork.insertEdge(candy, sugar, "contains");
		_DiabeticSemanticNetwork.insertEdge(snickers, candy, "ako");
		
	}
	
	public void expand() {
		
		HashSet<String> values = new HashSet<String>();
		
		Iterator<Edge<String>> edges = _DiabeticSemanticNetwork.edges().iterator();
		
		while (edges.hasNext()) {
			
			values.add(edges.next().toString());
			
		}
		
		int previousSize = -1;
		
		while (previousSize != values.size()) {
			
			edges = _DiabeticSemanticNetwork.edges().iterator();
			
			while (edges.hasNext()) {
				
				Edge<String> edge = edges.next();
				
				if (edge.element().equals("isa")) {
					
					Iterator<Edge<String>> incidentEdges = _DiabeticSemanticNetwork.incidentEdges(_DiabeticSemanticNetwork.endVertex(edge)).iterator();
					
					while (incidentEdges.hasNext()) {
						
						Edge<String> incidentEdge = incidentEdges.next();
						
						if (_DiabeticSemanticNetwork.startVertex(incidentEdge).equals(_DiabeticSemanticNetwork.endVertex(edge)))
							
							values.add(_DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(edge), _DiabeticSemanticNetwork.endVertex(incidentEdge), incidentEdge.element()).toString());
	
					}
					
				}
				
				else if (edge.element().equals("ako")) {
					
					Iterator<Edge<String>> incidentEdges = _DiabeticSemanticNetwork.incidentEdges(_DiabeticSemanticNetwork.endVertex(edge)).iterator();
					
					while (incidentEdges.hasNext()) {
						
						Edge<String> incidentEdge = incidentEdges.next();
						
						if (_DiabeticSemanticNetwork.startVertex(incidentEdge).equals(_DiabeticSemanticNetwork.endVertex(edge)))
	
							values.add(_DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(edge), _DiabeticSemanticNetwork.endVertex(incidentEdge), incidentEdge.element()).toString());
						
					}
					
				}
				
				if (edge.element().equals("contains")) {
					
					Iterator<Edge<String>> incidentEdges = _DiabeticSemanticNetwork.incidentEdges(_DiabeticSemanticNetwork.endVertex(edge)).iterator();
					
					while (incidentEdges.hasNext()) {
						
						Edge<String> incidentEdge = incidentEdges.next();
						
						if (_DiabeticSemanticNetwork.endVertex(edge).equals(_DiabeticSemanticNetwork.endVertex(incidentEdge)))
						
						if (incidentEdge.element().equals("shouldAvoid"))
							
							values.add(_DiabeticSemanticNetwork.insertEdge(_DiabeticSemanticNetwork.startVertex(incidentEdge), _DiabeticSemanticNetwork.startVertex(edge), "shouldAvoid").toString());
							
					}
					
				}
				
			}
			
			previousSize = values.size();
			
		}
		
	}
	
	public Iterable<String> executeQuery(String query) {
		
		query = query.replaceAll("[\\s]", "");
		
		String[] result = query.split("\\W");
		
		if ((result.length != 4) || (!result[0].equals("value"))) {
			
			System.err.println("ERROR: toplevel: Undefined procedure: notVlaue");
			
			return null;
			
		}
		
		if ((!result[1].substring(0, 1).matches("[A-Z]")) && (!result[2].substring(0, 1).matches("[A-Z]")) && (!result[3].substring(0, 1).matches("[A-Z]"))) {

			return query1(result[1], result[2], result[3]);
			
		} else if ((result[1].substring(0, 1).matches("[A-Z]")) && (!result[2].substring(0, 1).matches("[A-Z]")) && (!result[3].substring(0, 1).matches("[A-Z]"))) {
			
			return query2(result[2], result[3]);
			
		} else if ((!result[1].substring(0, 1).matches("[A-Z]")) && (result[2].substring(0, 1).matches("[A-Z]")) && (!result[3].substring(0, 1).matches("[A-Z]"))) {
		
			return query3(result[1], result[3]);
			
		} else if ((!result[1].substring(0, 1).matches("[A-Z]")) && (!result[2].substring(0, 1).matches("[A-Z]")) && (result[3].substring(0, 1).matches("[A-Z]"))) {
	
			return query4(result[1], result[2]);
			
		} else if ((!result[1].substring(0, 1).matches("[A-Z]")) && (result[2].substring(0, 1).matches("[A-Z]")) && (result[3].substring(0, 1).matches("[A-Z]"))) {
		
			return query5(result[1]);
			
		} else if ((result[1].substring(0, 1).matches("[A-Z]")) && (!result[2].substring(0, 1).matches("[A-Z]")) && (result[3].substring(0, 1).matches("[A-Z]"))) {
		
			return query6(result[2]);
			
		} else if ((result[1].substring(0, 1).matches("[A-Z]")) && (result[2].substring(0, 1).matches("[A-Z]")) && (!result[3].substring(0, 1).matches("[A-Z]"))) {
			
			return query7(result[3]);
			
		} else if ((result[1].substring(0, 1).matches("[A-Z]")) && (result[2].substring(0, 1).matches("[A-Z]")) && (result[3].substring(0, 1).matches("[A-Z]"))) {
			
			return query8(result[1], result[2], result[3]);
			
		}
		
		return null;
		
	}
	
	public Iterable<String> query1(String node, String slot, String value) {
	
		List<String> toReturn = new LinkedList<String>();
		
		return toReturn;
		
	}
	
	public Iterable<String> query2(String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		return toReturn;
		
	}
	
	public Iterable<String> query3(String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		return toReturn;
		
	}
	
	public Iterable<String> query4(String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		return toReturn;
		
	}
	
	public Iterable<String> query5(String slot) {
		
		List<String> toReturn = new LinkedList<String>();
		
		return toReturn;
		
	}
	
	public Iterable<String> query6(String slot) {
		
		List<String> toReturn = new LinkedList<String>();
		
		return toReturn;
		
	}
	
	public Iterable<String> query7(String slot) {
		
		List<String> toReturn = new LinkedList<String>();
		
		return toReturn;
		
	}
	
	public Iterable<String> query8(String node, String slot, String value) {
		
		List<String> toReturn = new LinkedList<String>();
		
		return toReturn;
		
	}
	
	public static void main(String[] args) {
		
		Main main = new Main();
		
		main.expand();
		
		InputStreamReader converter = new InputStreamReader(System.in);

		BufferedReader in = new BufferedReader(converter);

		String currentLine = "";
		
		while (!(currentLine.equals("end"))){
			
			try {
				
				currentLine = in.readLine();
				
			} catch (IOException e) {}
			
			main.executeQuery(currentLine);
		
		}
		
	}
	
}
