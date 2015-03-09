class Task {
    int id;
    int time;
    int staff;
    boolean visited = false;
    boolean slack = false;
    String name;
    int earliestStart;
    int latestStart;
    Stack outEdges; //kanter ut
    int inngrad; //inngrad

    Task(int id) { 
        this.id = id;
        this.outEdges = new Stack();
    }
    boolean visited(){
        return visited;
    }
    void addTaskinfo(String name, int time, int staff) {
        this.name = name;
        this.time = time;
        this.staff = staff;
    }
}
