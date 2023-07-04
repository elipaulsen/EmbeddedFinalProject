import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;

public class Client extends JFrame {
  private final JTextArea displayArea;
  private final JTextField enterField;
  private DatagramSocket socket;

  public Client() {
    super("CLIENT");
    displayArea = new JTextArea(); // create displayArea
    displayArea.setFont(new Font("Dialog", Font.PLAIN, 14));
    displayArea.setEditable(false);

    enterField = new JTextField();
    enterField.setEditable(true);
    enterField.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              String command = e.getActionCommand();
              displayArea.append("\n client >>> " + command);
              enterField.setText("");



              byte[] data = command.getBytes();

              DatagramPacket sendPacket = new DatagramPacket(
                  data, data.length, InetAddress.getLocalHost(), 23762);

              socket.send(sendPacket);
              if (command.strip().toLowerCase().equals("stop")) {
                System.exit(0);
              }
            } catch (IOException ioE) {
              ioE.printStackTrace();
            }
          }
        }
    );

    add(enterField, BorderLayout.NORTH);

    add(new JScrollPane(displayArea), BorderLayout.CENTER);
    setSize(600, 750); // set size of window

    displayArea.append("\n\n to remove an element: enter index of element" +
        "\n example: [1,2,3,4] --> client >>> 2 --> [1,2,4]\n" +
        "\n to insert an element into an index enter value followed by index seperated by space" +
        "\n example: [1,2,3,4] --> client >>> 7 1 --> [1,7,2,3,4]\n" +
        "\n to view list enter 'show'" +
        "\n to end program and close server, enter 'stop'\n");

    setVisible(true); // show window

    try {
      socket = new DatagramSocket();
    } catch (SocketException sE) {
      sE.printStackTrace();
      System.exit(1);
    }
  }

  public void waitForPackets() {
    while (true) {
      try {
        byte[] data = new byte[100];
        DatagramPacket packetReceived = new DatagramPacket(data, data.length);
        socket.receive(packetReceived);
        displayArea.append("\n server >>> " + new String(packetReceived.getData()));
      } catch (IOException ioE) {
        ioE.printStackTrace();
      }
    }
  }

}