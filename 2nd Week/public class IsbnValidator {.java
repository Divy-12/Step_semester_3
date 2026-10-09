public class IsbnValidator {

    // Trims whitespace and uppercases only the first 3 characters
    public static String normalizeCode(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    // Validates and formats normalized ISBN code without using regex
    public static String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: wrong length (must be 13 characters)";
        }

        // Validate first 3 characters are letters
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        // Validate remaining 10 characters are digits
        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }

        // Extract sections
        String pubCode = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalog = code.substring(7, 13);

        // Format output display string using StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(pubCode).append("] ");
        sb.append("YEAR: ").append(year);
        sb.append(" | CATALOG: ").append(catalog);

        return sb.toString();
    }

    public static void processCode(String rawInput) {
        String normalized = normalizeCode(rawInput);
        String result = validateAndFormat(normalized);
        System.out.println("Input:  \"" + rawInput + "\"\nOutput: " + result + "\n");
    }

    public static void main(String[] args) {
        System.out.println("=== Library ISBN Normalizer & Validator ===\n");

        processCode(" pen2026004251 ");
        processCode("12N2026004251");
    }
}