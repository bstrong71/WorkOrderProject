import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Creator {
    public void createWorkOrders() {
        // read input, create work orders and write as json files

        while (true) { //always running per project requirement
            try {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter Work Order Description: ");
                String descriptionEntry = scanner.nextLine();

                System.out.println("Enter Work Order Sender's Name: ");
                String senderNameEntry = scanner.nextLine();

                WorkOrder newWorkOrder = new WorkOrder();
                WorkOrder.numWOCreated++;
                newWorkOrder.setId(WorkOrder.numWOCreated);
                newWorkOrder.setDescription(descriptionEntry);
                newWorkOrder.setSenderName(senderNameEntry);
                newWorkOrder.setStatus(Status.INITIAL);

                File fileForJson = new File(WorkOrder.numWOCreated + ".json");
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

    public static void main(String[] args) {

        Creator creator = new Creator();
        creator.createWorkOrders();
    }
}
