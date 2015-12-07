public class Link {
	public String nodeIP;
	public int listenPort;
	public double weight;
	public boolean isSelf = false;
	public boolean isAlive = true;
	
	public Link(String nodeIP, int listenPort, double weight, boolean isSelf) {
		super();
		this.nodeIP = nodeIP;
		this.listenPort = listenPort;
		this.weight = weight;
		this.isSelf = isSelf;
	}

	@Override
	public String toString() {
		return nodeIP + ":" + listenPort;
	}
}
