import java.util.ArrayList;

public class RouteTable {
	public ArrayList<RouteInfo> routeTable;

	public RouteTable() {
		routeTable = new ArrayList<>();
	}

	public void addRouteInfoWithoutCheck(RouteInfo routeInfo) {
		routeTable.add(routeInfo);
	}

	public void ReplaceRouteInfo(RouteInfo routeInfo) {
		for (RouteInfo route : routeTable) {
			if (route.destination.equals(routeInfo.destination))
				route.isValid = false;
		}
		routeTable.add(routeInfo);
	}

	public void showRouteTable() {
		for (RouteInfo route : routeTable) {
			if (route.isValid) {
				System.out.println(route.toString());
			}
		}
	}
}
