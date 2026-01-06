package Shop;

public class ClothingItem {

    protected int itemId;
    protected String name;
    protected String size;
    protected double price;
    protected String brand;

    public ClothingItem(int itemId, String name, String size, double price, String brand) {
        this.itemId = itemId;
        setName(name);
        this.size = size;
        setPrice(price);
        this.brand = brand;
    }

    public void work() {
        System.out.println("Clothing item is being processed");
    }

    public ClothingItem() {
        this.itemId = 0;
        this.name = "Unknown";
        this.size = "M";
        this.price = 0.0;
        this.brand = "No brand";
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            this.name = "Unknown";
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            this.price = 0;
        }
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public void applyDiscount(double percent) {
        price = price * (1 - percent / 100);
    }

    public boolean isPremium() {
        return price > 20000;
    }

    @Override
    public String toString() {
        return "ClothingItem{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                '}';
    }
}
