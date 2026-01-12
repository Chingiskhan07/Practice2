package Shop.model;

public class Shirt extends ClothingItem {
    private boolean longSleeve;

    public Shirt(int itemId, String name, String size, double price, String brand, boolean longSleeve) {
        super(itemId, name, size, price, brand);
        this.longSleeve = longSleeve;
    }

    @Override
    public void work() {
        System.out.println("Shirt " + name + " is being sold");
    }

    public boolean isLongSleeve() {
        return longSleeve;
    }

    public void setLongSleeve(boolean longSleeve) {
        this.longSleeve = longSleeve;
    }

    public boolean isWinterShirt() {
        return longSleeve;
    }

    public void fold() {
        System.out.println(name + " is folded neatly.");
    }

    @Override
    public String toString() {
        return "Shirt{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", longSleeve=" + longSleeve +
                '}';
    }
}