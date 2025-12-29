package Shop;

public class Customer {

    private int customerId;
    private String name;
    private String preferredSize;
    private int points;

    public Customer(int customerId, String name, String preferredSize, int points) {
        this.customerId = customerId;
        setName(name);
        this.preferredSize = preferredSize;
        setPoints(points);
    }

    public Customer() {
        this.customerId = 0;
        this.name = "Guest";
        this.preferredSize = "M";
        this.points = 0;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPreferredSize() {
        return preferredSize;
    }

    public int getPoints() {
        return points;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            this.name = "Unknown";
        }
    }

    public void setPreferredSize(String preferredSize) {
        this.preferredSize = preferredSize;
    }

    public void setPoints(int points) {
        if (points >= 0) {
            this.points = points;
        } else {
            this.points = 0;
        }
    }


    // LOGIC METHODS
    public void addPoints(int points) {
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
                '}';
    }
}
