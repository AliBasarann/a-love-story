import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.*;

public class project3main {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		int timeLimit = Integer.parseInt(in.nextLine());
		int numOfCities = Integer.parseInt(in.nextLine());
		String line1 = in.nextLine();
		String[] splittedLine1 = line1.split(" ");
		String mecnunCity = splittedLine1[0];
		String leylaCity = splittedLine1[1];
		PriorityQueue<City> cityList = new PriorityQueue<>();
		HashMap<String, City> cityMap = new HashMap<>();
		PriorityQueue<Road> roadQ = new PriorityQueue<>();
		ArrayList<String[]> lineList = new ArrayList<>();
		int roadCityNum = 0;
		
		for(int i = 0; i < numOfCities; i++) {
			String line = in.nextLine();
			String[] splittedLine = line.split(" ");
			String name = splittedLine[0];
			City city = new City(name);
			lineList.add(splittedLine);
			
			
			for(int k = 1; k<splittedLine.length;k+=2) {
				city.neighbours.add(splittedLine[k]);
				city.neighbourDistance.add(Integer.parseInt(splittedLine[k+1]));
			}
			cityMap.put(name, city);
			
		}
		
		for(String [] splittedLine : lineList) {
			if(splittedLine[0].startsWith("d")) {
				City city = cityMap.get(splittedLine[0]);
				for(int k = 1; k<splittedLine.length;k+=2) {
					City neighbour = cityMap.get(splittedLine[k]);
					neighbour.neighbours.add(city.name);
					neighbour.neighbourDistance.add(Integer.parseInt(splittedLine[k+1]));
				}
				roadCityNum += 1;
			}
		}
		
		roadCityNum += 1;
		cityList.add(cityMap.get(leylaCity));
		cityMap.get(leylaCity).inQueue = true;
		String currentCityName = mecnunCity;
		cityMap.get(currentCityName).length=0;
		cityList.add(cityMap.get(currentCityName));
		cityMap.get(currentCityName).inQueue = true;
		String check ="";
		
		while(!currentCityName.equals(leylaCity) && !cityList.isEmpty()) {
			City currentCity = cityList.poll();
			currentCity.inQueue = false;
			currentCity.isVisited = true;
			
			if (!currentCity.neighbours.isEmpty()) {
				for(int i = 0; i < currentCity.neighbours.size(); i++) {
					String s = currentCity.neighbours.get(i);
					City city = cityMap.get(s);
					if(city.length > currentCity.length+currentCity.neighbourDistance.get(i) && !city.isVisited && !city.name.startsWith("d")) {
						
						city.length = currentCity.length+currentCity.neighbourDistance.get(i);
						
						city.parent = currentCity.name;
						
						if(!city.isVisited) {
							if(!city.inQueue) {
								cityList.add(city);
								city.inQueue = true;
							}else {
								cityList.remove(city);
								cityList.add(city);
							}
						}
						
						
					}
				}
			}
				
		}
		String cityName = leylaCity;
		Stack<String> result1 = new Stack<>();
		
			while(!cityMap.get(cityName).parent.equals("-1")) {
				City c = cityMap.get(cityName);
				result1.add(cityName);
				cityName = c.parent;
			}
			if(cityName.equals(mecnunCity)) {
				result1.add(mecnunCity);
				check = result1.pop();
				out.print(check +" ");
				while(result1.size() - 1 > 0) {
					out.print(result1.pop() + " ");
				}
				out.print(result1.pop());
				out.println();
				
			}else {
				out.println(-1);
			}
			
			
		
		if (!check.equals(mecnunCity) || cityMap.get(leylaCity).length > timeLimit)  {
			out.print(-1);
		}else {
			int totalLength = 0;
			int roadNum = 0;
			
			
			City currentCity = cityMap.get(leylaCity);
			for(int i = 0; i < currentCity.neighbours.size(); i++) {
				String s = currentCity.neighbours.get(i);
				if(s.startsWith("d")) {
					Road r = new Road(currentCity.name,s,currentCity.neighbourDistance.get(i));
					roadQ.add(r);
				}
			}
			
			
			while(!roadQ.isEmpty()) {
				Road r = roadQ.poll();
				currentCity = cityMap.get(r.end);
				if(!cityMap.get(r.start).isVisited || !cityMap.get(r.end).isVisited) {
					for(int i = 0; i < currentCity.neighbours.size(); i++) {
						String s = currentCity.neighbours.get(i);
						if(s.startsWith("d")) {
							Road road = new Road(currentCity.name,s,currentCity.neighbourDistance.get(i));
							roadQ.add(road);
						}
					}
					roadNum += 1;
					totalLength += r.weight;
				}
				currentCity.isVisited = true;
			}
			
			
			if(roadCityNum-1 == roadNum) {
				out.print(totalLength*2);
			}else {
				out.print(-2);
			}
		}
	}
}
