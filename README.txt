Programming Assignment 3: Distributed Bellman-Ford

UNI: jl4347
Name: Jialun Liu

=======================================================================================
How to Use
=======================================================================================
In order to build the application first, run the following command:
$ make all

Then run bfclient:
java bfclient localport timeout [ipaddress1 port1 weight1 ...]

=======================================================================================
Program Structure
=======================================================================================
1. bfclient.java:
The main program that executes the commands LINDOWN/UP etc. It has a listening thread to
to receive updates from the adjecent nodes, and a broadcast thread to constantly broadcast
its link cost information to all its direct neighbors.

2. Router.java:
It stores all the information about the node, e.g. distance vector, routing table. Whenever
the node receives an uncorrupted datagram from its neighbors, it uses the updateDistanceVector()
to update its link costs.

3. RouteTable.java:
The model for Route Table, it's simply an array list of Route Info.

4. RouteInfo.java:
The model for Route Info, each instance is a record in the Route Table, it contains information
of the destination, the cost to destination and next-hop node.

5. Link.java:
This is the model for each link of one node's direct neighbor, it contains the weight of each
link when the program is first set up, convient for later link restore.

6. LinkInfo.java:
The is the payload of the datagram being sent to node's direct neighbors, so that the neighbors
could update their route table if there's any update.

7. RUUDPpacket.java:
This is the customized transport protocol, it's used to generate the packet, extract info from
the received packets and verify the checksum to make sure the data is not corrupted.

=======================================================================================
UDP datagram structure
=======================================================================================
The datagram header includes the following information:
1. source port
2. destination port
3. payload length
4. Checksum
5. Actual Data

=======================================================================================
Commands
=======================================================================================
1. SHOWRT
2. LINKDOWN ip_address port_number
3. LINKUP ip_address port_number (little change in the command format)
4. CLOSE

=======================================================================================
Known bugs
=======================================================================================	
1. Bug with LINKDOWN command, some indirectly connected neighbors didn't get a correct
update in its route table.

2. Haven't been able to implement the timer.
