package nets150_hw5;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph {
	
	private HashMap<String, HashSet<String>> adjMap;

	public Graph() {
		adjMap = new HashMap<String, HashSet<String>>();
	}
	
	/**
	 * 
	 * @param name of vertex to add
	 * 
	 * adds vertex to graph
	 */
	public void addVertex(String name) {
		adjMap.put(name, new HashSet<String>());
	}
	
	/**
	 * 
	 * @param n1: start node of edge
	 * @param n2: end node of edge
	 * 
	 * adds directed edge to graph
	 */
	public void addEdge(String n1, String n2) {
		if(adjMap.containsKey(n1) && adjMap.containsKey(n2)){
			adjMap.get(n1).add(n2);
		}
		else {
			throw new IllegalArgumentException("Vertex doesn't exist");
		}
	}
	
	/**
	 * 
	 * @param vertex name
	 * @return set of vertices the given vertex points to
	 */
	public HashSet<String> getAdjacent(String vertex) {
		HashSet<String> toReturn = adjMap.get(vertex);
		return toReturn;
	}
	
	/**
	 * 
	 * @param vertex
	 * @return whether or not graph contains vertex
	 */
	public boolean contains(String vertex) {
		return adjMap.containsKey(vertex);
	}
	
	/**
	 * prints out list of vertices followed by set of each vertex's edges
	 */
	public void printGraph() {
		for (String vertex: adjMap.keySet()) {
			System.out.println("Vertex: " + vertex);
			System.out.println("Edges: " + adjMap.get(vertex).toString());
		}
	}
	
	/**
	 * 
	 * @param start name
	 * @return map of vertices and their distance from start vertex
	 */
	public HashMap<String, Integer> shortestPath(String start) {
		HashMap<String, Integer> distances = new HashMap<String, Integer>();
		LinkedList<Set<String>> toSearch = new LinkedList<Set<String>>();
		
		Set<String> startSet = new HashSet<String>();
		startSet.add(start);
		
		toSearch.add(startSet);
		
		int counter = 1;
		
		while (!toSearch.isEmpty()) {
			Set<String> counterSearch = toSearch.remove();
			Set<String> addToSearch = new HashSet<String>();
			
			for (String s: counterSearch) {
				Set<String> search = adjMap.get(s);
				
				for (String s1: search) {
					if (!distances.containsKey(s1)) {
						distances.put(s1, counter);
						addToSearch.add(s1);
					}
				}
			}
			
			if (!addToSearch.isEmpty()) {
				toSearch.add(addToSearch);
			}
			counter++;
		}
		
		return distances;
	}
	
	/**
	 * 
	 * @param start vertex
	 * @param end vertex
	 * @return length of shortest path from start to end
	 */
	public int shortestPathLength(String start, String end) {
		HashMap<String, Integer> shortest = shortestPath(start);
		return shortest.get(end);
	}
	
	/**
	 * 
	 * @param start vertex
	 * @return map of vertices and the number of shortest paths between each and start vertex
	 */
	public HashMap<String, Integer> numShortestPath(String start) {
		HashMap<String, Integer> numShortest = new HashMap<String, Integer>();
		HashMap<String, Integer> distances = new HashMap<String, Integer>();

		LinkedList<Set<String>> toSearch = new LinkedList<Set<String>>();
		
		Set<String> startSet = new HashSet<String>();
		startSet.add(start);
		
		toSearch.add(startSet);
		
		int counter = 1;
		
		while (!toSearch.isEmpty()) {
			Set<String> counterSearch = toSearch.remove();
			Set<String> addToSearch = new HashSet<String>();
			
			for (String s: counterSearch) {
				Set<String> search = adjMap.get(s);
				
				for (String s1: search) {
					if (!distances.containsKey(s1)) {
						distances.put(s1, counter);
						numShortest.put(s1, 1);
						addToSearch.add(s1);
					} else if (distances.get(s1) == counter) {
						numShortest.replace(s1, numShortest.get(s1) + 1);
					}
				}
			}
			
			if (!addToSearch.isEmpty()) {
				toSearch.add(addToSearch);
			}
			counter++;
		}
		
		return numShortest;
	}
	
	/**
	 * 
	 * @return map of edge names (start " to " end) to betweenness value
	 */
	public HashMap<String, Double> betweenness() {
		HashMap<String, Double> btwns = new HashMap<String, Double>();
		
		Set<String> allKeys = adjMap.keySet();
		
		//loop through each possible start key
		for (String key: allKeys) {
	
			//stores length of shortest path from key to each end vertex
			HashMap<String, Integer> pathLength = shortestPath(key);
			
			//stores number of of shortest paths from key to each end vertex
			HashMap<String, Integer> numPaths = numShortestPath(key);
			
			//stores mapping of end vertex key to edges along path from given vertex
			HashMap<String, LinkedList<Edge>> incEdges = new HashMap<String, 
					LinkedList<Edge>>();
			
			//creates set with just key to start next loop
			Set<String> startSet = new HashSet<String>();
			startSet.add(key);
			
			//list of sets to search
			LinkedList<Set<String>> toSearch = new LinkedList<Set<String>>();
			
			toSearch.add(startSet);
			
			int counter = 1;
			
			//distance from given vertex to map key (end vertex)
			HashSet<String> searched = new HashSet<String>();
			
			//loop through each frontier
			while (!toSearch.isEmpty()) {				
				Set<String> counterSearch = toSearch.remove();
				Set<String> addToSearch = new HashSet<String>();
				
				for (String s: counterSearch) {
					Set<String> search = adjMap.get(s);
					
					for (String s1: search) {
						Edge e = new Edge(s, s1);
						e.addBetweenness(1.0/numPaths.get(s1));
						System.out.println(1.0/numPaths.get(s1));
						
						if (!searched.contains(s1)) {
							LinkedList<Edge> toPut = new LinkedList<Edge>();
							
							if (incEdges.get(s) != null) {
								for (Edge inc: incEdges.get(s)) {
									Edge copy = new Edge(inc.getStart(), inc.getEnd());
									copy.setBetweenness(1.0/numPaths.get(s1));
									toPut.add(copy);
								}
							}
							
							toPut.add(e);
							incEdges.put(s1, toPut);
							
							searched.add(s1);
							addToSearch.add(s1);
						} else if (counter == pathLength.get(s1)) {
							LinkedList<Edge> toPut = new LinkedList<Edge>();
							
							//add old incEdges to list
							if (incEdges.get(s1) != null) {
								for (Edge inc: incEdges.get(s1)) {
									inc.setBetweenness(1.0/numPaths.get(s1));
									toPut.add(inc);
								}
							}
							
							//add new incEdges to list
							if (incEdges.get(s) != null) {
								for (Edge inc: incEdges.get(s)) {
									Edge copy = new Edge(inc.getStart(), inc.getEnd());
									copy.setBetweenness(1.0/numPaths.get(s1));
									toPut.add(copy);
								}
							}
							
							//add currently searching edge to list
							toPut.add(e);
							
							incEdges.replace(s1, toPut);
						}
					}
				}
				
				if (!addToSearch.isEmpty()) {
					toSearch.add(addToSearch);
				}
				counter++;
			}
			
			//update btwns based on incEdges for HashMap<String, Double>
			Collection<LinkedList<Edge>> edges = incEdges.values();
			for (LinkedList<Edge> list: edges) {
				while (!list.isEmpty()) {
					Edge e = list.remove();
					String name = e.getStart() + " to " + e.getEnd();
					double b = e.getBetweenness();
					System.out.println(e.getStart() + e.getEnd() + b);
					if (btwns.containsKey(name)) {
						double oldB = btwns.get(name);
						btwns.replace(name, oldB + b);
					} else {
						btwns.put(name, b);
					}
				}
			}
		}
		
		return btwns;
	}
	
	public void betweennessCluster(double threshold) {
		LinkedList<Entry<String, Double>> lowestEntry = new LinkedList<Entry<String, Double>>();
		double lowest = threshold;
		
		while (lowest <= threshold) {
			HashMap<String, Double> btwns = this.betweenness();
			Set<Entry<String, Double>> entrySet = btwns.entrySet();
			
			for (Entry<String, Double> e: entrySet) {
				if (e.getValue() < lowest) {
					lowestEntry.addFirst(e);
				}
			}
			
			if (lowestEntry.isEmpty()) {
				return;
			}
			
			Pattern p = Pattern.compile("(.*?) to (.*)");
			Matcher m = p.matcher(lowestEntry.remove().getKey());
			
			if (m.find()) {
				String start = m.group(1);
				String end = m.group(2);
				
				this.adjMap.get(start).remove(end);
			} else {
				throw new IllegalArgumentException();
			}
		}
	}
	   public HashMap<String, HashSet<String>> getGraph() {
	        HashMap<String, HashSet<String>> copy = new HashMap<String, HashSet<String>>(adjMap);
	        return copy;
	    }
}
