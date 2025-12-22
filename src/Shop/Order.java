package Shop;

public class Order {

    private int orderId;
    private String customerName;
    private double total;
    private String status;

    public Order(int orderId, String customerName, double total, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.total = total;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // LOGIC METHODS
    public void complete() {
        status = "Completed";
    }

    public void cancel() {
        status = "Cancelled";
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", total=" + total +
                ", status='" + status + '\'' +
                '}';
    }
}

