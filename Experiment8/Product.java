public class Product {
    int productId;
    String name;
    double price;
    int quantity;

    Product(int id, String name, double price, int qty) {
        this.productId = id;
        this.name = name;
        this.price = price;
        this.quantity = qty;
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %8.2f | %-5d |", productId, name, price, quantity);
    }
}
