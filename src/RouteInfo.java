public class RouteInfo {
	public String destination;
	public double cost;
	public String nextHop;

	public RouteInfo(String destination, double cost, String nextHop) {
		super();
		this.destination = destination;
		this.cost = cost;
		this.nextHop = nextHop;
	}

	@Override
	public String toString() {
		return "Destination = " + destination + ", Cost = " + cost + ", (Link = " + nextHop + ")";
	}
}
