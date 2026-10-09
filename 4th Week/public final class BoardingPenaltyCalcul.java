public final class BoardingPenaltyCalculator {

    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Minimum penalty percent cannot be negative.");
        }
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0) {
            throw new IllegalArgumentException("Ticket fare cannot be negative.");
        }
        if (minutesLate < 0) {
            throw new IllegalArgumentException("Minutes late cannot be negative.");
        }

        // On-time passengers pay zero penalty
        if (minutesLate == 0) {
            return 0.0;
        }

        double tieredPenalty = 0.0;

        // Tier 1: Minutes 1–5 (0.5% per minute)
        int tier1Minutes = Math.min(minutesLate, 5);
        tieredPenalty += tier1Minutes * (0.005 * ticketFare);

        // Tier 2: Minutes 6–15 (1.0% per minute)
        if (minutesLate > 5) {
            int tier2Minutes = Math.min(minutesLate - 5, 10);
            tieredPenalty += tier2Minutes * (0.01 * ticketFare);
        }

        // Tier 3: Minute 16 onward (2.0% per minute)
        if (minutesLate > 15) {
            int tier3Minutes = minutesLate - 15;
            tieredPenalty += tier3Minutes * (0.02 * ticketFare);
        }

        // Flat-fee floor calculation
        double minimumFloor = (minimumPenaltyPercent / 100.0) * ticketFare;

        return Math.max(tieredPenalty, minimumFloor);
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 4: Tiered Boarding Penalty Calculator ===");

        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1.0); // 1.0% minimum floor

        System.out.println("Fare: 1000, Late: 0  -> Rs " + calc.calculatePenalty(1000, 0));
        System.out.println("Fare: 1000, Late: 1  -> Rs " + calc.calculatePenalty(1000, 1));
        System.out.println("Fare: 1000, Late: 16 -> Rs " + calc.calculatePenalty(1000, 16));
    }
}