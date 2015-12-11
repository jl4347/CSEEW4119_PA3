import java.io.*;
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
		System.out.println("Initiate LINKDOWN");
		if (command.length != 3) {
			System.out.println("Illege Command.");
			System.out.println("Usage: LINKDOWN <ip> <port>");
			return;
		}

		linkInfo.linkMap.replace(command[1] + ":" + command[2], Double.POSITIVE_INFINITY);
		linkInfo.brokenLink.put(command[1] + ":" + command[2], true);
		routerDV.linkDown.put(command[1] + ":" + command[2], true);
		for (Link link : linkHistory) {
			if (link.toString().equals(command[1] + ":" + command[2])) {
				link.isAlive = false;
				break;
			}
		}
		
	}
	
	private void linkUp(String[] command) {
		System.out.println("Initiate LINKUP");
		if (command.length != 3) {
			System.out.println("Illege Command.");
			System.out.println("Usage: LINKUP <ip> <port>");
			return;
		}
		
		for (Link link : linkHistory) {
			if (link.toString().equals(command[1] + ":" + command[2])) {
				link.isAlive = true;
				linkInfo.linkMap.replace(command[1] + ":" + command[2], link.weight);
				linkInfo.brokenLink.remove(command[1] + ":" + command[2]);
				routerDV.linkDown.put(command[1] + ":" + command[2], false);
				break;
			}
		}
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


// Thread for listening to ROUTE UPDATE
class ListenThread extends Thread {
	DatagramSocket listenSocket;
	DatagramPacket inputDatagram;
	byte[] inputDataByte = new byte[60 * 1024];
	ObjectInputStream ois;
	int listenPort;
	ArrayList<Link> links;
	LinkInfo linkInfo;
	Router routerDV;

	public ListenThread(int listenPort, LinkInfo linkInfo, ArrayList<Link> links, Router routerDV) {
		this.listenPort = listenPort;
		this.linkInfo = linkInfo;
		this.links = links;
		this.routerDV = routerDV;
	}

	@Override
	public void run() {
		try {
			listenSocket = new DatagramSocket(listenPort);

			while (true) {
				inputDatagram = new DatagramPacket(inputDataByte, inputDataByte.length);
				listenSocket.receive(inputDatagram);
				RUUDPpacket rudp = new RUUDPpacket();
				rudp.extractPacket(inputDataByte);

				if (!rudp.verifyChecksum()) {
					System.out.println("Packet is corrupted.");
				} else {
					ois = new ObjectInputStream(new ByteArrayInputStream(rudp.getPayload()));
					LinkInfo incomingLinks = (LinkInfo) ois.readObject();
					routerDV.updateDistanceVector(linkInfo, incomingLinks);
				}
			}
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}