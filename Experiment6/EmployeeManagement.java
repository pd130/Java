import java.util.*;
public class EmployeeManagement {
    public static void main(String[] args) {

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new FullTimeEmployee(
                "Amit", "PAN123", "2022-01-10",
                "Developer", 101,
                600000, 50000, "SWE"));

        employees.add(new FullTimeEmployee(
                "Neha", "PAN456", "2021-06-15",
                "HR", 102,
                500000, 0, "HR"));

        employees.add(new ContractEmployee(
                "Ravi", "PAN789", "2023-03-01",
                "Consultant", 103,
                120, 500));

        employees.add(new Manager(
                "Priya", "PAN321", "2020-09-20",
                "Manager", 104,
                800000, 100000,
                50000, 30000));
        for (Employee emp : employees) {
            emp.display();
            System.out.println("CTC: " + emp.calcCTC());
            System.out.println("----------------------");
        }
    }
}