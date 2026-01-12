package Shop.model;

import Shop.exception.InvalidInputException;

public abstract class ClothingItem implements Discountable {
    protected int itemId;
    protected String name;
    protected String size;
    protected double price;
    protected String brand;

    public ClothingItem(int itemId, String name, String size, double price, String brand) {
        setItemId(itemId);
        setName(name);
        setSize(size);
        setPrice(price);
        setBrand(brand);
    }
    public ClothingItem() {
        this(0, "Unknown", "M", 0.0, "No brand");
    }

    public abstract void work();

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        if (itemId < 0) {
            throw new InvalidInputException("Item ID cannot be negative");
        }
        this.itemId = itemId;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new InvalidInputException("Size cannot be null or empty");
        }
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new InvalidInputException("Price cannot be negative");
        }
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new InvalidInputException("Brand cannot be null or empty");
        }
        this.brand = brand;
    }

    @Override
    public void applyDiscount(double percent) {
        if (percent < 0 || percent > 100) {
            throw new InvalidInputException("Discount percent must be between 0 and 100");
        }
        price = price * (1 - percent / 100);
    }

    @Override
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