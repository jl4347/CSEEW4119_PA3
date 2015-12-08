import java.util.ArrayList;

public class RouteTable {
	public ArrayList<RouteInfo> routeTable;

	public RouteTable() {
		routeTable = new ArrayList<>();
	}

	public void addRouteInfoWithoutCheck(RouteInfo routeInfo) {
		routeTable.add(routeInfo);
	}


	public void showRouteTable() {
		for (RouteInfo route : routeTable) {
			System.out.println(route.toString());
		}
	}
}
