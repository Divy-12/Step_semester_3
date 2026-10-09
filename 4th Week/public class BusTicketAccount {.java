public class BusTicketAccount {
    private final String bookingId;
    private final double ticketFare;

    private static final BoardingPenaltyCalculator PENALTY_CALCULATOR;

    // Static initialization block for class-level setup
    static {
        PENALTY_CALCULATOR = new BoardingPenaltyCalculator(1.0);
    }

    public BusTicketAccount(String bookingId, double ticketFare) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID required.");
        }
        if (ticketFare < 0) {
            throw new IllegalArgumentException("Ticket fare cannot be negative.");
        }
        this.bookingId = bookingId.trim();
        this.ticketFare = ticketFare;
    }

    // Provisional constructor
    public BusTicketAccount(String bookingId) {
        this(bookingId, 1000.0);
    }

    public String getBookingId() { return bookingId; }
    public double getTicketFare() { return ticketFare; }

    public final double calculatePenalty(int minutesLate) {
        return PENALTY_CALCULATOR.calculatePenalty(this.ticketFare, minutesLate);
    }

    // Subclass for Sleeper-Coach Accounts
    public static class SleeperCoachAccount extends BusTicketAccount {
        public SleeperCoachAccount(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        public SleeperCoachAccount(String bookingId) {
            super(bookingId);
        }
    }

    // Batch processing engine
    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null) {
            System.out.println("Error: Input arrays cannot be null.");
            return;
        }

        // Justification for array length mismatch handling:
        // A depot manager cannot safely guess missing values. Truncating to the shortest length
        // guarantees that every processed entry has a complete, corresponding set of inputs.
        int batchLength = Math.min(accounts.length, Math.min(amounts.length, minutesLateArray.length));

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double grandTotalPenalties = 0.0;

        for (int i = 0; i < batchLength; i++) {
            BusTicketAccount account = accounts[i];

            // Defensive null check prevents runtime crashes
            if (account == null) {
                nullSkipped++;
                continue;
            }

            int minutesLate = minutesLateArray[i];
            double penalty = account.calculatePenalty(minutesLate);

            // Use instanceof dispatch to handle polymorphic types
            if (account instanceof SleeperCoachAccount) {
                sleeperCount++;
                // Sleeper coach penalty includes a flat Rs 50 surcharge
                penalty += 50.0;
            } else {
                regularCount++;
            }

            grandTotalPenalties += penalty;
            processed++;
        }

        System.out.printf("%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = Rs %.2f%n",
                processed, nullSkipped, sleeperCount, regularCount, grandTotalPenalties);
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 5: Nightly Fleet Reconciliation Engine ===");

        BusTicketAccount[] accounts = {
            new SleeperCoachAccount("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};

        processBatch(accounts, amounts, minutesLateArray);
    }
}