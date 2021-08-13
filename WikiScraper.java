package nets150_hw5;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiScraper {
	private Graph g;
	private boolean connected;
	
	/**
	 * 
	 * @param start vertex
	 * @param end vertex
	 * @param size of graph
	 * @param random double between 0 and 1 representing probability that a found 
	 * vertex will be added
	 * 
	 * WikiScraper constructor, selects random links on each Wikipedia page and adds them to graph
	 */
	public WikiScraper(String start, String end, int size, double random) {
		
		boolean found = false;
		
		start.replace(" ", "_");
		end.replace(" ", "_");
		
		URLGetter search = new URLGetter("https://en.wikipedia.org/wiki/" + start);
		
		Graph graph = new Graph();
		
		int counter = 0;
				
		LinkedList<String> toSearch = new LinkedList<String>();
		
		toSearch.add(start);
		graph.addVertex(start);
		
		while (counter <= size) {
			String searchName = toSearch.remove();
			System.out.println(searchName);
			counter++;
			search = new URLGetter("https://en.wikipedia.org/wiki/" + searchName);
			
			String contents = search.getContentsString();
						
			Pattern p = Pattern.compile("<div id=\"mw-content-text\" lang=\"en\" dir=\"ltr\" "
					+ "class=\"mw-content-ltr\">(.*)");
			Matcher m = p.matcher(contents);
			
			if (m.find()) {
				Pattern p2 = Pattern.compile("/wiki/(.*?)\"");
				Matcher m2 = p2.matcher(m.group(1));
				
				while (m2.find() && counter <= size) {
					String n = m2.group(1);
										
					if (!n.contains(":")) {
						if (m2.group(1).equals(end)) {
							found = true;
						}
						if (Math.random() < random) {
							counter++;
							if (!graph.contains(n)) {
								graph.addVertex(n);
							}
							graph.addEdge(searchName, n);
							toSearch.add(n);
						}
					}
				}
			}
		}
				
		g = graph;
		connected = found;
	}
	
	/**
	 * 
	 * @param start vertex
	 * @param end vertex
	 * @param size of graph
	 * @param numSearch number of nodes to add to graph on each site
	 * 
	 * WikiScraper constructor, selects first numSearch amount of nodes from each Wikipedia page 
	 * and adds them to graph
	 */
	public WikiScraper(String start, String end, int size, int numSearch) {
		
		boolean found = false;
		
		start.replace(" ", "_");
		end.replace(" ", "_");
		
		URLGetter search = new URLGetter("https://en.wikipedia.org/wiki/" + start);
		
		Graph graph = new Graph();
		
		int counter = 0;
				
		LinkedList<String> toSearch = new LinkedList<String>();
		
		toSearch.add(start);
		graph.addVertex(start);
		
		while (counter <= size) {
			String searchName = toSearch.remove();
			System.out.println(searchName);
			counter++;
			search = new URLGetter("https://en.wikipedia.org/wiki/" + searchName);
			
			String contents = search.getContentsString();
						
			Pattern p = Pattern.compile("<div id=\"mw-content-text\" lang=\"en\" dir=\"ltr\" "
					+ "class=\"mw-content-ltr\">(.*)");
			Matcher m = p.matcher(contents);
			
			int nodesAdded = 0;
			
			if (m.find()) {
				Pattern p2 = Pattern.compile("/wiki/(.*?)\"");
				Matcher m2 = p2.matcher(m.group(1));
				
				while (m2.find() && counter <= size  && nodesAdded < numSearch) {
					String n = m2.group(1);
										
					if (!n.contains(":")) {
						if (m2.group(1).equals(end)) {
							found = true;
						}
						
						counter++;
						if (!graph.contains(n)) {
							graph.addVertex(n);
						}
						graph.addEdge(searchName, n);
						toSearch.add(n);
						nodesAdded++;
					}
				}
			}
		}
				
		g = graph;
		connected = found;
	}
	
	/**
	 * 
	 * @return whether or not the end vertex was reached
	 */
	public boolean getConnectivity() {
		boolean toReturn = connected;
		return toReturn;
	}
	
	/**
	 * 
	 * @return graph returned by WikiScraper
	 */
	public Graph getGraph() {
		Graph toReturn = g;
		return toReturn;
	}
}
