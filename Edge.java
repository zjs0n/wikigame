package nets150_hw5;

public class Edge {
	private double betweenness;
	private String start;
	private String end;
	
	public Edge(String start, String end) {
		this.start = start;
		this.end = end;
		this.betweenness = 0;
	}
	
	public Edge(String start, String end, double betweenness) {
		this.start = start;
		this.end = end;
		this.betweenness = betweenness;
	}
	
	public String getStart() {
		return start;
	}
	
	public String getEnd() {
		return end;
	}
	
	public double getBetweenness() {
		double x = betweenness;
		return x;
	}
	
	public void setBetweenness(double x) {
		betweenness = x;
	}
	
	public void addBetweenness(double x) {
		betweenness += x;
	}
	
	@Override
	public boolean equals(Object o) {
		super.equals(o);
		boolean equal = false;
		if (o instanceof Edge && o != null) {
			Edge e = (Edge) o;
			equal = e.start.equals(this.start) && e.end.equals(this.end) && e.betweenness==this.betweenness;
		}
		return equal;
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((start == null) ? 0 : start.hashCode());
	    result = prime * result + ((end == null) ? 0 : end.hashCode());
	    return result;
	}
}
