import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class ProductManager {
    static ProductManager instance = null;
    ArrayList<Product> productList;
    HashMap<Integer, Product> productMap;

    private ProductManager() {
        productList = new ArrayList<>();
        productMap = new HashMap<>();
    }

    static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    void addProduct(Product p) {
        if (productMap.containsKey(p.productId)) {
            System.out.println("Product ID already exists!");
            return;
        }
        productList.add(p);
        productMap.put(p.productId, p);
        System.out.println("Product added: " + p.name);
    }

    void removeProduct(int id) {
        Product p = productMap.remove(id);
        if (p != null) {
            productList.remove(p);
            System.out.println("Removed: " + p.name);
        } else {
            System.out.println("Product ID not found.");
        }
    }

    void displayProducts() {
        if (productList.isEmpty()) {
            System.out.println("No products.");
            return;
        }
        printTableHeader();
        Iterator<Product> it = getIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        printTableFooter();
    }

    void searchProduct(String keyword) {
        boolean found = false;
        Iterator<Product> it = getIterator();
        while (it.hasNext()) {
            Product p = it.next();
            if (p.name.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("Found: " + p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No product matched: " + keyword);
        }
    }

    void sortByPrice() {
        productList.sort(Comparator.comparingDouble(p -> p.price));
        System.out.println("Products sorted by price.");
    }

    void importLegacyProduct(String rawData) {
        try {
            LegacyProduct legacy = new LegacyProduct(rawData);
            LegacyProductAdapter adapter = new LegacyProductAdapter(legacy);
            Product p = adapter.toProduct();
            addProduct(p);
            System.out.println("Legacy product imported: " + p.name);
        } catch (Exception e) {
            System.out.println("Failed to import legacy data: " + e.getMessage());
        }
    }

    Iterator<Product> getIterator() {
        return new InventoryIterator(productList);
    }

    Iterator<Product> getFilteredIterator(double maxPrice) {
        return new FilteredInventoryIterator(productList, maxPrice);
    }

    void displayFilteredByPrice(double maxPrice) {
        System.out.println("Products priced at or below " + maxPrice + ":");
        Iterator<Product> it = getFilteredIterator(maxPrice);
        boolean found = false;
        printTableHeader();
        while (it.hasNext()) {
            System.out.println(it.next());
            found = true;
        }
        if (!found) {
            System.out.println("No products found in this range.");
        }
        printTableFooter();
    }

    void printTableHeader() {
        System.out.println("+------+----------------------+----------+-------+");
        System.out.println("| ID   | Name                 |  Price   | Qty   |");
        System.out.println("+------+----------------------+----------+-------+");
    }

    void printTableFooter() {
        System.out.println("+------+----------------------+----------+-------+");
    }
}
