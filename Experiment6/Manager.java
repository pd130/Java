class Manager extends FullTimeEmployee {
    private double TA;
    private double eduAllowance;

    public Manager(String name, String PANNo, String joiningDate,
                   String designation, int empId,
                   double baseSalary, double perfBonus,
                   double TA, double eduAllowance) {
        super(name, PANNo, joiningDate, designation, empId,
                baseSalary, perfBonus, "Manager");
        this.TA = TA;
        this.eduAllowance = eduAllowance;
    }

    @Override
    public double calcCTC() {
        return baseSalary + perfBonus + TA + eduAllowance;
    }
}
