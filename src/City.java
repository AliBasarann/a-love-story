import java.util.*;
public class City implements Comparable <City> {
	String name;
	String parent = "-1";
	int length = Integer.MAX_VALUE;
	String mapName = "-1";
	boolean isVisited = false;
	ArrayList<String> neighbours = new ArrayList<>();
	ArrayList<Integer> neighbourDistance = new ArrayList<>();
	Set<String> connectedCities = new HashSet<>();
	boolean inQueue = false;
	
	
	public City(String name) {
		this.name = name;
	}
	public void checkPath(City c) {
		if (this.length > c.length) {
			this.parent = c.name;
			this.length = c.length;
		}
	}
	public int compareTo(City c) {
		return this.length-c.length;
	}
	
}
