public class SavingsAccount extends Account {

    private String savingsAcctId;
    private double minBalance;
    private double withdrawalLimit;
    private double interestRate;
    private int txnLimit;
    private int currentTxnCount;

    public SavingsAccount(String acctNo, String createdOn, String acctType, double initialBalance, String pin,
                          String savingsAcctId, double minBalance, double withdrawalLimit, double interestRate, int txnLimit) {
        super(acctNo, createdOn, acctType, initialBalance, pin);
        this.savingsAcctId = savingsAcctId;
        this.minBalance = minBalance;
        this.withdrawalLimit = withdrawalLimit;
        this.interestRate = interestRate;
        this.txnLimit = txnLimit;
        this.currentTxnCount = 0;
    }

    @Override
    public void debit(double amount) {
        if (amount > withdrawalLimit) {
            System.out.println(savingsAcctId + " Transaction Failed: Amount exceeds withdrawal limit of Rs." + withdrawalLimit);
            return;
        }

        double txnFee = calcTxnFee();
        double totalDeduction = amount + txnFee;

        if ((getBalance() - totalDeduction) < minBalance) {
            System.out.println(savingsAcctId + " Transaction Failed: Minimum balance of Rs." + minBalance + " must be maintained.");
            return;
        }

        super.debit(totalDeduction);
        currentTxnCount++;

        if (txnFee > 0) {
            System.out.println("Note: A transaction fee of Rs." + txnFee + " was applied.");
        }
    }

    @Override
    public void credit(double amount) {
        super.credit(amount);
        currentTxnCount++;
    }

    @Override
    public double checkBalance() {
        System.out.print("[Savings Account " + savingsAcctId + "] ");
        return super.checkBalance();
    }

    public double calcTxnFee() {
        if (currentTxnCount >= txnLimit) {
            return 20.0;
        }
        return 0.0;
    }
}
