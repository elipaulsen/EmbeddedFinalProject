# Network Managed Linked List
 allows multiple clients to modify the same linked list that is managed on a server.
#
### Developer Documentation
This program uses DatagramSocket. This allows the server and clients to wait for packets instead of requiring a complete connection when communicating like in StreamSocket. The Linked list that was implemented in this program allows for insertion and deletion anywhere inside the list. The server is the only class that has access to the linked list. 
 
![UML](../umls/linked.png)

### User Documentation
The server is compatible with multiple clients.

To start this program:

- run the ClientDriver and the ServerDriver
- enter commands into the client text box and press enter

### Commands:
- i j          inserts a node with value i at index j
- k            deletes the node at index k
- show         to view the current list
- stop         to stop the client and server