package Shop.database;

import Shop.model.ClothingItem;
import Shop.model.Shirt;
import Shop.model.Jacket;
import Shop.model.Customer;
import Shop.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class clothingDAO {

    public boolean insertClothingItem(ClothingItem item) {
        String sql = "INSERT INTO clothing_items (name, size, price, brand, type, extra) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = databaseconnection.getConnection();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, item.getName());
                statement.setString(2, item.getSize());
                statement.setDouble(3, item.getPrice());
                statement.setString(4, item.getBrand());

                String type = (item instanceof Shirt) ? "Shirt" : "Jacket";
                boolean extra = (item instanceof Shirt) ? ((Shirt) item).isLongSleeve() : ((Jacket) item).hasHood();

                statement.setString(5, type);
                statement.setBoolean(6, extra);

                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Clothing item inserted successfully!");
                }
                statement.close();
            } catch (SQLException e) {
                System.out.println("Insert failed!");
                e.printStackTrace();
            } finally {
                databaseconnection.closeConnection(connection);
            }
        }
        return false;
    }

    public List<ClothingItem> getAllClothingItems() {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_items";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return items;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClothingItem item = extractClothingFromResultSet(resultSet);
                if (item != null) items.add(item);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Read failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return items;
    }

    public boolean updateClothingItem(ClothingItem item) {
        String sql = "UPDATE clothing_items SET name = ?, size = ?, price = ?, brand = ?, type = ?, extra = ? WHERE item_id = ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, item.getName());
            statement.setString(2, item.getSize());
            statement.setDouble(3, item.getPrice());
            statement.setString(4, item.getBrand());

            String type = (item instanceof Shirt) ? "Shirt" : "Jacket";
            boolean extra = (item instanceof Shirt) ? ((Shirt) item).isLongSleeve() : ((Jacket) item).hasHood();

            statement.setString(5, type);
            statement.setBoolean(6, extra);
            statement.setInt(7, item.getItemId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();
            if (rowsUpdated > 0) {
                System.out.println("Clothing item updated: " + item.getName());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Update failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return false;
    }

    public boolean deleteClothingItem(int itemId) {
        String sql = "DELETE FROM clothing_items WHERE item_id = ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemId);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            if (rowsDeleted > 0) {
                System.out.println("Clothing item deleted (ID: " + itemId + ")");
                return true;
            } else {
                System.out.println("No item found with ID: " + itemId);
            }
        } catch (SQLException e) {
            System.out.println("Delete failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return false;
    }

    public List<ClothingItem> searchByName(String name) {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_items WHERE name ILIKE ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return items;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClothingItem item = extractClothingFromResultSet(resultSet);
                if (item != null) items.add(item);
            }
            resultSet.close();
            statement.close();
            System.out.println("Found " + items.size() + " items");
        } catch (SQLException e) {
            System.out.println("Search failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return items;
    }

    public List<ClothingItem> searchByPriceRange(double minPrice, double maxPrice) {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_items WHERE price BETWEEN ? AND ? ORDER BY price DESC";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return items;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minPrice);
            statement.setDouble(2, maxPrice);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClothingItem item = extractClothingFromResultSet(resultSet);
                if (item != null) items.add(item);
            }
            resultSet.close();
            statement.close();
            System.out.println("Found " + items.size() + " items");
        } catch (SQLException e) {
            System.out.println("Search failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return items;
    }

    public List<ClothingItem> searchByMinPrice(double minPrice) {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_items WHERE price >= ? ORDER BY price DESC";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return items;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minPrice);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClothingItem item = extractClothingFromResultSet(resultSet);
                if (item != null) items.add(item);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return items;
    }

    private ClothingItem extractClothingFromResultSet(ResultSet resultSet) throws SQLException {
        int itemId = resultSet.getInt("item_id");
        String name = resultSet.getString("name");
        String size = resultSet.getString("size");
        double price = resultSet.getDouble("price");
        String brand = resultSet.getString("brand");
        String type = resultSet.getString("type");
        boolean extra = resultSet.getBoolean("extra");

        if (type.equals("Shirt")) {
            return new Shirt(itemId, name, size, price, brand, extra);
        } else if (type.equals("Jacket")) {
            return new Jacket(itemId, name, size, price, brand, extra);
        }
        return null;
    }

    public ClothingItem getClothingItemById(int itemId) {
        String sql = "SELECT * FROM clothing_items WHERE item_id = ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractClothingFromResultSet(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return null;
    }

    public boolean insertCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_id, name, preferred_size, points) VALUES (?, ?, ?, ?)";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customer.getCustomerId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getPreferredSize());
            statement.setInt(4, customer.getPoints());
            int rowsInserted = statement.executeUpdate();
            statement.close();
            if (rowsInserted > 0) {
                System.out.println("Customer added: " + customer.getName());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Insert customer failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return false;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY customer_id";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return customers;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                customers.add(customer);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Get customers failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return customers;
    }

    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractCustomerFromResultSet(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return null;
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, preferred_size = ?, points = ? WHERE customer_id = ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPreferredSize());
            statement.setInt(3, customer.getPoints());
            statement.setInt(4, customer.getCustomerId());
            int rowsUpdated = statement.executeUpdate();
            statement.close();
            if (rowsUpdated > 0) {
                System.out.println("Customer updated: " + customer.getName());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Update customer failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return false;
    }

    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            if (rowsDeleted > 0) {
                System.out.println("Customer deleted (ID: " + customerId + ")");
                return true;
            } else {
                System.out.println("No customer found with ID: " + customerId);
            }
        } catch (SQLException e) {
            System.out.println("Delete customer failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return false;
    }

    private Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        int customerId = resultSet.getInt("customer_id");
        String name = resultSet.getString("name");
        String preferredSize = resultSet.getString("preferred_size");
        int points = resultSet.getInt("points");
        return new Customer(customerId, name, preferredSize, points);
    }

    public boolean insertOrder(Order order) {
        String sql = "INSERT INTO orders (order_id, customer_name, total_amount, status) VALUES (?, ?, ?, ?)";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, order.getOrderId());
            statement.setString(2, order.getCustomerName());
            statement.setDouble(3, order.getTotal());
            statement.setString(4, order.getStatus());
            int rowsInserted = statement.executeUpdate();
            statement.close();
            if (rowsInserted > 0) {
                System.out.println("Order created: ID " + order.getOrderId());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Insert order failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return false;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_id";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return orders;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = extractOrderFromResultSet(resultSet);
                orders.add(order);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Get orders failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractOrderFromResultSet(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return null;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newStatus);
            statement.setInt(2, orderId);
            int rowsUpdated = statement.executeUpdate();
            statement.close();
            if (rowsUpdated > 0) {
                System.out.println("Order " + orderId + " status updated to: " + newStatus);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Update order status failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return false;
    }

    public boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        Connection connection = databaseconnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            if (rowsDeleted > 0) {
                System.out.println("Order deleted (ID: " + orderId + ")");
                return true;
            } else {
                System.out.println("No order found with ID: " + orderId);
            }
        } catch (SQLException e) {
            System.out.println("Delete order failed!");
            e.printStackTrace();
        } finally {
            databaseconnection.closeConnection(connection);
        }
        return false;
    }

    private Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
        int orderId = resultSet.getInt("order_id");
        String customerName = resultSet.getString("customer_name");
        double totalAmount = resultSet.getDouble("total_amount");
        String status = resultSet.getString("status");
        return new Order(orderId, customerName, totalAmount, status);
    }
}