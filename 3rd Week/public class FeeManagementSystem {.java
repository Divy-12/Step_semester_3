public class FeeManagementSystem {

    // Base FeeAccount class remains untouched/unmodified
    static class FeeAccount {
        private String regNo;
        private double totalFee;
        private double amountPaid;

        public FeeAccount(String regNo, double totalFee, double amountPaid) {
            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = amountPaid;
        }

        public void pay(double amount) {
            if (amount <= 0) {
                System.out.println("Payment rejected: Amount must be positive.");
                return;
            }
            this.amountPaid += amount;
        }

        public double getDue() {
            return totalFee - amountPaid;
        }
    }

    // Subclass 1
    static class HostelFeeAccount extends FeeAccount {
        public HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
            super(regNo, totalFee, amountPaid);
        }

        public void payInTwoInstallments(double amount) {
            pay(amount / 2);
            pay(amount / 2);
        }
    }

    // Subclass 2
    static class ScholarshipFeeAccount extends FeeAccount {
        private double scholarshipPercent; // 0 to 100

        public ScholarshipFeeAccount(String regNo, double totalFee, double amountPaid, double scholarshipPercent) {
            super(regNo, totalFee, amountPaid);
            this.scholarshipPercent = scholarshipPercent;
        }

        public double effectiveDue() {
            double rawDue = getDue();
            return rawDue * (1.0 - (scholarshipPercent / 100.0));
        }
    }

    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("RA2301", 150000, 150000);
        HostelFeeAccount hostel = new HostelFeeAccount("RA2302", 200000, 60000);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA2303", 180000, 0, 20);

        FeeAccount[] accounts = { plain, hostel, scholarship };

        for (FeeAccount acc : accounts) {
            if (acc instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount sAcc = (ScholarshipFeeAccount) acc;
                System.out.println("Scholarship account effective due: Rs " + sAcc.effectiveDue());
            } else if (acc instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + acc.getDue());
            } else {
                System.out.println("Plain account due: Rs " + acc.getDue());
            }
        }
    }
}