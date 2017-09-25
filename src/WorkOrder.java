public class WorkOrder {
    private int id;
    private String description;
    private String senderName;
    private Status status;
    private static int numWOCreated = 0;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkOrder workOrder = (WorkOrder) o;

        return id == workOrder.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public WorkOrder() {
        this.id = getAndIncrNumWOCreated();

    }

    public WorkOrder(String description, String senderName, Status status) {
        this.id = getAndIncrNumWOCreated();
        this.description = description;
        this.senderName = senderName;
        this.status = status;
    }

    private static int getAndIncrNumWOCreated() {
        return numWOCreated ++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
