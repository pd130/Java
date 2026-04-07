class FullTimeEmployee extends Employee {
    protected double baseSalary;
    protected double perfBonus;
    protected String role;

    public FullTimeEmployee(String name, String PANNo, String joiningDate,
                            String designation, int empId,
                            double baseSalary, double perfBonus, String role) {
        super(name, PANNo, joiningDate, designation, empId);
        this.baseSalary = baseSalary;
        this.perfBonus = perfBonus;
        this.role = role;
    }

    @Override
    public double calcCTC() {
        if (role.equalsIgnoreCase("SWE")) {
            return baseSalary + perfBonus;
        } else if (role.equalsIgnoreCase("HR")) {
            double hiringCommission = 10000; // example fixed value
            return baseSalary + hiringCommission;
        }
        return baseSalary;
    }
}