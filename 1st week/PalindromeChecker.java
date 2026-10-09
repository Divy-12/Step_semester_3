public class PalindromeChecker {

    // Approach 1: Iterative Comparison
    public static boolean isPalindromeIterative(String text) {
        String cleanText = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int left = 0;
        int right = cleanText.length() - 1;

        while (left < right) {
            if (cleanText.charAt(left) != cleanText.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // Approach 2: Recursive Check
    public static boolean isPalindromeRecursive(String text) {
        String cleanText = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return checkRecursive(cleanText, 0, cleanText.length() - 1);
    }

    private static boolean checkRecursive(String str, int start, int end) {
        if (start >= end) {
            return true;
        }
        if (str.charAt(start) != str.charAt(end)) {
            return false;
        }
        return checkRecursive(str, start + 1, end - 1);
    }

    // Approach 3: Array Reversal Check
    public static boolean isPalindromeArrayReversal(String text) {
        String cleanText = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        char[] original = cleanText.toCharArray();
        char[] reversed = new char[original.length];

        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }

        return new String(original).equals(new String(reversed));
    }

    public static void verifyText(String text) {
        boolean iterative = isPalindromeIterative(text);
        boolean recursive = isPalindromeRecursive(text);
        boolean arrayReversal = isPalindromeArrayReversal(text);

        String iterRes = iterative ? "Palindrome" : "Not Palindrome";
        String recurRes = recursive ? "Palindrome" : "Not Palindrome";
        String arrRes = arrayReversal ? "Palindrome" : "Not Palindrome";

        System.out.println("Input: \"" + text + "\"");
        System.out.printf("Iterative: %s | Recursive: %s | Array Reversal: %s%n%n", 
                iterRes, recurRes, arrRes);
    }

    public static void main(String[] args) {
        System.out.println("=== QA Text Verification Toolkit ===");
        verifyText("madam");
        verifyText("hello");
        verifyText("A man, a plan, a canal: Panama");
    }
}