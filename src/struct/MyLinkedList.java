package struct;

/**
 * 自定义简易版本LinkedList
 */
public class MyLinkedList<T> {

    private int length = 0;
    private Node<T> head;
    private Node<T> tail;

    public void add(T obj) {
        Node<T> node = new Node(obj);
        node.pre = this.tail;
        if (length == 0) {
            this.head = node;
        } else {
           this.tail.next = node;
        }
        this.tail = node;
        this.length++;
    }

    public T get(int index) {
        Node<T> node = getNode(index);
        if (node == null) {
            return null;
        }
        return node.data;
    }

    public boolean contain(T obj) {
        Node<T> node = getNode(obj);
        return node != null;
    }

    public boolean remove(int index) {
        Node<T> node = getNode(index);
        if (node == null) {
            return false;
        }
        node.pre.next = node.next;
        node.next.pre = node.pre;
        length--;
        return true;
    }

    public boolean remove(T obj) {
        Node<T> node = getNode(obj);
        if (node == null) {
            return false;
        }
        node.pre.next = node.next;
        node.next.pre = node.pre;
        length--;
        return true;
    }

    private Node<T> getNode(T obj) {
        Node<T> node = head;
        while (node != null) {
            if (obj == node.data || obj.equals(node.data)) {
                break;
            }
            node = node.next;
        }
        return node;
    }

    private Node<T> getNode(int index) {
        if (index >= length) {
            return null;
        }
        Node<T> node;
        if (index < length / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = length - 1; i > index; i--) {
                node = node.pre;
            }
        }
        return node;
    }

    public int size() {
        return this.length;
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("长度").append(this.length).append("\n");
        Node<T> node = head;
        while (node != null) {
            sb.append(node.data + " ");
            node = node.next;
        }
        return sb.toString();
    }

    static class Node<E> {
        public Node<E> next;
        public Node<E> pre;
        public E data;

        public Node(E data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.add("obj" + i);
            System.out.println(list);
        }
        System.out.println(list.get(3));
        System.out.println(list.get(4));
        System.out.println(list.get(1));
        System.out.println(list.contain("obj3"));
        System.out.println(list.contain("obj11"));
        System.out.println(list.remove("obj3"));
        System.out.println(list);
        System.out.println(list.remove(2));
        System.out.println(list);
    }
}