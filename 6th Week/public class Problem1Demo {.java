public class Problem1Demo {

    // Base Class: Shared Foundation
    public static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null) {
                throw new IllegalArgumentException("construction rejected");
            }
            String trimmedBib = bibNumber.trim();
            if (trimmedBib.length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            if (entryFee < 0) {
                throw new IllegalArgumentException("Entry fee cannot be negative");
            }

            this.bibNumber = trimmedBib;
            this.entryFee = entryFee;
            this.amountPaid = 0.0;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.amountPaid += amount;
            }
        }

        public double getBalanceDue() {
            return Math.max(0.0, entryFee - amountPaid);
        }

        public String getBibNumber() {
            return bibNumber;
        }

        public double getEntryFee() {
            return entryFee;
        }
    }

    // Single Inheritance Specialization
    public static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee); // Forwards shared validation and state setup
            this.category = category;
        }

        public String getCategory() {
            return category;
        }
    }

    // Batch Registration Processor
    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        if (bibNumbers != null) {
            for (String bib : bibNumbers) {
                try {
                    // Relies on RaceEntry constructor validation gate
                    new RaceEntry(bib, entryFee);
                    registered++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 1 Tests ===");

        // Test rejection
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("Construction test (\"B1\"): " + e.getMessage());
        }

        // Test payment & balance
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println("Balance due: " + r.getBalanceDue());

        // Test batch registration
        String[] batch = {"BIB1", "B1", "BIB2"};
        System.out.println(registerBatch(batch, 80));
    }
}