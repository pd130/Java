import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteredInventoryIterator implements Iterator<Product> {
    ArrayList<Product> products;
    double maxPrice;
    int index = 0;
    Product nextProduct = null;

    FilteredInventoryIterator(ArrayList<Product> products, double maxPrice) {
        this.products = products;
        this.maxPrice = maxPrice;
        advance();
    }

    void advance() {
        nextProduct = null;
        while (index < products.size()) {
            Product p = products.get(index++);
            if (p.price <= maxPrice) {
                nextProduct = p;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextProduct != null;
    }

    @Override
    public Product next() {
        if (!hasNext()) throw new NoSuchElementException();
        Product result = nextProduct;
        advance();
        return result;
    }
}
