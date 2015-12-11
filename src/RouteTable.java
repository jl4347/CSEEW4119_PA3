import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
		System.out.println("<" + getCurrentTime() + "> Distance vector list is:");
		for (RouteInfo route : routeTable) {
			System.out.println(route.toString());
		}
	}
	
	private String getCurrentTime() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}
