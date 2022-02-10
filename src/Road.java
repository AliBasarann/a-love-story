
public class Road implements Comparable<Road>{
	String start;
	String end;
	int weight;
	public Road(String start, String end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	public int compareTo(Road r){
		return(this.weight-r.weight);
	}
}
