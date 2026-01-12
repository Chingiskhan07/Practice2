package Shop.model;

public interface Discountable {
    void applyDiscount(double percent);
    boolean isPremium();
}