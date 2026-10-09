public class DeliveryAccount {
    private final String studentId;
    private final double orderValue;

    private static final SurgeFeeCalculator SURGE_CALCULATOR;

    // Static initialization block for class-level setup
    static {
        SURGE_CALCULATOR = new SurgeFeeCalculator(1.0);
    }

    public DeliveryAccount(String studentId, double orderValue) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        if (orderValue < 0) {
            throw new IllegalArgumentException("Order value cannot be negative.");
        }
        this.studentId = studentId.trim();
        this.orderValue = orderValue;
    }

    // Provisional constructor chaining to full constructor
    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public String getStudentId() { return studentId; }
    public double getOrderValue() { return orderValue; }

    public final double calculateSurgeFee(int delayMinutes) {
        return SURGE_CALCULATOR.calculateSurgeFee(this.orderValue, delayMinutes);
    }

    // Subclass for Premium Member Accounts
    public static class PremiumDeliveryAccount extends DeliveryAccount {
        public PremiumDeliveryAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        public PremiumDeliveryAccount(String studentId) {
            super(studentId);
        }
    }

    // Batch processing engine with complete null-safety
    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) {
            System.out.println("Error: Input arrays cannot be null.");
            return;
        }

        // Justification for length mismatch handling:
        // Operations integrity requires that every account processed has a complete 
        // triplet of data. Truncating to the minimum length prevents index mismatch corruption.
        int batchLength = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));

        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotalSurgeFees = 0.0;

        for (int i = 0; i < batchLength; i++) {
            DeliveryAccount account = accounts[i];

            // Defensive null check prevents NullPointerException runtime crashes
            if (account == null) {
                nullSkipped++;
                continue;
            }

            int delayMinutes = delayMinutesArray[i];
            double surgeFee = account.calculateSurgeFee(delayMinutes);

            // Use instanceof dispatch to customize settlement logic for premium members
            if (account instanceof PremiumDeliveryAccount) {
                premiumCount++;
                // Premium members get a 50% discount on final surge fees
                surgeFee *= 0.5;
            } else {
                regularCount++;
            }

            grandTotalSurgeFees += surgeFee;
            processed++;
        }

        System.out.printf("%d processed | %d null skipped | %d premium | %d regular | grand total surge fees = Rs %.2f%n",
                processed, nullSkipped, premiumCount, regularCount, grandTotalSurgeFees);
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 5: Nightly Multi-Kitchen Reconciliation Engine ===");

        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        processBatch(accounts, amounts, delayMinutesArray);
    }
}