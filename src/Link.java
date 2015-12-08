public class Link {
	public String routerIP;
	public int listenPort;
	public double weight;
	public boolean isAlive = true;
	
	public Link(String routerIP, int listenPort, double weight) {
		super();
		this.routerIP = routerIP;
		this.listenPort = listenPort;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return routerIP + ":" + listenPort;
	}
}
