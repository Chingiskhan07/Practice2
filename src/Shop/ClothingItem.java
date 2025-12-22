package Shop;

public class ClothingItem {

    private int itemId;
    private String name;
    private String size;
    private double price;
    private String brand;

    public ClothingItem(int itemId, String name, String size, double price, String brand) {
        this.itemId = itemId;
        this.name = name;
        this.size = size;
        this.price = price;
        this.brand = brand;
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
        this.name = name;
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
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // логика
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
