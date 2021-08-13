package nets150_hw5;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*WikiScraper w2 = new WikiScraper("Sudoku", "Code of law", 100000, 0.5);
		Graph g3 = w2.getGraph();
		g3.printGraph();
		System.out.println(w2.getConnectivity());*/

		/*WikiScraper w = new WikiScraper("Julienning", "Matchstick", 100000, 0.5);
		Graph g = w.getGraph();
		g.printGraph();
		System.out.println(w.getConnectivity());
		
		HashMap<String, Double> btwns = g.betweenness();
		Set<String> btwnsSet = btwns.keySet();
		for (String name: btwnsSet) {
			System.out.println(name + "= " + btwns.get(name));
		}*/
		
		/*Graph g2 = new Graph();
		g2.addVertex("A");
		g2.addVertex("B");
		g2.addVertex("C");
		g2.addVertex("D");
		g2.addVertex("E");
		g2.addEdge("A", "B");
		g2.addEdge("A", "C");
		g2.addEdge("B", "D");
		g2.addEdge("C", "D");
		g2.addEdge("D", "E");
		g2.addEdge("E", "A");
		
		System.out.println(g2.contains("A"));
		System.out.println(g2.shortestPath("A"));
		
		System.out.println(g2.numShortestPath("A"));
		
		Edge e = new Edge("A", "B");
		Edge e2 = new Edge("A", "B");
		System.out.println(e.equals(e2));
		HashSet<Edge> edges = new HashSet<Edge>();
		edges.add(e);
		System.out.println(edges.contains(e2));*/
		
		Graph g3 = new Graph();
		g3.addVertex("A");
		g3.addVertex("B");
		g3.addVertex("C");
		g3.addVertex("D");
		g3.addVertex("E");
		g3.addVertex("F");
		//g3.addEdge("F", "A");
		g3.addEdge("A", "B");
		//g3.addEdge("A", "C");
		g3.addEdge("B", "C");
		g3.addEdge("C", "D");
		//g3.addEdge("D", "E");
		
		g3.betweennessCluster(4);
		System.out.println(g3.getAdjacent("A").contains("B"));
		
		HashMap<String, Double> btwns = g3.betweenness();
		Set<String> btwnsSet = btwns.keySet();
		for (String name: btwnsSet) {
			System.out.println(name + "= " + btwns.get(name));
		}
		
		WikiScraper w3 = new WikiScraper("Julienning", "Matchstick", 100000, 5);
		Graph g = w3.getGraph();
		g.printGraph();
		System.out.println(w3.getConnectivity());
	}

}
