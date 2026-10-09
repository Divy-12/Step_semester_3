// Final class locks the calculation architecture against inheritance modification
public final class SurgeFeeCalculator {

    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge percent cannot be negative.");
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    // Final method locks calculation algorithm against overriding
    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0) {
            throw new IllegalArgumentException("Order value cannot be negative.");
        }
        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Delay minutes cannot be negative.");
        }

        // On-time orders are exempt from surge fees entirely
        if (delayMinutes == 0) {
            return 0.0;
        }

        double tieredSurge = 0.0;

        // Bracket 1: Delay minutes 1–5 (0.5% per minute)
        int tier1Minutes = Math.min(delayMinutes, 5);
        tieredSurge += tier1Minutes * (0.005 * orderValue);

        // Bracket 2: Delay minutes 6–15 (1.0% per minute)
        if (delayMinutes > 5) {
            int tier2Minutes = Math.min(delayMinutes - 5, 10);
            tieredSurge += tier2Minutes * (0.01 * orderValue);
        }

        // Bracket 3: Delay minute 16 onward (2.0% per minute)
        if (delayMinutes > 15) {
            int tier3Minutes = delayMinutes - 15;
            tieredSurge += tier3Minutes * (0.02 * orderValue);
        }

        // Floor calculation based on configured percentage
        double minimumFloor = (minimumSurgePercent / 100.0) * orderValue;

        return Math.max(tieredSurge, minimumFloor);
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 4: Exam-Week Surge Fee Calculator ===");

        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0); // 1% minimum surge floor

        System.out.println("Value: 500, Delay: 0  -> Rs " + calc.calculateSurgeFee(500, 0));
        System.out.println("Value: 500, Delay: 1  -> Rs " + calc.calculateSurgeFee(500, 1));
        System.out.println("Value: 500, Delay: 16 -> Rs " + calc.calculateSurgeFee(500, 16));
    }
}