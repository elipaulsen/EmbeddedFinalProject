import java.awt.*;
import java.io.IOException;
import java.net.*;
import javax.swing.*;

public class Server extends JFrame {
    private final JTextArea displayArea;
    private DatagramSocket socket;
    private final EnhancedLinkedList linkedList;

    public Server() {
        super("SERVER");
        linkedList = new EnhancedLinkedList();
        displayArea = new JTextArea(); // create displayArea
        displayArea.setFont(new Font("Dialog", Font.PLAIN, 13));
        displayArea.setEditable(false);
        displayArea.append("\n\n Enhanced Linked List Server"+
                "\n Insert and remove nodes anywhere within list" +
                "\n type commands into the client in order to modify the list");

        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setSize(400, 600); // set size of window
        setVisible(true); // show window

        try {
            socket = new DatagramSocket(23762);
        }
        catch (SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
    }

    public void waitForPackets() {
        while (true) {
            try {
                byte[] data = new byte[100];
                DatagramPacket packetReceived = new DatagramPacket(data, data.length);

                socket.receive(packetReceived);
                displayArea.append("\n\n received packet from "+packetReceived.getAddress());

                String changes = modifyList(packetReceived);
                byte[] sentData;
                DatagramPacket sentPacket;
                if (changes.equals("show")) {
                    sentData = linkedList.toString().getBytes();
                    sentPacket = new DatagramPacket(sentData,sentData.length, packetReceived.getAddress(), packetReceived.getPort());
                    socket.send(sentPacket);
                }
                else if (changes.strip().toLowerCase().equals("stop")) {
                    System.exit(0);
                }
                else {
                    sentData = changes.getBytes();
                    sentPacket = new DatagramPacket(sentData,sentData.length, packetReceived.getAddress(), packetReceived.getPort());
                    socket.send(sentPacket);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String modifyList(DatagramPacket packetReceived) {
        String command = new String(packetReceived.getData(),0, packetReceived.getLength());
        if (command.length()==1) {
            try {
                if (linkedList.remove(Integer.parseInt(command))) {
                    return "removing index "+command;
                }
            }
            catch (NumberFormatException ignored) {
            }
        }

        String[] arr = command.split(" ");
        if (arr.length == 2) {
            try {
                if (linkedList.insert(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]))) {
                    return " inserting "+arr[0]+" into index "+arr[1];
                }
            }
            catch (NumberFormatException ignored) {
            }
        }
        if (command.strip().toLowerCase().equals("show")) {
            return "show";
        }
        else if (command.strip().toLowerCase().equals("stop")) {
            return "stop";
        }
        return " improper input, list unchanged";
    }

}
