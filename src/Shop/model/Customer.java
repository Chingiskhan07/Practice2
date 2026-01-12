package Shop.model;

import Shop.exception.InvalidInputException;

public class Customer {
    private int customerId;
    private String name;
    private String preferredSize;
    private int points;

    public Customer(int customerId, String name, String preferredSize, int points) {
        setCustomerId(customerId);
        setName(name);
        setPreferredSize(preferredSize);
        setPoints(points);
    }

    public Customer() {
        this(0, "Guest", "M", 0);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        if (customerId < 0) {
            throw new InvalidInputException("Customer ID cannot be negative");
        }
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getPreferredSize() {
        return preferredSize;
    }

    public void setPreferredSize(String preferredSize) {
        if (preferredSize == null || preferredSize.trim().isEmpty()) {
            throw new InvalidInputException("Preferred size cannot be null or empty");
        }
        this.preferredSize = preferredSize;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if (points < 0) {
            throw new InvalidInputException("Points cannot be negative");
        }
        this.points = points;
    }

    public void addPoints(int points) {
        if (points < 0) {
            throw new InvalidInputException("Added points cannot be negative");
        }
        this.points += points;
    }

    public boolean isVIP() {
        return points > 100;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", preferredSize='" + preferredSize + '\'' +
                ", points=" + points +
                ", VIP=" + isVIP() +
                '}';
    }
}