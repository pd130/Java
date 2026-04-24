import java.util.Scanner;

public class CollectionsDesignPatternApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ProductManager pm1 = ProductManager.getInstance();
        ProductManager pm2 = ProductManager.getInstance();
        System.out.println("Same instance? " + (pm1 == pm2));

        pm1.addProduct(new Product(1, "Laptop", 65000, 10));
        pm1.addProduct(new Product(2, "Mouse", 800, 50));
        pm1.addProduct(new Product(3, "Keyboard", 1500, 30));
        pm1.addProduct(new Product(4, "Monitor", 18000, 8));

        pm1.importLegacyProduct("101, OldPrinter, 4500.0, 3");
        pm1.importLegacyProduct("102, DotMatrix, 2200.0, 5");
        pm1.importLegacyProduct("103, TrackballMouse, 950.0, 12");

        pm1.displayProducts();
        pm1.displayFilteredByPrice(2000);

        while (true) {
            System.out.println("\n1.Add  2.Remove  3.Display  4.Search  5.SortByPrice");
            System.out.println("6.ImportLegacy  7.FilterByPrice  0.Exit");
            System.out.print("Choice: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }

            if (ch == 1) {
                try {
                    System.out.print("ID: ");
                    int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Name: ");
                    String nm = sc.nextLine().trim();
                    System.out.print("Price: ");
                    double pr = Double.parseDouble(sc.nextLine().trim());
                    System.out.print("Qty: ");
                    int qty = Integer.parseInt(sc.nextLine().trim());
                    pm1.addProduct(new Product(id, nm, pr, qty));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            } else if (ch == 2) {
                System.out.print("Product ID to remove: ");
                try {
                    pm1.removeProduct(Integer.parseInt(sc.nextLine().trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid!");
                }
            } else if (ch == 3) {
                pm1.displayProducts();
            } else if (ch == 4) {
                System.out.print("Search keyword: ");
                pm1.searchProduct(sc.nextLine().trim());
            } else if (ch == 5) {
                pm1.sortByPrice();
                pm1.displayProducts();
            } else if (ch == 6) {
                System.out.print("Enter legacy data (id,name,price,qty): ");
                pm1.importLegacyProduct(sc.nextLine().trim());
            } else if (ch == 7) {
                System.out.print("Show products priced at or below: ");
                try {
                    pm1.displayFilteredByPrice(Double.parseDouble(sc.nextLine().trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price.");
                }
            } else if (ch == 0) {
                System.out.println("Goodbye!");
                sc.close();
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }
}
