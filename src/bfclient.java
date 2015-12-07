import java.net.*;
import java.util.*;


public class bfclient {
	public static int listenPort;
	public static int timeout;
	
	// Link history to recover links later
	public static ArrayList<Link> linkHistory = new ArrayList<Link>();
	
	public final static String LINKDOWN = "LINKDOWN";
	public final static String LINKUP = "LINKUP";
	public final static String SHOWRT = "SHOWRT";
	public final static String CLOSE = "CLOSE";
	
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
	}
	
	private static void setUp(String[] args) throws UnknownHostException{
		listenPort = Integer.parseInt(args[0]);
		timeout = Integer.parseInt(args[1]);
		
		linkHistory.add(new Link(InetAddress.getLocalHost().getHostAddress(), listenPort, 0.0, true));
		for (int node = 0; node < (args.length - 2) / 3; node++) {
			linkHistory.add(new Link(InetAddress.getByName(args[(node * 3) + 2]).getHostAddress(), 
					Integer.parseInt(args[(node * 3) + 3]), Double.parseDouble(args[(node * 3) + 4]), false));
		}
		
	}
	
	private static void printInstruction() {
		System.out.println("java bfclient localport timeout [ipaddress1 port1 weight1 ...]");
	}
}
