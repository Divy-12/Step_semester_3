import java.util.Arrays;

public class Problem3Demo {

    public static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;
        private double[] lateFeeHistory;
        private int feeCount;

        public RaceEntry(String bibNumber, double entryFee) {
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.amountPaid = 0.0;
            this.lateFeeHistory = new double[10];
            this.feeCount = 0;
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
            double totalLateFees = 0.0;
            for (int i = 0; i < feeCount; i++) {
                totalLateFees += lateFeeHistory[i];
            }
            return Math.max(0.0, entryFee + totalLateFees - amountPaid);
        }

        // Defensive copy guarantees encapsulates internal array
        public double[] getLateFeeHistory() {
            double[] copy = new double[feeCount];
            System.arraycopy(lateFeeHistory, 0, copy, 0, feeCount);
            return copy;
        }
    }

    public static class RunnerEntry extends RaceEntry {
        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
        }

        @Override
        protected void applyLateFee(double amount) {
            // Reuses parent penalty & audit log mechanisms with doubled fee
            super.applyLateFee(amount * 2.0);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 3 Tests ===");

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20); // Penalty doubled to 40
        System.out.println("Balance due after penalty: " + r.getBalanceDue());

        double[] history = r.getLateFeeHistory();
        System.out.println("Recorded history: " + Arrays.toString(history));

        // External array mutation attempt
        history[0] = 999;
        System.out.println("History after external array tampering: " + Arrays.toString(r.getLateFeeHistory()));
    }
}