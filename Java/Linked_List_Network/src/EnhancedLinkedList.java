public class EnhancedLinkedList {
  private Node head;
  private Node tail;
  private int sz;

  public boolean insert(int val, int index) {
    if (index > sz || index < 0) {
      return false;
    }
    Node node = new Node(val);
    if (sz == 0) {
      head = node;
      tail = head;
    }
    else if (index == sz) {
      tail.setNext(node);
      tail = tail.getNext();
    }
    else if (index == 0) {
      node.setNext(head);
      head = node;
    }
    else{
      Node tmp1 = head;
      int i = 0;
      while (i < index-1){
        tmp1 = tmp1.getNext();
        i++;
      }
      Node tmp2 = tmp1.getNext();
      tmp1.setNext(node);
      node.setNext(tmp2);
    }
    sz++;
    return true;
  }

  public boolean remove(int index) {
    if (sz == 0 || index < 0 || index >= sz) {
      return false;
    }
    if (sz == 1) {
      head = null;
      tail = null;
    }
    else if (index == 0) {
      Node tmp = head.getNext();
      head.setNext(null);
      head = tmp;
    }
    else if (index == sz-1) {
      Node tmp = head;
      while (tmp.getNext() != tail) {
        tmp = tmp.getNext();
      }
      tail = tmp;
      tail.setNext(null);
    }
    else {
      Node tmp1 = head;
      int i = 0;
      while (i < index-1){
        tmp1 = tmp1.getNext();
        i++;
      }
      Node tmp2 = tmp1.getNext();
      tmp1.setNext(tmp2.getNext());
      tmp2.setNext(null);
    }
    sz--;
    return true;
  }

  @Override
  public String toString() {
    if (sz == 0) {
      return "[]";
    }
    Node tmp = head;
    StringBuilder ls = new StringBuilder("[");
    while (tmp != tail) {
      ls.append(tmp.getVal());
      ls.append(", ");
      tmp = tmp.getNext();
    }
    ls.append(tmp.getVal());
    ls.append(']');
    return ls.toString();
  }
}
