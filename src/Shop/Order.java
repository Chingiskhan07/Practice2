package Shop;

public class Order {

    private int orderId;
    private String customerName;
    private double total;
    private String status;

    public Order(int orderId, String customerName, double total, String status) {
        this.orderId = orderId;
        setCustomerName(customerName);
        setTotal(total);
        setStatus(status);
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
        if (customerName != null && !customerName.trim().isEmpty()) {
            this.customerName = customerName;
        } else {
            this.customerName = "Unknown";
        }
    }

    public void setTotal(double total) {
        if (total >= 0) {
            this.total = total;
        } else {
            this.total = 0;
        }
    }

    public void setStatus(String status) {
        if (status.equals("Pending") || status.equals("Completed")) {
            this.status = status;
        } else {
            this.status = "Pending";
        }
    }

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

