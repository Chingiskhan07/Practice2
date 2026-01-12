package Shop.menu;

import Shop.exception.InvalidInputException;
import Shop.model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClothingStoreManager implements Menu {
    private ArrayList<ClothingItem> items = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);

    public ClothingStoreManager() {
        items.add(new Shirt(1, "T-Shirt", "M", 12000, "Nike", false));
        items.add(new Shirt(2, "Polo", "L", 15000, "Zara", true));
        items.add(new Jacket(3, "Winter Jacket", "XL", 35000, "Adidas", true));

        customers.add(new Customer(101, "Ali", "M", 80));

        orders.add(new Order(5001, "Ali", 12000, "Pending"));
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            try {
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
                    case 8:
                        cancelOrder();
                        break;
                    case 9:
                        applyDiscountToItem();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Program terminated.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }

                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=== CLOTHING STORE MENU by Chingiskhan.A ===");
        System.out.println("1. Add clothing item");
        System.out.println("2. View all items");
        System.out.println("3. Add customer");
        System.out.println("4. View all customers");
        System.out.println("5. Create order");
        System.out.println("6. View orders");
        System.out.println("7. Complete order");
        System.out.println("8. Cancel order");
        System.out.println("9. Apply discount to item");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private void addItem() {
        try {
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

            System.out.print("Type (Shirt/Jacket): ");
            String type = scanner.nextLine();

            boolean extra;
            if (type.equalsIgnoreCase("Shirt")) {
                System.out.print("Long sleeve? (true/false): ");
                extra = scanner.nextBoolean();
                scanner.nextLine();
                items.add(new Shirt(id, name, size, price, brand, extra));
            } else if (type.equalsIgnoreCase("Jacket")) {
                System.out.print("Has hood? (true/false): ");
                extra = scanner.nextBoolean();
                scanner.nextLine();
                items.add(new Jacket(id, name, size, price, brand, extra));
            } else {
                throw new InvalidInputException("Invalid item type. Must be Shirt or Jacket.");
            }
            System.out.println("Item added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine();
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewItems() {
        if (items.isEmpty()) {
            System.out.println("No items available.");
            return;
        }
        System.out.println("\n=== Inventory ===");
        for (ClothingItem item : items) {
            System.out.println(item);
            item.work();
            if (item.isPremium()) {
                System.out.println(" → This is a premium item");
            }
            if (item instanceof Jacket jacket) {
                if (jacket.hasHood()) {
                    System.out.println(" → This jacket has a hood");
                }
                jacket.zipUp();
            } else if (item instanceof Shirt shirt) {
                if (shirt.isWinterShirt()) {
                    System.out.println(" → Suitable for winter");
                }
                shirt.fold();
            }
            System.out.println();
        }
    }

    private void addCustomer() {
        try {
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
            System.out.println("Customer added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine();
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        System.out.println("\n=== Customers ===");
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    private void createOrder() {
        try {
            System.out.print("Order ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Customer name: ");
            String customerName = scanner.nextLine();

            System.out.print("Total amount: ");
            double total = scanner.nextDouble();
            scanner.nextLine();

            orders.add(new Order(id, customerName, total, "Pending"));
            System.out.println("Order created successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine();
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders.");
            return;
        }
        System.out.println("\n=== Orders ===");
        for (Order o : orders) {
            System.out.println(o);
        }
    }

    private void completeOrder() {
        try {
            System.out.print("Enter Order ID to complete: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Order order = findOrderById(id);
            if (order != null) {
                order.complete();
                System.out.println("Order completed.");
            } else {
                System.out.println("Order not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine();
        }
    }

    private void cancelOrder() {
        try {
            System.out.print("Enter Order ID to cancel: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Order order = findOrderById(id);
            if (order != null) {
                order.cancel();
                System.out.println("Order cancelled.");
            } else {
                System.out.println("Order not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine();
        }
    }

    private void applyDiscountToItem() {
        try {
            System.out.print("Enter Item ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ClothingItem item = findItemById(id);
            if (item != null) {
                System.out.print("Enter discount percent: ");
                double percent = scanner.nextDouble();
                scanner.nextLine();

                item.applyDiscount(percent);
                System.out.println("Discount applied. New price: " + item.getPrice());
            } else {
                System.out.println("Item not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine();
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private ClothingItem findItemById(int id) {
        for (ClothingItem item : items) {
            if (item.getItemId() == id) {
                return item;
            }
        }
        return null;
    }

    private Order findOrderById(int id) {
        for (Order order : orders) {
            if (order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }
}