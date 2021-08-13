package nets150_hw5;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NeighborhoodOverlap {
    private HashMap<String, HashSet<String>> givenGraph;
    private HashMap<String, Double> allOverlap;
    
    public NeighborhoodOverlap(Graph g) {
        this.givenGraph = g.getGraph();
    }
    
    /**
     * 
     * @return a mapping of all the vertices in the graph
     *         and its corresponding neighborhood overlap value
     */
    private HashMap<String, Double> getAllOverlapNumber() {
        allOverlap = new HashMap<String, Double>();
        double actualNeighborCount = 0;
        double allNeighborsCount = 0;
        double overlapFraction;
   
        for (Map.Entry<String, HashSet<String>> first : this.givenGraph.entrySet()) {

            HashSet<String> currentNeighbors = first.getValue();
            
            for (Map.Entry<String, HashSet<String>> entry : this.givenGraph.entrySet()) {
                if (!currentNeighbors.equals(entry.getValue())) {
                    for (String element : entry.getValue()) {
                        if (currentNeighbors.contains(element)) {
                            actualNeighborCount++;
                        }
                    }
                    allNeighborsCount = first.getValue().size() + entry.getValue().size();
                    overlapFraction = actualNeighborCount / allNeighborsCount;
                    allOverlap.put(first.getKey(), overlapFraction);
                    actualNeighborCount = 0;
                    
                }
            }
        }
    
        return allOverlap;
    }
    
    /**
     * 
     * @return a sorted ordered linked Hashmap that goes from from  largest value to smallest value
     */
    private HashMap<String, Double> sortOverlap() {
        List<Map.Entry<String, Double>> list = 
                new LinkedList<Map.Entry<String, Double>>(allOverlap.entrySet());
        
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        
        HashMap<String, Double> orderedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> element : list) {
            orderedMap.put(element.getKey(), element.getValue());
        }
        return orderedMap;
                
    }
    
    /**
     * 
     * @param threshold a threshold to compare the neighborhood overlap value 
     *        to designate weak ties
     * @return a graph that has its own components.
     */
    public HashMap<String, HashSet<String>> clustering(double threshold) {
        
        getAllOverlapNumber();
        //sortOverlap();
        for (Map.Entry<String, Double> element : this.allOverlap.entrySet()) {
            if (element.getValue() < threshold) {
                String removal = element.getKey();
                this.givenGraph.remove(removal);
            }
        }
      return this.givenGraph;  
    }
    
    /**
     * prints the graph. starts with vertices and its corresponding edges 
     */
    public void printGraph() {
        for (String vertex: this.givenGraph.keySet()) {
            System.out.println("Vertex: " + vertex);
            System.out.println("Edges: " + this.givenGraph.get(vertex).toString());
        }
    }
}
