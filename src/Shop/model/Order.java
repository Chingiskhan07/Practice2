package Shop.model;

import Shop.exception.InvalidInputException;

public class Order {
    private int orderId;
    private String customerName;
    private double total;
    private String status;

    public Order(int orderId, String customerName, double total, String status) {
        setOrderId(orderId);
        setCustomerName(customerName);
        setTotal(total);
        setStatus(status);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        if (orderId < 0) {
            throw new InvalidInputException("Order ID cannot be negative");
        }
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be null or empty");
        }
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        if (total < 0) {
            throw new InvalidInputException("Total cannot be negative");
        }
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!status.equals("Pending") && !status.equals("Completed") && !status.equals("Cancelled")) {
            throw new InvalidInputException("Invalid status. Must be Pending, Completed, or Cancelled");
        }
        this.status = status;
    }

    public void complete() {
        setStatus("Completed");
    }

    public void cancel() {
        setStatus("Cancelled");
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