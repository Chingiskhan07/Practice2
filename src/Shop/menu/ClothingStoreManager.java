package Shop.menu;

import Shop.exception.InvalidInputException;
import Shop.model.*;
import Shop.database.clothingDAO;
import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

public class ClothingStoreManager implements Menu {
    private clothingDAO clothingDAO;
    private Scanner scanner = new Scanner(System.in);

    public ClothingStoreManager() {
        clothingDAO = new clothingDAO();
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
                        updateItem();
                        break;
                    case 4:
                        deleteItem();
                        break;
                    case 5:
                        searchByName();
                        break;
                    case 6:
                        searchByPriceRange();
                        break;
                    case 7:
                        searchByMinPrice();
                        break;
                    case 8:
                        addCustomer();
                        break;
                    case 9:
                        viewCustomers();
                        break;
                    case 10:
                        createOrder();
                        break;
                    case 11:
                        viewOrders();
                        break;
                    case 12:
                        completeOrder();
                        break;
                    case 13:
                        cancelOrder();
                        break;
                    case 14:
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
        System.out.println("3. Update item");
        System.out.println("4. Delete item");
        System.out.println("5. Search by name");
        System.out.println("6. Search by price range");
        System.out.println("7. Search by min price");
        System.out.println("8. Add customer");
        System.out.println("9. View all customers");
        System.out.println("10. Create order");
        System.out.println("11. View orders");
        System.out.println("12. Complete order");
        System.out.println("13. Cancel order");
        System.out.println("14. Apply discount to item");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private void addItem() {
        try {
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

            ClothingItem item;

            if (type.equalsIgnoreCase("Shirt")) {
                System.out.print("Long sleeve? (true/false): ");
                boolean longSleeve = scanner.nextBoolean();
                scanner.nextLine();

                item = new Shirt(0, name, size, price, brand, longSleeve);

            } else if (type.equalsIgnoreCase("Jacket")) {
                System.out.print("Has hood? (true/false): ");
                boolean hood = scanner.nextBoolean();
                scanner.nextLine();

                item = new Jacket(0, name, size, price, brand, hood);

            } else {
                throw new InvalidInputException("Type must be Shirt or Jacket");
            }

            boolean success = clothingDAO.insertClothingItem(item);

            if (success) {
                System.out.println("Item saved to database successfully!");
            } else {
                System.out.println("Failed to save item.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewItems() {
        List<ClothingItem> items = clothingDAO.getAllClothingItems();

        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        System.out.println("\n=== Inventory ===");
        for (ClothingItem item : items) {
            System.out.println(item);
            item.work();
            if (item.isPremium()) {
                System.out.println("→ This is a premium item");
            }
            if (item instanceof Jacket jacket) {
                if (jacket.hasHood()) {
                    System.out.println("→ This jacket has a hood");
                }
            } else if (item instanceof Shirt shirt) {
                if (shirt.isWinterShirt()) {
                    System.out.println("→ Suitable for winter");
                }
            }
            System.out.println();
        }
    }

    private void updateItem() {
        try {
            System.out.print("Enter Item ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ClothingItem currentItem = clothingDAO.getClothingItemById(id);
            if (currentItem == null) {
                System.out.println("No item found with ID: " + id);
                return;
            }

            System.out.println("Current Info: " + currentItem);

            System.out.print("New Name [" + currentItem.getName() + "]: ");
            String newName = scanner.nextLine();
            if (newName.trim().isEmpty()) {
                newName = currentItem.getName();
            }

            System.out.print("New Size [" + currentItem.getSize() + "]: ");
            String newSize = scanner.nextLine();
            if (newSize.trim().isEmpty()) {
                newSize = currentItem.getSize();
            }

            System.out.print("New Price [" + currentItem.getPrice() + "]: ");
            String priceInput = scanner.nextLine();
            double newPrice = priceInput.trim().isEmpty() ? currentItem.getPrice() : Double.parseDouble(priceInput);

            System.out.print("New Brand [" + currentItem.getBrand() + "]: ");
            String newBrand = scanner.nextLine();
            if (newBrand.trim().isEmpty()) {
                newBrand = currentItem.getBrand();
            }

            ClothingItem updatedItem;
            if (currentItem instanceof Shirt) {
                System.out.print("Long sleeve? (true/false) [" + ((Shirt) currentItem).isLongSleeve() + "]: ");
                String extraInput = scanner.nextLine();
                boolean newExtra = extraInput.trim().isEmpty() ? ((Shirt) currentItem).isLongSleeve() : Boolean.parseBoolean(extraInput);
                updatedItem = new Shirt(id, newName, newSize, newPrice, newBrand, newExtra);
            } else {
                System.out.print("Has hood? (true/false) [" + ((Jacket) currentItem).hasHood() + "]: ");
                String extraInput = scanner.nextLine();
                boolean newExtra = extraInput.trim().isEmpty() ? ((Jacket) currentItem).hasHood() : Boolean.parseBoolean(extraInput);
                updatedItem = new Jacket(id, newName, newSize, newPrice, newBrand, newExtra);
            }

            boolean success = clothingDAO.updateClothingItem(updatedItem);
            if (success) {
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("Update failed.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteItem() {
        try {
            System.out.print("Enter Item ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ClothingItem item = clothingDAO.getClothingItemById(id);
            if (item == null) {
                System.out.println("No item found with ID: " + id);
                return;
            }

            System.out.println("Item to delete: " + item);
            System.out.print("Are you sure? (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                boolean success = clothingDAO.deleteClothingItem(id);
                if (success) {
                    System.out.println("Item deleted successfully.");
                } else {
                    System.out.println("Delete failed.");
                }
            } else {
                System.out.println("Delete cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchByName() {
        try {
            System.out.print("Enter name to search: ");
            String name = scanner.nextLine();
            List<ClothingItem> results = clothingDAO.searchByName(name);
            if (results.isEmpty()) {
                System.out.println("No items found.");
            } else {
                System.out.println("\n=== Search Results ===");
                for (ClothingItem item : results) {
                    System.out.println(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchByPriceRange() {
        try {
            System.out.print("Enter minimum price: ");
            double min = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter maximum price: ");
            double max = scanner.nextDouble();
            scanner.nextLine();

            List<ClothingItem> results = clothingDAO.searchByPriceRange(min, max);
            if (results.isEmpty()) {
                System.out.println("No items found in this range.");
            } else {
                System.out.println("\n=== Search Results ===");
                for (ClothingItem item : results) {
                    System.out.println(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchByMinPrice() {
        try {
            System.out.print("Enter minimum price: ");
            double min = scanner.nextDouble();
            scanner.nextLine();

            List<ClothingItem> results = clothingDAO.searchByMinPrice(min);
            if (results.isEmpty()) {
                System.out.println("No items found.");
            } else {
                System.out.println("\n=== Search Results ===");
                for (ClothingItem item : results) {
                    System.out.println(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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

            Customer customer = new Customer(id, name, size, points);
            boolean success = clothingDAO.insertCustomer(customer);

            if (success) {
                System.out.println("Customer saved to database successfully!");
            } else {
                System.out.println("Failed to save customer.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine();
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewCustomers() {
        List<Customer> customers = clothingDAO.getAllCustomers();
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

            Order order = new Order(id, customerName, total, "Pending");
            boolean success = clothingDAO.insertOrder(order);

            if (success) {
                System.out.println("Order saved to database successfully!");
            } else {
                System.out.println("Failed to save order.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine();
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewOrders() {
        List<Order> orders = clothingDAO.getAllOrders();
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

            boolean success = clothingDAO.updateOrderStatus(id, "Completed");
            if (success) {
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

            boolean success = clothingDAO.updateOrderStatus(id, "Cancelled");
            if (success) {
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

            ClothingItem item = clothingDAO.getClothingItemById(id);
            if (item != null) {
                System.out.print("Enter discount percent: ");
                double percent = scanner.nextDouble();
                scanner.nextLine();

                item.applyDiscount(percent);
                boolean success = clothingDAO.updateClothingItem(item);
                if (success) {
                    System.out.println("Discount applied. New price: " + item.getPrice());
                } else {
                    System.out.println("Failed to update price in database.");
                }
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
}