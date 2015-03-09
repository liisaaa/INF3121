class Operations {
    public int maxtime = 0;
    public Task[] taskList;
    public int tasksInList = 0;

    void readFile(String innfil) {
        File inn = new File(innfil);
        Task temp;
        Task t;
        try {
            Scanner s = new Scanner(inn);
            taskList = new Task[s.nextInt()]; //new task array
            while (s.hasNext()) {
                int id = s.nextInt();
                temp = getTask(id);
                if (temp == null) {
                    t = new Task(id);
                    taskList[tasksInList] = t;
                    tasksInList++;
                } else {
                    t = temp;
                }
                t.addTaskinfo(s.next(), s.nextInt(), s.nextInt());
                addEdges(t, s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Feil ved lesing av fil: " + e.getMessage() + "\n");
        }
        boolean syklus = syklus(taskList[0]);
        if(syklus == false){
        topsort();
        printTime();
        System.out.println("\n\n");
        findLatestTime();
        printCriticalTask();
        } else{
            System.out.println("Program terminated");
        }
    }

    void findLatestTime() {
        for(Task t: this.taskList){
            int timetotal = t.earliestStart+t.time;
            int lateTime = -1;
            Stack.Node temp = t.outEdges.getfirst();
            while(temp!=null){
                if(lateTime == -1){
                    lateTime = temp.t.earliestStart;
                } else if(lateTime>temp.t.earliestStart){
                    lateTime = temp.t.earliestStart;
                }
                temp = temp.next;
            }
            if(timetotal<lateTime){
                t.slack = true;
                t.latestStart = lateTime-t.time;
            } else{
                t.latestStart = t.earliestStart;
            }
        }
        
        
    }

    void printCriticalTask() {
        for (int i = 0; i < this.tasksInList; i++) {
            for (Task t : this.taskList) {
                if (t.id == i+1) {
                    System.out.println("ID: " + t.id);
                    System.out.println("Name: " + t.name);
                    System.out.println("Time: " + t.time);
                    System.out.println("Manpower: " + t.staff);
                    System.out.println("Slack: " + t.slack);
                    System.out.println("Latest starting time: " + t.latestStart);
                    Stack.Node Nabo = t.outEdges.getfirst();
                    System.out.print("Out edges: ");
                    while (Nabo != null) {
                        System.out.print(Nabo.t.id+" ");
                        Nabo = Nabo.next;
                    }
                    System.out.println("\n");
                }
            }
        }

    }

    void printTime() {

        int currentStaff = 0;
        int shortestTime = 0;
        boolean something = false; //something started or finished, print time
        for (int time = 0; time < maxtime; time++) { 
            for (Task t : this.taskList) {
                if (t.earliestStart == time) {
                    if (something == false) {
                        System.out.println("Time: " + time);
                        something = true;
                    }
                    currentStaff += t.staff;
                    System.out.println("   Starting task: " + t.id);
                } else if (t.earliestStart + t.time == time) {
                    if (something == false) {
                        System.out.println("Time: " + time);
                        something = true;
                        shortestTime = time;
                    }
                    currentStaff -= t.staff;
                    System.out.println("   Task ended: " + t.id);
                }

            }
            if (something == true) {
                System.out.println("   Current staff: " + currentStaff);
            }
            something = false;
        }
        System.out.println("Shortest possible project execution is " + shortestTime);
    }

    void addEdges(Task task, Scanner s) {
        //Vi får inn inngående tasks, dvs vi må inn i dem og linke tilbake til objektet vi leser fra. Da setter vi utgående edges.
        int id;
        id = s.nextInt();
        int count = 0;
        while (id != 0) {
            Task get = getTask(id);
            if (get == null) { //finnes ikke i listen, en ny node
                Task t = new Task(id);
                t.outEdges.push(task);
                taskList[tasksInList] = t;
                tasksInList++;
            } else {
                get.outEdges.push(task);
            }
            count++;
            
            id = s.nextInt();
        }
        task.inngrad = task.inngrad + count;
    }

    boolean taskListContains(int id) {
        for (int i = 0; i < tasksInList; i++) {
            if (taskList[i].id == id) {
                return true;
            }
        }
        return false;
    }

    Task getTask(int id) {
        for (int i = 0; i < tasksInList; i++) {
            if (taskList[i].id == id) {
                return taskList[i];
            }
        }
        return null;
    }

    void topsort() {
        Stack q = new Stack();
        
        //Step one. Finn alle med inngrad 0. 
        for (Task t : taskList) {
            if (t.inngrad == 0) {
                q.push(t);
                t.earliestStart = 0;
                t.latestStart = 0;
            }
        }
        
        while (q.empty()) {
            Task t = q.pop();
            int nyTid = t.earliestStart + t.time;
            Stack.Node nabo = t.outEdges.getfirst();
            while (nabo != null) {
                //Sjekk for flere innkanter for å redusere latest start og mulig øke latest start. 
                if (nabo.t.earliestStart < nyTid) {
                    nabo.t.earliestStart = nyTid;
                    nabo.t.latestStart = nyTid; //Midlertidig 
                }
                nabo.t.inngrad--;
                if (nabo.t.inngrad == 0) {
                    q.push(nabo.t);
                }
                if(maxtime<t.earliestStart+t.time) maxtime = t.earliestStart + t.time;
                nabo = nabo.next;
            }
        }
    }

    boolean syklus(Task t) {
        if(t.visited){
            t.visited=false;
            return true;
        } else{
            t.visited=true;
            Stack.Node temp = t.outEdges.getfirst();
            while(temp!=null){
                if(syklus(temp.t)){
                    System.out.println("!Syklus funnet: "+t.id);
                    return true;
                }
                temp.t.visited=false;
                temp = temp.next;
            }
        }

        return false;
    }
}