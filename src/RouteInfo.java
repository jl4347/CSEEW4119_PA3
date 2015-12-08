
public class RouteInfo {
	public String destination;
	public double cost;
	public String nextHop;
	public boolean isValid;

	public RouteInfo(String destination, double cost, String nextHop, boolean isValid) {
		super();
		this.destination = destination;
		this.cost = cost;
		this.nextHop = nextHop;
		this.isValid = isValid;
	}

	@Override
	public String toString() {
		return "Destination = " + destination + ", Cost = " + cost + ", (Link = " + nextHop + ")";
	}
}
