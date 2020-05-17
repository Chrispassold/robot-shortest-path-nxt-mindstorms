
public class Edge {

	public Node start;
	public Node end;
	public Integer cost;
	
	public Edge(Node start, Node end, Integer cost) {
		this.start = start;
		this.end = end;
		this.cost = cost;
	}
	
	public String toString() {
		return "Edges(start="+start+", end="+end+", cost="+cost+")";
	}
}
