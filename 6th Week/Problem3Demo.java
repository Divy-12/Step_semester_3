import java.util.Arrays;

public class Problem3Demo {

    public static class EventTicket {
        private final String attendeeId;
        private final double basePrice;
        private double amountPaid;
        private double[] lateFeeHistory;
        private int feeCount;

        public EventTicket(String attendeeId, double basePrice) {
            this.attendeeId = attendeeId;
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
            this.lateFeeHistory = new double[10];
            this.feeCount = 0;
        }

        public EventTicket(double basePrice) {
            this("STU0000", basePrice);
        }

        public void pay(double amount) {
            if (amount > 0) this.amountPaid += amount;
        }

        protected void applyLateFee(double amount) {
            if (amount > 0) {
                recordFee(amount);
            }
        }

        private void recordFee(double amount) {
            if (feeCount < lateFeeHistory.length) {
                lateFeeHistory[feeCount++] = amount;
            }
        }

        public double getBalanceDue() {
            double totalFees = 0.0;
            for (int i = 0; i < feeCount; i++) {
                totalFees += lateFeeHistory[i];
            }
            return Math.max(0.0, basePrice + totalFees - amountPaid);
        }

        // Defensive copy on return
        public double[] getLateFeeHistory() {
            double[] copy = new double[feeCount];
            System.arraycopy(lateFeeHistory, 0, copy, 0, feeCount);
            return copy;
        }
    }

    public static class WorkshopTicket extends EventTicket {

        public WorkshopTicket(double basePrice) {
            super(basePrice);
        }

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
        }

        @Override
        protected void applyLateFee(double amount) {
            // Reuses parent deduction and array-recording logic with doubled penalty
            super.applyLateFee(amount * 2.0);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 3 Tests ===");

        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println("Balance due after $100 penalty (doubled to $200): " + w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println("Recorded history: " + Arrays.toString(history));

        // Tamper test
        history[0] = 999;
        System.out.println("History after attempting external array modification: " + Arrays.toString(w.getLateFeeHistory()));
    }
}