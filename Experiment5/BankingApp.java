import java.util.ArrayList;
import java.util.Scanner;

public class BankingApp {

    private static ArrayList<Customer> bankCustomers = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);


    private static String pendingLoanCustomerId = null;
    private static double pendingLoanAmount     = 0;
    private static int    pendingLoanTenure     = 0;

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=================================");
            System.out.println("      MAIN BANKING MENU          ");
            System.out.println("=================================");
            System.out.println("1. Create Account");
            System.out.println("2. Credit");
            System.out.println("3. Debit");
            System.out.println("4. Loan Request");
            System.out.println("5. Loan Approval");
            System.out.println("6. Pay EMI");
            System.out.println("7. Check Balance");
            System.out.println("8. Change PIN");
            System.out.println("9. Exit");
            System.out.print("Select an option (1-9): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: createAccountMenu();  break;
                case 2: creditMenu();         break;
                case 3: debitMenu();          break;
                case 4: loanRequestMenu();    break;
                case 5: loanApprovalMenu();   break;
                case 6: payEMIMenu();         break;
                case 7: checkBalanceMenu();   break;
                case 8: changePINMenu();      break;
                case 9:
                    System.out.println("Exiting banking system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }


    private static void createAccountMenu() {
        System.out.println("\n--- 1. Create Account ---");

        System.out.print("Enter Customer ID: ");
        String custId = scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNo = scanner.nextLine();
        System.out.print("Enter Account Number (e.g., SA-101): ");
        String acctNo = scanner.nextLine();
        System.out.print("Set a 4-digit PIN: ");
        String pin = scanner.nextLine();
        System.out.print("Enter Initial Deposit Amount: Rs.");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine();

        Customer newCustomer = new Customer(custId, "000000000000", "XXXXX0000X", name, "01/01/1990", "System Default", phoneNo);
        SavingsAccount sa = new SavingsAccount(acctNo, "Today", "individual", initialDeposit, pin,
                "SAV-" + acctNo, 1000.0, 50000.0, 4.0, 5);
        newCustomer.addAccount(sa);
        bankCustomers.add(newCustomer);
        System.out.println("Success! Savings Account " + acctNo + " created for " + name + ".");
    }


    private static void creditMenu() {
        System.out.println("\n--- 2. Credit ---");
        System.out.print("Enter Account Number: ");
        String acctNo = scanner.nextLine();

        Account account = findAccount(acctNo);
        if (account == null) { System.out.println("Error: Account not found."); return; }

        System.out.print("Enter Amount to Credit: Rs.");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        account.credit(amount);
    }

    private static void debitMenu() {
        System.out.println("\n--- 3. Debit ---");
        System.out.print("Enter Account Number: ");
        String acctNo = scanner.nextLine();

        Account account = findAccount(acctNo);
        if (account == null) { System.out.println("Error: Account not found."); return; }

        System.out.print("Enter Amount to Debit: Rs.");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        account.debit(amount);
    }


    private static void loanRequestMenu() {
        System.out.println("\n--- 4. Loan Request ---");
        System.out.print("Enter Customer ID: ");
        String custId = scanner.nextLine();

        Customer customer = findCustomer(custId);
        if (customer == null) { System.out.println("Error: Customer not found."); return; }

        System.out.print("Enter Loan Amount Required: Rs.");
        double loanAmt = scanner.nextDouble();
        System.out.print("Enter Loan Tenure (in months): ");
        int tenure = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Checking eligibility...");
        customer.checkLoanEligibility();


        System.out.println("\nLoan request of Rs." + loanAmt + " for " + tenure + " months submitted for customer "
                + customer.getName() + " (ID: " + custId + ").");
        System.out.println("Proceed to option 5 (Loan Approval) to approve and activate the loan account.");

       
        pendingLoanCustomerId = custId;
        pendingLoanAmount     = loanAmt;
        pendingLoanTenure     = tenure;
    }

    
    private static void loanApprovalMenu() {
        System.out.println("\n--- 5. Loan Approval ---");

        if (pendingLoanCustomerId == null) {
            System.out.println("No pending loan request found. Please submit a Loan Request first (option 4).");
            return;
        }

        Customer customer = findCustomer(pendingLoanCustomerId);
        if (customer == null) { System.out.println("Error: Customer record missing."); return; }

        System.out.println("Pending request: Customer " + customer.getName()
                + " | Amount: Rs." + pendingLoanAmount
                + " | Tenure: " + pendingLoanTenure + " months");
        System.out.print("Approve this loan? (yes/no): ");
        String decision = scanner.nextLine().trim().toLowerCase();

        if (decision.equals("yes")) {
            System.out.print("Enter Loan Account Number (e.g., LN-101): ");
            String loanAcctNo = scanner.nextLine();
            System.out.print("Set a 4-digit PIN for the loan account: ");
            String pin = scanner.nextLine();

            LoanAccount la = new LoanAccount(loanAcctNo, "Today", "individual", pin,
                    "LOAN-" + loanAcctNo, pendingLoanAmount, 8.0, pendingLoanTenure,
                    "Personal", 12.0, customer);
            customer.addAccount(la);

            System.out.println("Loan APPROVED! Account " + loanAcctNo + " created for " + customer.getName() + ".");
            System.out.printf("Monthly EMI: Rs.%.2f%n", la.getEmiAmount());
        } else {
            System.out.println("Loan request REJECTED for customer " + customer.getName() + ".");
        }


        pendingLoanCustomerId = null;
        pendingLoanAmount     = 0;
        pendingLoanTenure     = 0;
    }


    private static void payEMIMenu() {
        System.out.println("\n--- 6. Pay EMI ---");
        System.out.print("Enter Loan Account Number: ");
        String acctNo = scanner.nextLine();

        Account account = findAccount(acctNo);
        if (account == null) { System.out.println("Error: Account not found."); return; }

        if (account instanceof LoanAccount) {
            ((LoanAccount) account).payEMI();
        } else {
            System.out.println("Error: The specified account is not a Loan Account.");
        }
    }


    private static void checkBalanceMenu() {
        System.out.println("\n--- 7. Check Balance ---");
        System.out.print("Enter Account Number: ");
        String acctNo = scanner.nextLine();

        Account account = findAccount(acctNo);
        if (account != null) {
            account.checkBalance();
        } else {
            System.out.println("Error: Account not found.");
        }
    }

    private static void changePINMenu() {
        System.out.println("\n--- 8. Change PIN ---");
        System.out.print("Enter Customer ID: ");
        String custId = scanner.nextLine();

        Customer customer = findCustomer(custId);
        if (customer == null) { System.out.println("Error: Customer not found."); return; }

        System.out.print("Enter Account Number: ");
        String acctNo = scanner.nextLine();
        System.out.print("Enter Old PIN: ");
        String oldPin = scanner.nextLine();
        System.out.print("Enter New PIN: ");
        String newPin = scanner.nextLine();

        customer.changePIN(acctNo, oldPin, newPin);
    }

    private static Account findAccount(String acctNo) {
        for (Customer customer : bankCustomers) {
            for (Account account : customer.getAccounts()) {
                if (account.getAcctNo() != null && account.getAcctNo().equalsIgnoreCase(acctNo)) {
                    return account;
                }
            }
        }
        return null;
    }

    private static Customer findCustomer(String custId) {
        for (Customer customer : bankCustomers) {
            if (customer.getCustId().equalsIgnoreCase(custId)) {
                return customer;
            }
        }
        return null;
    }

}
