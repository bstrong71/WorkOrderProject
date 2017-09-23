import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Processor {

    public void processWorkOrders() {
        while (true) { // always running per project specs
            try { // loop sleeps for 5 seconds
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            moveIt();

            readIt();
        }
    }

    private void moveIt() {
        // move work orders in map from one state to another

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
        // create workOrderSet
        Set<WorkOrder> workOrderSet = new HashSet<>();

        for (File f : currentDir.listFiles()) {
            if (f.getName().endsWith(".json")) {
                String workOrderJSON = getFileContents(f.getName()).get(0);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WorkOrder wo = mapper.readValue(workOrderJSON, WorkOrder.class);

                    // add workOrder object to workOrderSet
                    workOrderSet.add(wo);
                    System.out.println("**** workOrderSet: " + workOrderSet.toString());

                    // create status map to include all statuses as the keys
//                    Map<Status, Set<WorkOrder>> statusMap = new HashMap<>();
//                    statusMap.put(WorkOrder.getStatus(), workOrderSet);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        return workOrderSet;

    }

    public static void main(String[] args) {

        Processor newProcessor = new Processor();
        newProcessor.processWorkOrders();
    }
}
