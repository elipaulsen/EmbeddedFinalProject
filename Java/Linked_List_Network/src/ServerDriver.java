import javax.swing.*;

public class ServerDriver {
    public static void main(String[] args) {
        Server S = new Server();
        S.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        S.waitForPackets();
    }
}
