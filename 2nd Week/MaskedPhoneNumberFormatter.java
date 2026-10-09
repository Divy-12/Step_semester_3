public class MaskedPhoneNumberFormatter {

    public static String maskPhoneNumber(String phone) {
        if (phone == null) {
            return "Invalid phone number";
        }

        String trimmed = phone.trim();

        // Validate length
        if (trimmed.length() != 10) {
            return "Invalid phone number";
        }

        // Validate all characters are digits
        for (int i = 0; i < trimmed.length(); i++) {
            if (!Character.isDigit(trimmed.charAt(i))) {
                return "Invalid phone number";
            }
        }

        // Build masked string using StringBuilder
        StringBuilder sb = new StringBuilder("XXXXXX");
        sb.append("-");
        sb.append(trimmed.substring(6)); // Extract last 4 digits

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== Masked Phone Number Formatter ===");

        System.out.println("Input: \"9876543210\" -> Output: " + maskPhoneNumber("9876543210"));
        System.out.println("Input: \"98765\"      -> Output: " + maskPhoneNumber("98765"));
    }
}