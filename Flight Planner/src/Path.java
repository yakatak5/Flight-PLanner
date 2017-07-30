import java.util.ArrayList;
import java.util.Stack;

public class Path {
	private Stack<FlightData> cities;
	private int cost;
	private int time;
	private boolean found = false;
	
	public Path(FlightData source, FlightData destination){
		cities = new Stack<FlightData> ();
		this.cities.push(source);
		this.cities.push(destination);
		this.cost = destination.getCost();
		this.time = destination.getTime();
	}
	public Path(Path path, FlightData destination){
		Stack<FlightData> list = path.getCities();
		this.cities = (Stack<FlightData>)list.clone();
		this.cities.push(destination);
		this.cost = path.getCost() + destination.getCost();
		this.time = path.getTime() + destination.getTime();
		
	}
	public Stack<FlightData> getCities() {
		return cities;
	}
	public void setCities(Stack<FlightData> cities) {
		this.cities = cities;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public void print(){
		ArrayList<String> cities = new ArrayList<String> ();
		Stack<FlightData> list = (Stack<FlightData>) this.cities.clone();
		while(!list.isEmpty()){
			cities.add(list.pop().getCity());
		}//fill list with cities for output
		for (int i = (cities.size()-1); i >=0; i--){
			if (i == 0)
				System.out.println(cities.get(i));
			else
				System.out.print(cities.get(i) + " -> ");
		}
		System.out.println("Cost: " + this.cost + " Time: " + this.time);
	}
	
	
	
	

}
