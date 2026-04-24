import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;


public class RestaurantManagerApp extends Application {

    static final String URL  = "jdbc:mysql://localhost:3306/college_db?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "";

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }


    public static class Restaurant {
        private final IntegerProperty id;
        private final StringProperty name;
        private final StringProperty address;

        public Restaurant(int id, String name, String address) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.address = new SimpleStringProperty(address);
        }

        public int getId() { return id.get(); }
        public String getName() { return name.get(); }
        public String getAddress() { return address.get(); }
        public IntegerProperty idProperty() { return id; }
        public StringProperty nameProperty() { return name; }
        public StringProperty addressProperty() { return address; }
    }


    public static class MenuItem {
        private final IntegerProperty id;
        private final StringProperty name;
        private final DoubleProperty price;
        private final IntegerProperty resId;

        public MenuItem(int id, String name, double price, int resId) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleDoubleProperty(price);
            this.resId = new SimpleIntegerProperty(resId);
        }

        public int getId() { return id.get(); }
        public String getName() { return name.get(); }
        public double getPrice() { return price.get(); }
        public int getResId() { return resId.get(); }
        public IntegerProperty idProperty() { return id; }
        public StringProperty nameProperty() { return name; }
        public DoubleProperty priceProperty() { return price; }
        public IntegerProperty resIdProperty() { return resId; }
    }

    private final ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();
    private final ObservableList<MenuItem> menuItemList = FXCollections.observableArrayList();
    private Label statusLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        createTablesIfNotExist();

        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabs.getTabs().add(new Tab("Restaurants", buildRestaurantTab()));
        tabs.getTabs().add(new Tab("Menu Items", buildMenuItemTab()));

        statusLabel = new Label("Ready");
        HBox statusBar = new HBox(statusLabel);
        statusBar.setPadding(new Insets(5, 10, 5, 10));

        BorderPane root = new BorderPane();
        root.setCenter(tabs);
        root.setBottom(statusBar);

        stage.setTitle("Restaurant Manager");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();

        loadRestaurants();
        loadMenuItems();
        setStatus("Connected to college_db!");
    }


    private VBox buildRestaurantTab() {
    
        TableView<Restaurant> table = new TableView<>(restaurantList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Restaurant, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Restaurant, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Restaurant, String> colAddr = new TableColumn<>("Address");
        colAddr.setCellValueFactory(new PropertyValueFactory<>("address"));

        table.getColumns().addAll(colId, colName, colAddr);

        
        Button btnInsert  = new Button("Insert");
        Button btnUpdate  = new Button("Update");
        Button btnDelete  = new Button("Delete");
        Button btnRefresh = new Button("Refresh");

        btnInsert.setOnAction(e -> showInsertRestaurantDialog());
        btnUpdate.setOnAction(e -> showUpdateRestaurantDialog());
        btnDelete.setOnAction(e -> showDeleteRestaurantDialog());
        btnRefresh.setOnAction(e -> { loadRestaurants(); setStatus("Refreshed."); });

        HBox buttons = new HBox(8, btnInsert, btnUpdate, btnDelete, btnRefresh);
        buttons.setPadding(new Insets(8));

        VBox box = new VBox(8, buttons, table);
        VBox.setVgrow(table, Priority.ALWAYS);
        box.setPadding(new Insets(10));
        return box;
    }


    private VBox buildMenuItemTab() {
        
        TableView<MenuItem> table = new TableView<>(menuItemList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<MenuItem, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MenuItem, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<MenuItem, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<MenuItem, Integer> colResId = new TableColumn<>("Restaurant ID");
        colResId.setCellValueFactory(new PropertyValueFactory<>("resId"));

        table.getColumns().addAll(colId, colName, colPrice, colResId);


        Button btnInsert     = new Button("Insert");
        Button btnUpdate     = new Button("Update Price");
        Button btnDelete     = new Button("Delete");
        Button btnByPrice    = new Button("Filter by Price");
        Button btnByRest     = new Button("Filter by Restaurant");
        Button btnRefresh    = new Button("Refresh");
        Button btnSeed       = new Button("Seed Sample Data");

        btnInsert.setOnAction(e  -> showInsertMenuItemDialog());
        btnUpdate.setOnAction(e  -> showUpdateMenuItemDialog());
        btnDelete.setOnAction(e  -> showDeleteMenuItemDialog());
        btnByPrice.setOnAction(e -> showFilterByPriceDialog());
        btnByRest.setOnAction(e  -> showFilterByRestaurantDialog());
        btnRefresh.setOnAction(e -> { loadMenuItems(); setStatus("Refreshed."); });
        btnSeed.setOnAction(e    -> seedData());

        HBox buttons = new HBox(8, btnInsert, btnUpdate, btnDelete, btnByPrice, btnByRest, btnRefresh, btnSeed);
        buttons.setPadding(new Insets(8));

        VBox box = new VBox(8, buttons, table);
        VBox.setVgrow(table, Priority.ALWAYS);
        box.setPadding(new Insets(10));
        return box;
    }


    private void setStatus(String msg) {
        Platform.runLater(() -> statusLabel.setText(msg));
    }


    private void showInsertRestaurantDialog() {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Insert Restaurant");

        TextField tfId   = new TextField(); tfId.setPromptText("ID");
        TextField tfName = new TextField(); tfName.setPromptText("Name");
        TextField tfAddr = new TextField(); tfAddr.setPromptText("Address");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));
        grid.addRow(0, new Label("ID:"), tfId);
        grid.addRow(1, new Label("Name:"), tfName);
        grid.addRow(2, new Label("Address:"), tfAddr);

        dlg.getDialogPane().setContent(grid);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dlg.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    int id = Integer.parseInt(tfId.getText().trim());
                    String sql = "INSERT INTO Restaurant (Id, Name, Address) VALUES (?, ?, ?)";
                    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setString(2, tfName.getText().trim());
                        ps.setString(3, tfAddr.getText().trim());
                        ps.executeUpdate();
                        loadRestaurants();
                        setStatus("Restaurant inserted!");
                    }
                } catch (Exception ex) { setStatus("Error: " + ex.getMessage()); }
            }
        });
    }

    private void showUpdateRestaurantDialog() {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Update Restaurant");

        TextField tfId   = new TextField(); tfId.setPromptText("ID to update");
        TextField tfName = new TextField(); tfName.setPromptText("New name (optional)");
        TextField tfAddr = new TextField(); tfAddr.setPromptText("New address (optional)");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));
        grid.addRow(0, new Label("ID:"), tfId);
        grid.addRow(1, new Label("New Name:"), tfName);
        grid.addRow(2, new Label("New Address:"), tfAddr);

        dlg.getDialogPane().setContent(grid);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dlg.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    int id = Integer.parseInt(tfId.getText().trim());
                    String newName = tfName.getText().trim();
                    String newAddr = tfAddr.getText().trim();

                    try (Connection con = getConnection()) {
                        if (!newName.isEmpty()) {
                            PreparedStatement ps = con.prepareStatement("UPDATE Restaurant SET Name=? WHERE Id=?");
                            ps.setString(1, newName); ps.setInt(2, id);
                            ps.executeUpdate();
                        }
                        if (!newAddr.isEmpty()) {
                            PreparedStatement ps = con.prepareStatement("UPDATE Restaurant SET Address=? WHERE Id=?");
                            ps.setString(1, newAddr); ps.setInt(2, id);
                            ps.executeUpdate();
                        }
                    }
                    loadRestaurants();
                    setStatus("Restaurant " + id + " updated!");
                } catch (Exception ex) { setStatus("Error: " + ex.getMessage()); }
            }
        });
    }

    private void showDeleteRestaurantDialog() {
        TextInputDialog dlg = new TextInputDialog();
        dlg.setTitle("Delete Restaurant");
        dlg.setHeaderText("Enter Restaurant ID to delete (linked menu items will also be deleted)");
        dlg.setContentText("ID:");

        dlg.showAndWait().ifPresent(val -> {
            try {
                int id = Integer.parseInt(val.trim());
                try (Connection con = getConnection();
                     PreparedStatement ps = con.prepareStatement("DELETE FROM Restaurant WHERE Id=?")) {
                    ps.setInt(1, id);
                    int rows = ps.executeUpdate();
                    loadRestaurants(); loadMenuItems();
                    setStatus(rows > 0 ? "Restaurant " + id + " deleted." : "ID not found.");
                }
            } catch (Exception ex) { setStatus("Error: " + ex.getMessage()); }
        });
    }


    private void showInsertMenuItemDialog() {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Insert Menu Item");

        TextField tfId    = new TextField(); tfId.setPromptText("ID");
        TextField tfName  = new TextField(); tfName.setPromptText("Name");
        TextField tfPrice = new TextField(); tfPrice.setPromptText("Price");
        TextField tfResId = new TextField(); tfResId.setPromptText("Restaurant ID");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));
        grid.addRow(0, new Label("ID:"), tfId);
        grid.addRow(1, new Label("Name:"), tfName);
        grid.addRow(2, new Label("Price:"), tfPrice);
        grid.addRow(3, new Label("Restaurant ID:"), tfResId);

        dlg.getDialogPane().setContent(grid);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dlg.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    String sql = "INSERT INTO MenuItem (Id, Name, Price, ResId) VALUES (?, ?, ?, ?)";
                    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setInt(1, Integer.parseInt(tfId.getText().trim()));
                        ps.setString(2, tfName.getText().trim());
                        ps.setDouble(3, Double.parseDouble(tfPrice.getText().trim()));
                        ps.setInt(4, Integer.parseInt(tfResId.getText().trim()));
                        ps.executeUpdate();
                        loadMenuItems();
                        setStatus("Menu item inserted!");
                    }
                } catch (Exception ex) { setStatus("Error: " + ex.getMessage()); }
            }
        });
    }

    private void showUpdateMenuItemDialog() {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Update Menu Item Price");

        TextField tfId       = new TextField(); tfId.setPromptText("Item ID (leave blank for bulk update)");
        TextField tfMaxPrice = new TextField(); tfMaxPrice.setPromptText("Max current price (for bulk)");
        TextField tfNewPrice = new TextField(); tfNewPrice.setPromptText("New price");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));
        grid.add(new Label("Leave ID blank to bulk-update all items with Price ≤ Max Price."), 0, 0, 2, 1);
        grid.addRow(1, new Label("Item ID (optional):"), tfId);
        grid.addRow(2, new Label("Max current price:"), tfMaxPrice);
        grid.addRow(3, new Label("New price:"), tfNewPrice);

        dlg.getDialogPane().setContent(grid);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dlg.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    double newPrice = Double.parseDouble(tfNewPrice.getText().trim());
                    String idStr = tfId.getText().trim();
                    int rows;

                    try (Connection con = getConnection()) {
                        if (!idStr.isEmpty()) {
                            PreparedStatement ps = con.prepareStatement("UPDATE MenuItem SET Price=? WHERE Id=?");
                            ps.setDouble(1, newPrice);
                            ps.setInt(2, Integer.parseInt(idStr));
                            rows = ps.executeUpdate();
                        } else {
                            double maxP = Double.parseDouble(tfMaxPrice.getText().trim());
                            PreparedStatement ps = con.prepareStatement("UPDATE MenuItem SET Price=? WHERE Price<=?");
                            ps.setDouble(1, newPrice);
                            ps.setDouble(2, maxP);
                            rows = ps.executeUpdate();
                        }
                        loadMenuItems();
                        setStatus(rows + " item(s) updated to " + newPrice);
                    }
                } catch (Exception ex) { setStatus("Error: " + ex.getMessage()); }
            }
        });
    }

    private void showDeleteMenuItemDialog() {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Delete Menu Item");

        TextField tfId     = new TextField(); tfId.setPromptText("Item ID (optional)");
        TextField tfPrefix = new TextField(); tfPrefix.setPromptText("Name prefix, e.g. 'P' (optional)");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));
        grid.add(new Label("Delete by ID, or by name prefix, or both."), 0, 0, 2, 1);
        grid.addRow(1, new Label("Item ID:"), tfId);
        grid.addRow(2, new Label("Name prefix:"), tfPrefix);

        dlg.getDialogPane().setContent(grid);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dlg.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try (Connection con = getConnection()) {
                    int rows = 0;
                    String idStr = tfId.getText().trim();
                    String prefix = tfPrefix.getText().trim();

                    if (!idStr.isEmpty()) {
                        PreparedStatement ps = con.prepareStatement("DELETE FROM MenuItem WHERE Id=?");
                        ps.setInt(1, Integer.parseInt(idStr));
                        rows += ps.executeUpdate();
                    }
                    if (!prefix.isEmpty()) {
                        PreparedStatement ps = con.prepareStatement("DELETE FROM MenuItem WHERE Name LIKE ?");
                        ps.setString(1, prefix + "%");
                        rows += ps.executeUpdate();
                    }
                    loadMenuItems();
                    setStatus(rows + " item(s) deleted.");
                } catch (Exception ex) { setStatus("Error: " + ex.getMessage()); }
            }
        });
    }

    private void showFilterByPriceDialog() {
        TextInputDialog dlg = new TextInputDialog("100");
        dlg.setTitle("Filter by Price");
        dlg.setHeaderText("Show Menu Items where Price ≤ X");
        dlg.setContentText("Max Price:");

        dlg.showAndWait().ifPresent(val -> {
            try {
                double max = Double.parseDouble(val.trim());
                String sql = "SELECT * FROM MenuItem WHERE Price <= ? ORDER BY Price";
                try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setDouble(1, max);
                    ResultSet rs = ps.executeQuery();
                    ObservableList<MenuItem> filtered = FXCollections.observableArrayList();
                    while (rs.next())
                        filtered.add(new MenuItem(rs.getInt("Id"), rs.getString("Name"),
                                rs.getDouble("Price"), rs.getInt("ResId")));
                    showFilteredResults("Items with Price ≤ " + max, filtered);
                    setStatus(filtered.size() + " items found.");
                }
            } catch (Exception ex) { setStatus("Error: " + ex.getMessage()); }
        });
    }

    private void showFilterByRestaurantDialog() {
        TextInputDialog dlg = new TextInputDialog("Cafe Java");
        dlg.setTitle("Filter by Restaurant");
        dlg.setHeaderText("Show Menu Items for a Restaurant");
        dlg.setContentText("Restaurant Name:");

        dlg.showAndWait().ifPresent(val -> {
            try {
                String sql = "SELECT m.Id, m.Name, m.Price, m.ResId " +
                             "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id WHERE r.Name = ?";
                try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, val.trim());
                    ResultSet rs = ps.executeQuery();
                    ObservableList<MenuItem> filtered = FXCollections.observableArrayList();
                    while (rs.next())
                        filtered.add(new MenuItem(rs.getInt("Id"), rs.getString("Name"),
                                rs.getDouble("Price"), rs.getInt("ResId")));
                    showFilteredResults("Items in " + val.trim(), filtered);
                    setStatus(filtered.size() + " items found.");
                }
            } catch (Exception ex) { setStatus("Error: " + ex.getMessage()); }
        });
    }

    private void showFilteredResults(String title, ObservableList<MenuItem> data) {
        TableView<MenuItem> table = new TableView<>(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<MenuItem, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MenuItem, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<MenuItem, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<MenuItem, Integer> colResId = new TableColumn<>("Restaurant ID");
        colResId.setCellValueFactory(new PropertyValueFactory<>("resId"));

        table.getColumns().addAll(colId, colName, colPrice, colResId);

        Stage popup = new Stage();
        popup.setTitle(title);
        popup.setScene(new Scene(new VBox(table), 500, 350));
        popup.show();
    }


    private void loadRestaurants() {
        restaurantList.clear();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Restaurant ORDER BY Id")) {
            while (rs.next())
                restaurantList.add(new Restaurant(rs.getInt("Id"), rs.getString("Name"), rs.getString("Address")));
        } catch (SQLException e) { setStatus("Error: " + e.getMessage()); }
    }

    private void loadMenuItems() {
        menuItemList.clear();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM MenuItem ORDER BY Id")) {
            while (rs.next())
                menuItemList.add(new MenuItem(rs.getInt("Id"), rs.getString("Name"),
                        rs.getDouble("Price"), rs.getInt("ResId")));
        } catch (SQLException e) { setStatus("Error: " + e.getMessage()); }
    }

    private void createTablesIfNotExist() {
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Restaurant " +
                             "(Id INT PRIMARY KEY, Name VARCHAR(60), Address VARCHAR(100))");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS MenuItem " +
                             "(Id INT PRIMARY KEY, Name VARCHAR(60), Price DOUBLE, ResId INT, " +
                             "FOREIGN KEY (ResId) REFERENCES Restaurant(Id) ON DELETE CASCADE)");
        } catch (SQLException e) {
            System.err.println("Table init error: " + e.getMessage());
        }
    }

    private void seedData() {
        try (Connection con = getConnection()) {
     
            String rSql = "INSERT IGNORE INTO Restaurant (Id, Name, Address) VALUES (?, ?, ?)";
            Object[][] restaurants = {
                {1, "Cafe Java",       "12 MG Road, Pune"},
                {2, "Pizza Palace",    "44 FC Road, Pune"},
                {3, "Burger Barn",     "7 Baner Road, Pune"},
                {4, "The Curry House", "22 Camp Area, Pune"},
                {5, "Noodle Nest",     "5 Kothrud Lane, Pune"},
                {6, "Spice Garden",    "18 Shivaji Nagar, Pune"},
                {7, "Pasta Point",     "3 Viman Nagar, Pune"},
                {8, "Dosa Delight",    "9 Hadapsar, Pune"},
                {9, "Tandoor Town",    "31 Wakad, Pune"},
                {10,"Sushi Stop",      "15 Hinjewadi, Pune"}
            };
            try (PreparedStatement ps = con.prepareStatement(rSql)) {
                for (Object[] r : restaurants) {
                    ps.setInt(1, (int)r[0]);
                    ps.setString(2, (String)r[1]);
                    ps.setString(3, (String)r[2]);
                    ps.executeUpdate();
                }
            }

            // Insert sample menu items
            String mSql = "INSERT IGNORE INTO MenuItem (Id, Name, Price, ResId) VALUES (?, ?, ?, ?)";
            Object[][] items = {
                {1,  "Espresso",           80.0,  1},
                {2,  "Cappuccino",          90.0,  1},
                {3,  "Pasta Arrabbiata",   150.0,  1},
                {4,  "Paneer Pizza",       250.0,  2},
                {5,  "Pepperoni Pizza",    300.0,  2},
                {6,  "Burger Classic",     120.0,  3},
                {7,  "Masala Chai",         50.0,  4},
                {8,  "Poha",               60.0,  8},
                {9,  "Noodle Soup",         95.0,  5},
                {10, "Chicken Tikka",      180.0,  6}
            };
            try (PreparedStatement ps = con.prepareStatement(mSql)) {
                for (Object[] m : items) {
                    ps.setInt(1, (int)m[0]);
                    ps.setString(2, (String)m[1]);
                    ps.setDouble(3, (double)m[2]);
                    ps.setInt(4, (int)m[3]);
                    ps.executeUpdate();
                }
            }

            loadRestaurants();
            loadMenuItems();
            setStatus("Sample data inserted!");
        } catch (SQLException e) { setStatus("Seed error: " + e.getMessage()); }
    }
}
