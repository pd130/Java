import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class InventoryIterator implements Iterator<Product> {
    ArrayList<Product> products;
    int index = 0;

    InventoryIterator(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public boolean hasNext() {
        return index < products.size();
    }

    @Override
    public Product next() {
        if (!hasNext()) throw new NoSuchElementException("No more products.");
        return products.get(index++);
    }
}
