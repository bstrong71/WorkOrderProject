import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Processor {

    public void processWorkOrders() {
        while (true) { // always running per project specs
            try { // loop sleeps for 5 seconds
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // create status map to include all statuses as the keys
            Map<Status, Set<WorkOrder>> statusMap = new HashMap<>();

            int length = workOrderSet.size();
//            for (int i = 0; i < )

            System.out.println("Status Map: ");
            for(Map.Entry<Status, Set<WorkOrder>> each: statusMap.entrySet()) {
                System.out.println(each);
            }

            moveIt();

            readIt();

        }
    }

    private void moveIt() {
        // move work orders in map from one state to another

    }

    private void readIt() {
        // read the json files into WorkOrders and put in map

    }

    public static void main(String[] args) {

        Processor newProcessor = new Processor();
        newProcessor.processWorkOrders();
    }
}
