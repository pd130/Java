import java.util.ArrayList;

public class Customer {

    private String custId;
    private String aadharNo;
    private String panNo;
    private String name;
    private String dob;
    private String address;
    private String phoneNo;
    private ArrayList<Account> accounts;

    public Customer(String custId, String aadharNo, String panNo, String name, String dob, String address, String phoneNo) {
        this.custId = custId;
        this.aadharNo = aadharNo;
        this.panNo = panNo;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phoneNo = phoneNo;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public String getCustId() {
        return custId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void changePIN(String acctNo, String oldPin, String newPin) {
        for (Account acc : accounts) {
            if (acc.getAcctNo().equalsIgnoreCase(acctNo)) {
                acc.changePIN(oldPin, newPin);
                return;
            }
        }
        System.out.println("Account " + acctNo + " not found for this customer.");
    }

    public void checkLoanEligibility() {
        System.out.println("Loan Eligibility Check for " + name);

        double totalSavings = 0;
        for (Account acc : accounts) {
            if (acc instanceof SavingsAccount) {
                totalSavings += acc.getBalance();
            }
        }

        double totalOutstanding = 0;
        for (Account acc : accounts) {
            if (acc instanceof LoanAccount) {
                totalOutstanding += ((LoanAccount) acc).getOutstandingPrincipal();
            }
        }

        System.out.println("Total Savings Balance: Rs." + totalSavings);
        System.out.println("Total Outstanding Loans: Rs." + totalOutstanding);

        if (totalOutstanding == 0 && totalSavings >= 1000) {
            System.out.println("Result: ELIGIBLE for a new loan.");
        } else if (totalOutstanding > 0) {
            System.out.println("Result: NOT ELIGIBLE - Please clear existing loans first.");
        } else {
            System.out.println("Result: NOT ELIGIBLE - Insufficient savings balance.");
        }
    }
}
