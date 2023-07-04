public class Node {
    private final int val;
    private Node next;

    public Node(final int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
