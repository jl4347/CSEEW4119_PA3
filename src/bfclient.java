import java.net.*;
import java.util.*;


public class bfclient {
	public static int listenPort;
	public static int timeout;
	public static String localhost;
	
	// Link history to recover links later
	public static ArrayList<Link> linkHistory = new ArrayList<Link>();
	public static LinkInfo linkInfo;
	public static Router routerDV;
	
	public final static String LINKDOWN = "LINKDOWN";
	public final static String LINKUP = "LINKUP";
	public final static String SHOWRT = "SHOWRT";
	public final static String CLOSE = "CLOSE";
	
	public bfclient() {
		
	}
	
	public static void main(String[] args) {
		if (args.length < 5 || (args.length - 2) % 3 != 0)
			printInstruction();
		else {
			try {
				setUp(args);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		linkInfo = new LinkInfo(linkHistory, localhost);
		
		// Initialize the router and the local distance vector
		routerDV = new Router(localhost, linkHistory);
		routerDV.createDistanceVector(linkInfo);
		
		// Start Listening Thread
		
		
		// Start broadcasting Thread
		
		
		// Start UI
		bfclient bfc = new bfclient();
		bfc.startCLI();
	}
	
	private static void setUp(String[] args) throws UnknownHostException{
		listenPort = Integer.parseInt(args[0]);
		timeout = Integer.parseInt(args[1]);
		
		// Store original links for recover and send link update to neighbors
		//linkHistory.add(new Link(InetAddress.getLocalHost().getHostAddress(), listenPort, 0.0, true));
		for (int router = 0; router < (args.length - 2) / 3; router++) {
			linkHistory.add(new Link(InetAddress.getByName(args[(router * 3) + 2]).getHostAddress(), 
					Integer.parseInt(args[(router * 3) + 3]), Double.parseDouble(args[(router * 3) + 4])));
		}
		
		localhost = InetAddress.getLocalHost().getHostAddress() + ":" + listenPort;
	}
	
	private void startCLI() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to bfclient!");
		
		while (true)
		{
			String input = scanner.nextLine().trim();
			String[] command = input.split(" ");
			if (command.length == 0 || command[0].length() == 0) {
				continue;
			}
			String cmd = command[0].toUpperCase();

			switch (cmd)
			{
				case LINKDOWN:
					linkDown(command);
					break;
				case LINKUP:
					linkUp(command);
					break;
				case SHOWRT:
					showRT(command);
					break;
				case CLOSE:
					close();
					break;
				default:
					System.out.println("Unknown command: " + input);
					break;
			}
		}
	}
	
	private void linkDown(String[] command) {
		// TODO Auto-generated method stub
		
	}
	
	private void linkUp(String[] command) {
		// TODO Auto-generated method stub
		
	}
	
	private void showRT(String[] command) {
		routerDV.routeTable.showRouteTable();
	}
	
	private void close() {
		System.exit(0);
	}

	private static void printInstruction() {
		System.out.println("java ./class/bfclient localport timeout [ipaddress1 port1 weight1 ...]");
	}
}
