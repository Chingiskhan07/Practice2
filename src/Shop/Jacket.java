package Shop;

public class Jacket extends ClothingItem {

    private boolean hood;

    public Jacket(int itemId, String name, String size, double price, String brand, boolean hood) {
        super(itemId, name, size, price, brand);
        this.hood = hood;
    }

    @Override
    public void work() {
        System.out.println("Jacket " + name + " is protecting from cold");
    }

    @Override
    public String toString() {
        return "Jacket{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", hood=" + hood +
                '}';
    }

    public boolean hasHood() {
        return hood;
    }

    public void zipUp() {
        System.out.println(name + " is zipped up.");
    }
}
