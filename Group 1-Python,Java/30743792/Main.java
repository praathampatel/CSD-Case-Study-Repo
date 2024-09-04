import java.time.LocalDateTime;
import java.util.*;

// MENU ITEM CLASS
class MenuItem {
    private int id;
    private String name;
    private String category;
    private double price;
    private boolean available;

    public MenuItem(int id, String name, String category, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = available;
    }
// menu item list
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}

// ORDER CLASS
class Order {
    private int id;
    private int menuItemId;
    private int quantity;
    private LocalDateTime orderTime;
    private String status;
// use variables menuITEMID, orderTime
    public Order(int id, int menuItemId, int quantity, LocalDateTime orderTime, String status) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.orderTime = orderTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", menuItemId=" + menuItemId +
                ", quantity=" + quantity +
                ", orderTime=" + orderTime +
                ", status='" + status + '\'' +
                '}';
    }
}

// RESTAURANT MANAGEMENT CLASS, used hashmap for optimisation

class RestaurantManager {
    private HashMap<Integer, MenuItem> menuItems = new HashMap<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private int nextMenuItemId = 1;
    private int nextOrderId = 1;

    public void addMenuItem(String name, String category, double price, boolean available) {
        MenuItem item = new MenuItem(nextMenuItemId++, name, category, price, available);
        menuItems.put(item.getId(), item);
        System.out.println("Menu item added: " + item);
    }

    public void removeMenuItem(int itemId) {
        MenuItem removedItem = menuItems.remove(itemId);
        if (removedItem != null) {
            System.out.println("Menu item removed: " + removedItem);
        } else {
            System.out.println("Menu item not found with ID: " + itemId);
        }
    }

    public void updateMenuItem(int itemId, String name, String category, double price, boolean available) {
        MenuItem item = menuItems.get(itemId);
        if (item != null) {
            item.setName(name);
            item.setCategory(category);
            item.setPrice(price);
            item.setAvailable(available);
            System.out.println("Menu item updated: " + item);
        } else {
            System.out.println("Menu item not found with ID: " + itemId);
        }
    }

    public List<MenuItem> searchMenuItems(String keyword) {
        List<MenuItem> results = new ArrayList<>();
        for (MenuItem item : menuItems.values()) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    item.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(item);
            }
        }
        if (results.isEmpty()) {
            System.out.println("No menu items found for keyword: " + keyword);
        } else {
            System.out.println("Search results:");
            for (MenuItem item : results) {
                System.out.println(item);
            }
        }
        return results;
    }

    public void placeOrder(int itemId, int quantity) {
        try {
            MenuItem item = menuItems.get(itemId);
            if (item != null && item.isAvailable()) {
                Order order = new Order(nextOrderId++, itemId, quantity, LocalDateTime.now(), "pending");
                orders.add(order);
                item.setAvailable(false); // Assuming the item becomes unavailable after ordering.
                System.out.println("Order placed: " + order);
            } else {
                System.out.println("Menu item not available or not found with ID: " + itemId);
            }
        } catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }

    public void processOrder(int orderId) {
        try {
            boolean found = false;
            for (Order order : orders) {
                if (order.getId() == orderId) {
                    order.setStatus("preparing");
                    System.out.println("Order is now preparing: " + order);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Order not found with ID: " + orderId);
            }
        } catch (Exception e) {
            System.out.println("Error processing order: " + e.getMessage());
        }
    }
}

// MAIN CLASS
public class Main {
    public static void main(String[] args) {
        RestaurantManager manager = new RestaurantManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nRestaurant Management System");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Update Menu Item");
            System.out.println("4. Search Menu Items");
            System.out.println("5. Place Order");
            System.out.println("6. Process Order");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter category: ");
                        String category = scanner.nextLine();
                        System.out.print("Enter price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Is available (true/false): ");
                        boolean available = scanner.nextBoolean();
                        manager.addMenuItem(name, category, price, available);
                        break;
                    case 2:
                        System.out.print("Enter menu item ID to remove: ");
                        int removeId = scanner.nextInt();
                        manager.removeMenuItem(removeId);
                        break;
                    case 3:
                        System.out.print("Enter menu item ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new category: ");
                        String newCategory = scanner.nextLine();
                        System.out.print("Enter new price: ");
                        double newPrice = scanner.nextDouble();
                        System.out.print("Is available (true/false): ");
                        boolean newAvailable = scanner.nextBoolean();
                        manager.updateMenuItem(updateId, newName, newCategory, newPrice, newAvailable);
                        break;
                    case 4:
                        System.out.print("Enter keyword to search: ");
                        String keyword = scanner.nextLine();
                        manager.searchMenuItems(keyword);
                        break;
                    case 5:
                        System.out.print("Enter menu item ID to order: ");
                        int orderId = scanner.nextInt();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        manager.placeOrder(orderId, quantity);
                        break;
                    case 6:
                        System.out.print("Enter order ID to process: ");
                        int processId = scanner.nextInt();
                        manager.processOrder(processId);
                        break;
                    case 0:
                        System.out.println("Exiting the system.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
                choice = -1; // Set choice to an invalid number to continue the loop
            }
        } while (choice != 0);

        scanner.close();
    }
}