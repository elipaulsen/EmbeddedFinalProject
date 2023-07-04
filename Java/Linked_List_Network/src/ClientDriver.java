import javax.swing.*;


public class ClientDriver {
    private static int clientNumber = 0;
    public static void main(String[] args) {
        Client C = new Client();
        C.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        C.waitForPackets();
    }

}
