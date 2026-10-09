public class FirstNonRepeatingChar {

    // Finds the first character with a frequency of 1
    public static char findFirstNonRepeatingChar(String text) {
        int[] frequencyMap = new int[256]; // Frequency array for ASCII characters

        // Count occurrences of each character
        for (int i = 0; i < text.length(); i++) {
            frequencyMap[text.charAt(i)]++;
        }

        // Scan left to right for the first unique character
        for (int i = 0; i < text.length(); i++) {
            if (frequencyMap[text.charAt(i)] == 1) {
                return text.charAt(i);
            }
        }

        return '\0'; // Return null character if no non-repeating character exists
    }

    public static void testInput(String text) {
        char result = findFirstNonRepeatingChar(text);
        System.out.println("Input: \"" + text + "\"");
        if (result != '\0') {
            System.out.println("First Non-Repeating Character: '" + result + "'");
        } else {
            System.out.println("No Non-Repeating Character Found");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("=== Unique Letter Hunt Mini-Game ===");
        testInput("swiss");
        testInput("aabbcc");
        testInput("college coding arcade");
    }
}