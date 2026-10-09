public class WordReversalEncoder {

    public static String reverseEachWord(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return "";
        }

        // Split sentence into individual words
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            // Reverse individual word using StringBuilder
            StringBuilder reversedWord = new StringBuilder(words[i]).reverse();
            result.append(reversedWord);

            // Re-insert space between words
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== Word Reversal Encoder ===");

        String input = "hello club";
        String output = reverseEachWord(input);

        System.out.println("Input:  \"" + input + "\"");
        System.out.println("Output: \"" + output + "\"");
    }
}