public class Link {
	public String routerIP;
	public int listenPort;
	public double weight;
	public boolean isAlive = true;
	public long lastUpdate;
	
	public Link(String routerIP, int listenPort, double weight, long lastUpdate) {
		this.routerIP = routerIP;
		this.listenPort = listenPort;
		this.weight = weight;
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return routerIP + ":" + listenPort;
	}
}
