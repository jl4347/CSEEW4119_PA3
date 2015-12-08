import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class LinkInfo implements Serializable {
	public ConcurrentHashMap<String, Double> linkMap;
	public String host;
	
	public LinkInfo(ArrayList<Link> linkHistory, String host) {
		this.host = host;
		linkMap = new ConcurrentHashMap<>();
		for (Link link : linkHistory) {
			linkMap.put(link.toString(), link.weight);
		}
	}
}
