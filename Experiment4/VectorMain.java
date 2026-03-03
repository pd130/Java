import java.util.Scanner;

public class VectorMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter dimension of Vector 1 (2 or 3): ");
            int dim1 = sc.nextInt();
            double[] comp1 = new double[dim1];
            System.out.println("Enter " + dim1 + " components of Vector 1:");
            for (int i = 0; i < dim1; i++) {
                System.out.print("  Component " + (i + 1) + ": ");
                comp1[i] = sc.nextDouble();
            }
            Vector A = new Vector(comp1);

            System.out.print("Enter dimension of Vector 2(2 or 3): ");
            int dim2 = sc.nextInt();
            double[] comp2 = new double[dim2];
            System.out.println("Enter " + dim2 + " components of Vector 2:");
            for (int i = 0; i < dim2; i++) {
                System.out.print("  Component " + (i + 1) + ": ");
                comp2[i] = sc.nextDouble();
            }
            Vector B = new Vector(comp2);

            boolean running = true;
            while (running) {
                System.out.println("\n--- Vector Operations ---");
                System.out.println("1. Add");
                System.out.println("2. Subtract (A - B)");
                System.out.println("3. Dot Product");
                System.out.println("4. Display Vectors");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Vector 1 + Vector 2 = ");
                        Vector sum = A.add(B);
                        sum.display();
                        break;
                    case 2:
                        System.out.print("Vector 1 - Vector 2 = ");
                        Vector diff = A.subtract(B);
                        diff.display();
                        break;
                    case 3:
                        double dot = A.dotProduct(B);
                        System.out.println("Vector 1 . Vector 2 = " + dot);
                        break;
                    case 4:
                        System.out.print("Vector 1 = ");
                        A.display();
                        System.out.print("Vector 2 = ");
                        B.display();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }

        } catch (VectorException e) {
            System.out.println("VectorException: " + e.getMessage());
        } 
    }
}
