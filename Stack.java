
class Stack { //LIFO

    private Node first, last;
    private int ant = 0;
    class Node {

        Node next;
        Task t;

        Node(Task t) {
            this.t = t;
        }
    }
    int antall(){
        return this.ant;
    }
    Node getfirst() { //for å kunne gå gjennom listen uten å fjerne elementer.
        return first;
    }

    void push(Task t) { //setter inn sist
        Node ny = new Node(t);
        if (first == null) {
            first = ny;
            last = ny;
        } else {
            last.next = ny;
            last = ny;
        }
        ant ++;
    }

    Task pop() { //fjerner siste element
        Node temp = first;
        if (temp == null) {
            return null;
        } else if (temp.next == null) {
            first = null;
            last = null;
        } else {
            while (temp.next != last) {
                temp = temp.next;
            }
            last = temp; //nest siste ord
            temp = last.next; //siste ord
            last.next = null; //nest siste ord blir siste

        }
        ant--;
        return temp.t;
    }

    Task top() { //ser på siste element i listen
        return last.t;
    }

    boolean empty() {
        return null != first;
    }
}

