package Shop;

public class Main {
    public static void main(String[] args) {

        System.out.println("_-Clothing Store ChingisKhan.A-_");

        ClothingItem item1 = new ClothingItem(1, "T-shirt", "M", 12000, "Nike");
        Customer customer1 = new Customer(101, "Ali", "M", 80);
        Order order1 = new Order(5001, "Ali", 12000, "Pending");

        System.out.println(item1);
        System.out.println(customer1);
        System.out.println(order1);

        item1.applyDiscount(10);
        customer1.addPoints(50);
        order1.complete();

        System.out.println("\nAFTER CHANGES");
        System.out.println(item1);
        System.out.println(customer1);
        System.out.println(order1);
    }
}


