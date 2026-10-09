public class BankTransactionValidator {

    // Normalizes input: trims whitespace and uppercases only the first 3 characters
    public static String normalizeReference(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    // Validates and formats the normalized reference code without regex
    public static String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: wrong length (must be 14 characters)";
        }

        // Validate first 3 characters are letters
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        // Validate remaining 11 characters are digits
        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }

        // Extract components
        String bankCode = reference.substring(0, 3);
        String day = reference.substring(3, 5);
        String month = reference.substring(5, 7);
        String year = reference.substring(7, 9);
        String seq = reference.substring(9, 14);

        // Format output using StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(bankCode).append("] ");
        sb.append("DATE: ").append(day).append("/").append(month).append("/").append(year);
        sb.append(" | SEQ: ").append(seq);

        return sb.toString();
    }

    public static void processReference(String rawInput) {
        String normalized = normalizeReference(rawInput);
        String result = validateAndFormat(normalized);
        System.out.println("Input: \"" + rawInput + "\"\nOutput: " + result + "\n");
    }

    public static void main(String[] args) {
        System.out.println("=== Bank Transaction Reference Generator & Validator ===\n");

        processReference(" hdf03022600042 ");
        processReference("12F03022600042");
    }
}