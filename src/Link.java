public class Link {
	public String routerIP;
	public int listenPort;
	public double weight;
	
	public boolean isSelf = false;
	public boolean isAlive = true;
	public boolean stopExchange = false;
	public long lastUpdate;
	
	public Link(String routerIP, int listenPort, double weight, boolean isSelf, long lastUpdate) {
		this.routerIP = routerIP;
		this.listenPort = listenPort;
		this.weight = weight;
		this.isSelf = isSelf;
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return routerIP + ":" + listenPort;
	}
}
