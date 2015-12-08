public class Link {
	public String routerIP;
	public int listenPort;
	public double weight;
	public boolean isSelf = false;
	public boolean isAlive = true;
	
	public Link(String routerIP, int listenPort, double weight, boolean isSelf) {
		super();
		this.routerIP = routerIP;
		this.listenPort = listenPort;
		this.weight = weight;
		this.isSelf = isSelf;
	}

	@Override
	public String toString() {
		return routerIP + ":" + listenPort;
	}
}
