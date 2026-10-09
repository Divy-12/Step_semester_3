public class TypingAccuracyChecker {

    public static void checkTypingAccuracy(String original, String typed) {
        int length = Math.min(original.length(), typed.length());
        int matched = 0;
        int firstMismatchPos = -1;
        char originalMismatchChar = '\0';
        char typedMismatchChar = '\0';

        // Traversal and character comparison
        for (int i = 0; i < length; i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else if (firstMismatchPos == -1) {
                firstMismatchPos = i + 1; // 1-based index position
                originalMismatchChar = original.charAt(i);
                typedMismatchChar = typed.charAt(i);
            }
        }

        // Account for length differences if strings are unequal
        if (firstMismatchPos == -1 && original.length() != typed.length()) {
            firstMismatchPos = length + 1;
            originalMismatchChar = original.length() > length ? original.charAt(length) : '[End of String]';
            typedMismatchChar = typed.length() > length ? typed.charAt(length) : '[End of String]';
        }

        int totalChars = original.length();
        double accuracy = ((double) matched / totalChars) * 100;

        System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | ", matched, totalChars, accuracy);
        if (firstMismatchPos == -1) {
            System.out.println("No Mismatches");
        } else {
            System.out.printf("First Mismatch at position %d ('%c' vs '%c')%n", 
                    firstMismatchPos, originalMismatchChar, typedMismatchChar);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Typing Speed Test Accuracy Checker ===");

        System.out.print("Test 1: ");
        checkTypingAccuracy("hello world", "hello worlt");

        System.out.print("Test 2: ");
        checkTypingAccuracy("coding", "coding");
    }
}