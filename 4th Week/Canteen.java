import java.util.Arrays;

public class Canteen implements Comparable<Canteen> {
    private final String canteenCode;
    private final String canteenName;
    private final int trustScore;

    private static final int DEFAULT_TRUST_SCORE = 3;

    // Master Constructor resolving field/parameter name collisions using 'this'
    public Canteen(String canteenCode, String canteenName, int trustScore) {
        if (canteenCode == null || canteenCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Canteen code cannot be empty.");
        }
        if (canteenName == null || canteenName.trim().isEmpty()) {
            throw new IllegalArgumentException("Canteen name cannot be empty.");
        }
        this.canteenCode = canteenCode.trim();
        this.canteenName = canteenName.trim();
        this.trustScore = trustScore;
    }

    // Constructor Chaining with default trust score
    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, DEFAULT_TRUST_SCORE);
    }

    public String getCanteenCode() { return canteenCode; }
    public String getCanteenName() { return canteenName; }
    public int getTrustScore() { return trustScore; }

    @Override
    public int compareTo(Canteen other) {
        if (other == null) return -1;

        // 1. Primary Sort: Higher trust score ranks first
        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }

        // 2. Secondary Sort: Case-insensitive canteen code comparison
        int codeCompare = this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeCompare != 0) {
            return codeCompare;
        }

        // 3. Tertiary Sort: Canteen name length comparison
        return Integer.compare(this.canteenName.length(), other.canteenName.length());
    }

    // Stable insertion sort to preserve deterministic ranking
    public static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null || canteens.length <= 1) {
            return canteens;
        }

        Canteen[] sorted = Arrays.copyOf(canteens, canteens.length);
        for (int i = 1; i < sorted.length; i++) {
            Canteen key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].compareTo(key) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 3: Canteen Trust-Score Ranking Engine ===");

        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats") // Uses 2-arg constructor, trustScore = 3
        };

        Canteen[] ranked = rankCanteens(canteens);
        String[] codes = new String[ranked.length];
        for (int i = 0; i < ranked.length; i++) {
            codes[i] = ranked[i].getCanteenCode();
        }

        System.out.println("Ranked Canteen Codes: " + Arrays.toString(codes));
    }
}