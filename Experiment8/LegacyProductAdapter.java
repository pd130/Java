public class LegacyProductAdapter implements ProductSource {
    LegacyProduct legacyProduct;

    LegacyProductAdapter(LegacyProduct lp) {
        this.legacyProduct = lp;
    }

    @Override
    public Product toProduct() {
        String[] parts = legacyProduct.getRawData().split(",");
        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        double price = Double.parseDouble(parts[2].trim());
        int qty = Integer.parseInt(parts[3].trim());
        return new Product(id, name, price, qty);
    }
}
