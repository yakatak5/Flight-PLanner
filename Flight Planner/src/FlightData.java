
public class FlightData {
	private String city;
	private int cost;
	private int time;
	boolean found;
	
	public FlightData(){}
	public FlightData(String c, int co, int ti){
		this.city = c;
		this.cost = co;
		this.time= ti;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public boolean isFound() {
		return found;
	}
	public void setFound(boolean found) {
		this.found = found;
	}

}
