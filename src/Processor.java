import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Processor {

    Map<Status, Set<WorkOrder>> workOrders = new HashMap<>();

    // need the empty contructor in order to instantiate the object later
    public Processor() {
        for (Status status: Status.getAllStatus()) {
            workOrders.put(status, new HashSet<WorkOrder>());
        }
    }

    public static void main(String[] args) {
        Processor newProcessor = new Processor();
        newProcessor.processWorkOrders();
    }

    public void processWorkOrders() {
        while (true) { // always running per project specs
            readIt();
            moveIt();
            try { // loop sleeps for 5 seconds
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> getFileContents (String fileName) {
        File file = new File (fileName);
        try {
            Scanner fileScanner = new Scanner(file);
            List<String> fileContents = new ArrayList<>();
            while (fileScanner.hasNext()) {
                fileContents.add(fileScanner.nextLine());
            }
            return fileContents;
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file *" + fileName + "*");
            ex.printStackTrace();
            return null;
        }
    }

    private void readIt() {
        // read the json files into WorkOrders and put in map

        File currentDir = new File(".");


        for (File f : currentDir.listFiles()) {
            if (f.getName().endsWith(".json")) {
                String workOrderJSON = getFileContents(f.getName()).get(0);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WorkOrder wo = mapper.readValue(workOrderJSON, WorkOrder.class);

                    Set<WorkOrder> appropriateSet = workOrders.get(wo.getStatus());
                    appropriateSet.add(wo);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void moveIt() {
        // move work orders in map from one state to another
        Set<WorkOrder> inProgressOrders = workOrders.get(Status.IN_PROGRESS);
        System.out.println("Looking for Work Orders In Progress... ");
        if (inProgressOrders.size() > 0) {
            WorkOrder firstInProgress = inProgressOrders.iterator().next();
            inProgressOrders.remove(firstInProgress);
            firstInProgress.setStatus(Status.DONE);
            workOrders.get(Status.DONE).add(firstInProgress);
            System.out.println("****** Moved " + firstInProgress + " to Done.******");
            updateWorkOrder(firstInProgress);

        }

        Set<WorkOrder> assignedOrders = workOrders.get(Status.ASSIGNED);
        System.out.println("Looking for Assigned Work Orders... ");
        if (assignedOrders.size() > 0) {
            WorkOrder firstAssigned = assignedOrders.iterator().next();
            assignedOrders.remove(firstAssigned);
            firstAssigned.setStatus(Status.IN_PROGRESS);
            workOrders.get(Status.IN_PROGRESS).add(firstAssigned);
            System.out.println("****** Moved " + firstAssigned + " to In Progress.******");
            updateWorkOrder(firstAssigned);
        }

        Set<WorkOrder> initialOrders = workOrders.get(Status.INITIAL);
        System.out.println("Looking for new Work Orders... ");
        if (initialOrders.size() > 0) {
            WorkOrder firstInitial = initialOrders.iterator().next();
            initialOrders.remove(firstInitial);
            firstInitial.setStatus(Status.ASSIGNED);
            workOrders.get(Status.ASSIGNED).add(firstInitial);
            System.out.println("****** Moved " + firstInitial + " to Assigned.******");
            updateWorkOrder(firstInitial);
        }

    }

    private void updateWorkOrder(WorkOrder newWorkOrder) {
        try{
            File fileForJson = new File(newWorkOrder.getId() + ".json");
            FileWriter fileWriter = new FileWriter(fileForJson);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(newWorkOrder);

            fileWriter.write(json);
            fileWriter.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
