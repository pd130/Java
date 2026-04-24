import java.sql.*;
import java.util.Scanner;

public class JdbcApplication {

    static String URL = "jdbc:mysql://localhost:3306/college_db?useSSL=false&serverTimezone=UTC";
    static String USER = "root";
    static String PASS = "sit123";

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    static void createStudentTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students ("
                + "roll_no INT PRIMARY KEY, "
                + "name VARCHAR(50), "
                + "branch VARCHAR(30), "
                + "marks DOUBLE)";
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            System.out.println("Students table ready.");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void insertStudent(int roll, String name, String branch, double marks) {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?)";
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roll);
            ps.setString(2, name);
            ps.setString(3, branch);
            ps.setDouble(4, marks);
            ps.executeUpdate();
            System.out.println("Student record inserted.");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void fetchStudents() {
        String sql = "SELECT * FROM students ORDER BY roll_no";
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("+------+---------------------+-----------+--------+");
            System.out.println("| Roll | Name                | Branch    | Marks  |");
            System.out.println("+------+---------------------+-----------+--------+");
            boolean any = false;
            while (rs.next()) {
                System.out.printf("| %-4d | %-19s | %-9s | %-6.1f |%n",
                        rs.getInt("roll_no"),
                        rs.getString("name"),
                        rs.getString("branch"),
                        rs.getDouble("marks"));
                any = true;
            }
            if (!any) {
                System.out.println("No records found.");
            }
            System.out.println("+------+---------------------+-----------+--------+");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void updateStudent(int roll, double newMarks) {
        String sql = "UPDATE students SET marks=? WHERE roll_no=?";
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, newMarks);
            ps.setInt(2, roll);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student record updated.");
            } else {
                System.out.println("Roll No not found.");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteStudent(int roll) {
        String sql = "DELETE FROM students WHERE roll_no=?";
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roll);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student record deleted.");
            } else {
                System.out.println("Roll No not found.");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void createRestaurantTables() {
        String restaurantSQL = "CREATE TABLE IF NOT EXISTS Restaurant ("
                + "Id INT PRIMARY KEY, "
                + "Name VARCHAR(60), "
                + "Address VARCHAR(100))";

        String menuItemSQL = "CREATE TABLE IF NOT EXISTS MenuItem ("
                + "Id INT PRIMARY KEY, "
                + "Name VARCHAR(60), "
                + "Price DOUBLE, "
                + "ResId INT, "
                + "FOREIGN KEY (ResId) REFERENCES Restaurant(Id) ON DELETE CASCADE)";

        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(restaurantSQL);
            System.out.println("Restaurant table ready.");
            st.executeUpdate(menuItemSQL);
            System.out.println("MenuItem table ready.");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void insertRestaurantRecords() {
        String sql = "INSERT IGNORE INTO Restaurant (Id, Name, Address) VALUES (?, ?, ?)";

        Object[][] data = {
            {1,  "Cafe Java",       "12 MG Road, Pune"},
            {2,  "Pizza Palace",    "44 FC Road, Pune"},
            {3,  "Burger Barn",     "7 Baner Road, Pune"},
            {4,  "The Curry House", "22 Camp Area, Pune"},
            {5,  "Noodle Nest",     "5 Kothrud Lane, Pune"},
            {6,  "Spice Garden",    "18 Shivaji Nagar, Pune"},
            {7,  "Pasta Point",     "3 Viman Nagar, Pune"},
            {8,  "Dosa Delight",    "9 Hadapsar, Pune"},
            {9,  "Tandoor Town",    "31 Wakad, Pune"},
            {10, "Sushi Stop",      "15 Hinjewadi, Pune"}
        };

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            for (Object[] row : data) {
                ps.setInt(1, (int) row[0]);
                ps.setString(2, (String) row[1]);
                ps.setString(3, (String) row[2]);
                ps.executeUpdate();
            }
            System.out.println("10 Restaurant records inserted.");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void insertMenuItemRecords() {
        String sql = "INSERT IGNORE INTO MenuItem (Id, Name, Price, ResId) VALUES (?, ?, ?, ?)";

        Object[][] data = {
            {1,  "Espresso",         80.0,  1},
            {2,  "Cappuccino",       90.0,  1},
            {3,  "Pasta Arrabbiata", 150.0, 1},
            {4,  "Paneer Pizza",     250.0, 2},
            {5,  "Pepperoni Pizza",  300.0, 2},
            {6,  "Burger Classic",   120.0, 3},
            {7,  "Masala Chai",      50.0,  4},
            {8,  "Poha",             60.0,  8},
            {9,  "Noodle Soup",      95.0,  5},
            {10, "Chicken Tikka",    180.0, 6}
        };

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            for (Object[] row : data) {
                ps.setInt(1, (int) row[0]);
                ps.setString(2, (String) row[1]);
                ps.setDouble(3, (double) row[2]);
                ps.setInt(4, (int) row[3]);
                ps.executeUpdate();
            }
            System.out.println("10 MenuItem records inserted.");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void printMenuItemResultSet(ResultSet rs) throws SQLException {
        System.out.println("+------+----------------------+---------+-------+");
        System.out.println("| Id   | Name                 | Price   | ResId |");
        System.out.println("+------+----------------------+---------+-------+");
        boolean any = false;
        while (rs.next()) {
            System.out.printf("| %-4d | %-20s | %-7.2f | %-5d |%n",
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getDouble("Price"),
                    rs.getInt("ResId"));
            any = true;
        }
        if (!any) {
            System.out.println("No records matched.");
        }
        System.out.println("+------+----------------------+---------+-------+");
    }

    static void selectMenuItemsBudget() {
        String sql = "SELECT * FROM MenuItem WHERE Price <= 100 ORDER BY Price";
        System.out.println("SELECT: MenuItem where Price <= 100");
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            printMenuItemResultSet(rs);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void selectMenuItemsByCafeJava() {
        String sql = "SELECT m.Id, m.Name, m.Price, m.ResId "
                + "FROM MenuItem m "
                + "JOIN Restaurant r ON m.ResId = r.Id "
                + "WHERE r.Name = 'Cafe Java'";
        System.out.println("SELECT: MenuItem available in Cafe Java");
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            printMenuItemResultSet(rs);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void updateMenuItemsPriceTo200() {
        String sql = "UPDATE MenuItem SET Price = 200 WHERE Price <= 100";
        System.out.println("UPDATE: Set Price = 200 where Price <= 100");
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            int rows = ps.executeUpdate();
            System.out.println(rows + " record(s) updated to Price = 200.");

            String verify = "SELECT * FROM MenuItem WHERE Price = 200 ORDER BY Id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(verify);
            System.out.println("Updated records:");
            printMenuItemResultSet(rs);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteMenuItemsStartingWithP() {
        System.out.println("DELETE: MenuItem where Name starts with P");
        try {
            Connection con = getConnection();

            String selectSql = "SELECT * FROM MenuItem WHERE Name LIKE 'P%'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSql);
            System.out.println("Records to be deleted:");
            printMenuItemResultSet(rs);

            String deleteSql = "DELETE FROM MenuItem WHERE Name LIKE 'P%'";
            PreparedStatement ps = con.prepareStatement(deleteSql);
            int rows = ps.executeUpdate();
            System.out.println(rows + " record(s) deleted.");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void fetchRestaurants() {
        String sql = "SELECT * FROM Restaurant ORDER BY Id";
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("+----+----------------------+------------------------------+");
            System.out.println("| Id | Name                 | Address                      |");
            System.out.println("+----+----------------------+------------------------------+");
            boolean any = false;
            while (rs.next()) {
                System.out.printf("| %-2d | %-20s | %-28s |%n",
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Address"));
                any = true;
            }
            if (!any) {
                System.out.println("No records found.");
            }
            System.out.println("+----+----------------------+------------------------------+");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void fetchMenuItems() {
        String sql = "SELECT * FROM MenuItem ORDER BY Id";
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            printMenuItemResultSet(rs);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        System.out.println("=== Setting up tables ===");
        createStudentTable();
        createRestaurantTables();

        System.out.println("\n=== Inserting Records ===");
        insertRestaurantRecords();
        insertMenuItemRecords();

        System.out.println("\n=== SELECT Operations ===");
        selectMenuItemsBudget();
        selectMenuItemsByCafeJava();

        System.out.println("\n=== UPDATE Operation ===");
        updateMenuItemsPriceTo200();

        System.out.println("\n=== DELETE Operation ===");
        deleteMenuItemsStartingWithP();

        System.out.println("\n=== Final State: Restaurant Table ===");
        fetchRestaurants();

        System.out.println("\n=== Final State: MenuItem Table ===");
        fetchMenuItems();

        Scanner sc = new Scanner(System.in);
        System.out.println("\n=== JDBC Interactive Menu ===");

        while (true) {
            System.out.println("\n--- Students ---");
            System.out.println("1.Insert Student  2.View Students  3.Update Marks  4.Delete Student");
            System.out.println("--- Restaurant & Menu ---");
            System.out.println("5.View Restaurants  6.View MenuItems  7.MenuItems <= price  8.MenuItems by Restaurant");
            System.out.println("0.Exit");
            System.out.print("Choice: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }

            try {
                if (ch == 1) {
                    System.out.print("Roll: ");
                    int r = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Name: ");
                    String n = sc.nextLine().trim();
                    System.out.print("Branch: ");
                    String br = sc.nextLine().trim();
                    System.out.print("Marks: ");
                    double m = Double.parseDouble(sc.nextLine().trim());
                    insertStudent(r, n, br, m);

                } else if (ch == 2) {
                    fetchStudents();

                } else if (ch == 3) {
                    System.out.print("Roll to update: ");
                    int ur = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("New Marks: ");
                    double um = Double.parseDouble(sc.nextLine().trim());
                    updateStudent(ur, um);

                } else if (ch == 4) {
                    System.out.print("Roll to delete: ");
                    deleteStudent(Integer.parseInt(sc.nextLine().trim()));

                } else if (ch == 5) {
                    fetchRestaurants();

                } else if (ch == 6) {
                    fetchMenuItems();

                } else if (ch == 7) {
                    System.out.print("Max price: ");
                    double maxP = Double.parseDouble(sc.nextLine().trim());
                    String budgetSQL = "SELECT * FROM MenuItem WHERE Price <= ? ORDER BY Price";
                    Connection con = getConnection();
                    PreparedStatement ps = con.prepareStatement(budgetSQL);
                    ps.setDouble(1, maxP);
                    ResultSet rs = ps.executeQuery();
                    System.out.println("MenuItems with Price <= " + maxP + ":");
                    printMenuItemResultSet(rs);
                    con.close();

                } else if (ch == 8) {
                    System.out.print("Restaurant name: ");
                    String resName = sc.nextLine().trim();
                    String joinSQL = "SELECT m.Id, m.Name, m.Price, m.ResId "
                            + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id "
                            + "WHERE r.Name = ?";
                    Connection con = getConnection();
                    PreparedStatement ps = con.prepareStatement(joinSQL);
                    ps.setString(1, resName);
                    ResultSet rs = ps.executeQuery();
                    System.out.println("MenuItems in " + resName + ":");
                    printMenuItemResultSet(rs);
                    con.close();

                } else if (ch == 0) {
                    System.out.println("Goodbye!");
                    sc.close();
                    return;

                } else {
                    System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid numeric input.");
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
