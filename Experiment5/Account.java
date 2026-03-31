public class Account {

    protected String acctNo;
    private String createdOn;
    private String acctType;
    private boolean isActive;
    private double balance;
    private String pin;

    public Account(String acctNo, String createdOn, String acctType, double initialBalance, String initialPin) {
        this.acctNo = acctNo;
        this.createdOn = createdOn;
        this.acctType = acctType;
        this.isActive = true;
        this.balance = initialBalance;
        this.pin = initialPin;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public double getBalance() {
        return balance;
    }

    public void debit(double amount) {
        if (!isActive) {
            System.out.println("Transaction failed: Account " + acctNo + " is inactive or closed.");
            return;
        }
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println(acctNo + ": Debited Rs." + amount + ". New Balance: Rs." + balance);
        } else {
            System.out.println(acctNo + ": Debit failed. Insufficient funds or invalid amount.");
        }
    }

    public void credit(double amount) {
        if (!isActive) {
            System.out.println("Transaction failed: Account " + acctNo + " is inactive or closed.");
            return;
        }
        if (amount > 0) {
            balance += amount;
            System.out.println(acctNo + ": Credited Rs." + amount + ". New Balance: Rs." + balance);
        } else {
            System.out.println(acctNo + ": Credit failed. Invalid amount.");
        }
    }

    public double checkBalance() {
        System.out.println(acctNo + " Current Balance: Rs." + balance);
        return balance;
    }

    public void changePIN(String oldPin, String newPin) {
        if (this.pin.equals(oldPin)) {
            this.pin = newPin;
            System.out.println("PIN updated successfully for account: " + acctNo);
        } else {
            System.out.println("Authentication failed: Old PIN is incorrect.");
        }
    }
}
