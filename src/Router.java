import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class Router {
	public HashMap<String, Boolean> linkDown;
	public String host;
	public ArrayList<Link> links;

	public ArrayList<String> routersList;
	public ConcurrentHashMap<String, LinkInfo> linkInfoTable;
	public ConcurrentHashMap<String, Double> distanceVector;

	public RouteTable routeTable;

	public Router(String host, ArrayList<Link> linkHistory) {
		this.host = host;
		links = linkHistory;

		linkDown = new HashMap<>();
		for (Link link : linkHistory) 
			linkDown.put(link.toString(), false);
		
		routersList = new ArrayList<>();
		linkInfoTable = new ConcurrentHashMap<>();
		distanceVector = new ConcurrentHashMap<>();

		routeTable = new RouteTable();
	}

	public void createDistanceVector(LinkInfo localLinks) {
		for (ConcurrentHashMap.Entry<String, Double> link : localLinks.linkMap.entrySet()) {
			routersList.add(link.getKey());
			linkInfoTable.put(host, localLinks);
			distanceVector.put(link.getKey(), link.getValue());

			routeTable.addRouteInfo(new RouteInfo(link.getKey(), link.getValue(), link.getKey()));
		}
	}

	public void updateDistanceVector(LinkInfo localLinks, LinkInfo incomingLinks) {
		//		System.out.println("ListenThread: Process update from: " + incomingLinks.host);

		linkInfoTable.put(incomingLinks.host, incomingLinks);

		// Check broken links
		if (incomingLinks.linkMap.get(localLinks.host) == Double.POSITIVE_INFINITY
				&& localLinks.linkMap.get(incomingLinks.host) != Double.POSITIVE_INFINITY) {
			for (Link link : links) {
				if (link.toString().equals(incomingLinks.host))
					link.isAlive = false;
			}
			localLinks.brokenLink.put(incomingLinks.host, true);
			linkInfoTable.get(host).linkMap.put(incomingLinks.host, Double.POSITIVE_INFINITY);
		}

		// Linkage restore
		if (!incomingLinks.brokenLink.containsKey(host)
				&& localLinks.brokenLink.containsKey(incomingLinks.host)
				&& !linkDown.get(incomingLinks.host)) {
			localLinks.brokenLink.remove(incomingLinks.host);
			linkInfoTable.get(host).linkMap.put(incomingLinks.host, incomingLinks.linkMap.get(host));
			System.out.println("restore");
		}

		// Learning new routers
		for (ConcurrentHashMap.Entry<String, Double> link : incomingLinks.linkMap.entrySet()) {
			if (!localLinks.linkMap.containsKey(link.getKey())
					&& !localLinks.indirectNeighbor.containsKey(link.getKey())) {
				localLinks.indirectNeighbor.put(link.getKey(), true);
				routersList.add(link.getKey());
				// New routers are initialized to infinity first for computation later
				distanceVector.put(link.getKey(), Double.POSITIVE_INFINITY);
			}
		}

		// Calculate distance vector 
		for (String dst : routersList) {
			double currentCost = Double.POSITIVE_INFINITY;
			String next = "";

			// If direct link exists, use direct link first. 
			if (linkInfoTable.get(host).linkMap.containsKey(dst) 
					&& !localLinks.indirectNeighbor.containsKey(dst)) {
				double directCost = linkInfoTable.get(host).linkMap.get(dst);
				if (directCost < currentCost) {
					currentCost = directCost;
					next = dst;
				}
			}

			// Try to find shorter path. 
			for (ConcurrentHashMap.Entry<String, LinkInfo> path : linkInfoTable.entrySet()) {
				// Skip if the path doesn't lead to specific destination
				if (!path.getValue().linkMap.containsKey(dst))
					continue;

				if (localLinks.brokenLink.containsKey(dst) && linkInfoTable.get(dst).indirectNeighbor.containsKey(path.getKey()))
					continue;

				if (path.getKey().equals(host))
					continue;

				double viaCost = linkInfoTable.get(host).linkMap.get(path.getKey()) + path.getValue().linkMap.get(dst);
				//System.out.println("via = " + via.getKey() + ", " + viaCost);
				if (viaCost < currentCost) {
					currentCost = viaCost;
					next = path.getKey();
				}
			}

			if (localLinks.indirectNeighbor.containsKey(dst)) {
				localLinks.linkMap.put(dst, currentCost);
			}

			if (localLinks.brokenLink.containsKey(dst)) {
				localLinks.linkMap.put(dst, currentCost);
			}

			if (distanceVector.get(dst) != currentCost) {
				distanceVector.put(dst, currentCost);
				routeTable.replaceRouteInfo(new RouteInfo(dst, currentCost, next));
				System.out.println("ListenThread: Update " + dst + ":" + currentCost + ", " + next);
			}
		}
	}
}
