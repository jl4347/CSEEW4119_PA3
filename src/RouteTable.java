import java.util.ArrayList;

public class RouteTable {
	public ArrayList<RouteInfo> routeTable;

	public RouteTable() {
		routeTable = new ArrayList<>();
	}

	public void addRouteInfo(RouteInfo routeInfo) {
		routeTable.add(routeInfo);
	}

	public void replaceRouteInfo(RouteInfo routeInfo) {
		int index = 0;
		for (; index < routeTable.size(); index++) {
			if (routeInfo.destination.equals(routeTable.get(index).destination))
				break;
		}
		routeTable.remove(index);
		routeTable.add(routeInfo);
	}

	public void showRouteTable() {
		for (RouteInfo route : routeTable) {
			System.out.println(route.toString());
		}
	}
}
