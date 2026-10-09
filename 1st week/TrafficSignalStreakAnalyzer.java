public class TrafficSignalStreakAnalyzer {

    public static void findLongestStreak(String signalLog) {
        if (signalLog == null || signalLog.isEmpty()) {
            System.out.println("No signal data provided.");
            return;
        }

        char maxChar = signalLog.charAt(0);
        int maxStreak = 1;

        char currentChar = signalLog.charAt(0);
        int currentStreak = 1;

        // Single pass scan to track running consecutive count
        for (int i = 1; i < signalLog.length(); i++) {
            if (signalLog.charAt(i) == currentChar) {
                currentStreak++;
            } else {
                currentChar = signalLog.charAt(i);
                currentStreak = 1;
            }

            if (currentStreak > maxStreak) {
                maxStreak = currentStreak;
                maxChar = currentChar;
            }
        }

        System.out.printf("Longest Streak: '%c' repeated %d times%n", maxChar, maxStreak);
    }

    public static void main(String[] args) {
        System.out.println("=== Traffic Signal Streak Analyzer ===");

        System.out.print("Input: \"RRGGGYRR\" -> ");
        findLongestStreak("RRGGGYRR");

        System.out.print("Input: \"RRRRYYGG\" -> ");
        findLongestStreak("RRRRYYGG");
    }
}