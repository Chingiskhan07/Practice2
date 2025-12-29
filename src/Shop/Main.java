package Shop;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<ClothingItem> items = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Order> orders = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Test data
        items.add(new ClothingItem(1, "T-Shirt", "M", 12000, "Nike"));
        customers.add(new Customer(101, "Ali", "M", 80));
        orders.add(new Order(5001, "Ali", 12000, "Pending"));

        boolean running = true;

        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    viewItems();
                    break;
                case 3:
                    addCustomer();
                    break;
                case 4:
                    viewCustomers();
                    break;
                case 5:
                    createOrder();
                    break;
                case 6:
                    viewOrders();
                    break;
                case 7:
                    completeOrder();
                    break;
                case 0:
                    running = false;
                    System.out.println("Program terminated.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }


    private static void showMenu() {
        System.out.println("\n= CLOTHING STORE MENU by Chingiskhan.A=");
        System.out.println("1. Add clothing item");
        System.out.println("2. View all items");
        System.out.println("3. Add customer");
        System.out.println("4. View all customers");
        System.out.println("5. Create order");
        System.out.println("6. View orders");
        System.out.println("7. Complete order");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private static void addItem() {
        System.out.print("Item ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Size: ");
        String size = scanner.nextLine();

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Brand: ");
        String brand = scanner.nextLine();

        ClothingItem item = new ClothingItem(id, name, size, price, brand);
        items.add(item);

        System.out.println("Item added successfully.");
    }

    private static void viewItems() {
        if (items.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        for (ClothingItem item : items) {
            System.out.println(item);
        }
    }

    private static void addCustomer() {
        System.out.print("Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Preferred size: ");
        String size = scanner.nextLine();

        System.out.print("Points: ");
        int points = scanner.nextInt();
        scanner.nextLine();

        customers.add(new Customer(id, name, size, points));
        System.out.println("Customer added.");
    }

    private static void viewCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    private static void createOrder() {
        System.out.print("Order ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Total amount: ");
        double total = scanner.nextDouble();
        scanner.nextLine();

        Order order = new Order(id, customerName, total, "Pending");
        orders.add(order);

        System.out.println("Order created.");
    }

    private static void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders.");
            return;
        }

        for (Order o : orders) {
            System.out.println(o);
        }
    }

    private static void completeOrder() {
        System.out.print("Enter order ID to complete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Order o : orders) {
            if (o.getOrderId() == id) {
                o.complete();
                System.out.println("Order completed.");
                return;
            }
        }

        System.out.println("Order not found.");
    }
}


