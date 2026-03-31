public class LoanAccount extends Account {

    private String loanAcctId;
    private double loanAmt;
    private double interestRate;
    private int tenure;
    private String loanType;
    private double rateOfPenalty;
    private Customer customer;
    private double outstandingPrincipal;
    private double emiAmount;

    public LoanAccount(String acctNo, String createdOn, String acctType, String pin,
                       String loanAcctId, double loanAmt, double interestRate, int tenure,
                       String loanType, double rateOfPenalty, Customer customer) {
        super(acctNo, createdOn, acctType, 0.0, pin);
        this.loanAcctId = loanAcctId;
        this.loanAmt = loanAmt;
        this.interestRate = interestRate;
        this.tenure = tenure;
        this.loanType = loanType;
        this.rateOfPenalty = rateOfPenalty;
        this.customer = customer;
        this.outstandingPrincipal = loanAmt;
        this.emiAmount = calcEMI();
    }

    public double getOutstandingPrincipal() {
        return outstandingPrincipal;
    }

    public double getEmiAmount() {
        return emiAmount;
    }

    @Override
    public void debit(double amount) {
        System.out.println(loanAcctId + " [Loan Account]: Withdrawals are not permitted.");
    }

    @Override
    public void credit(double amount) {
        payPrincipal(amount);
    }

    @Override
    public double checkBalance() {
        System.out.println("[Loan Account " + loanAcctId + "] Outstanding Principal: Rs." + outstandingPrincipal + " | EMI: Rs." + emiAmount);
        return outstandingPrincipal;
    }

    public double calcEMI() {
        double monthlyRate = (interestRate / 100) / 12;
        double emi = (loanAmt * monthlyRate * Math.pow(1 + monthlyRate, tenure))
                / (Math.pow(1 + monthlyRate, tenure) - 1);
        return Math.round(emi * 100.0) / 100.0;
    }

    public void payEMI() {
        if (outstandingPrincipal <= 0) {
            System.out.println(loanAcctId + ": Loan is already fully paid off!");
            return;
        }
        System.out.println("Processing EMI Payment of Rs." + emiAmount + " for account " + loanAcctId);
        outstandingPrincipal -= emiAmount;
        if (outstandingPrincipal < 0) outstandingPrincipal = 0;
        System.out.println("EMI Paid. Remaining Principal: Rs." + outstandingPrincipal);
    }

    public void payPrincipal(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return;
        }
        if (outstandingPrincipal <= 0) {
            System.out.println(loanAcctId + ": Loan is already fully paid off!");
            return;
        }
        outstandingPrincipal -= amount;
        if (outstandingPrincipal < 0) outstandingPrincipal = 0;
        System.out.println(loanAcctId + ": Payment of Rs." + amount + " successful. Remaining Principal: Rs." + outstandingPrincipal);
    }
}
