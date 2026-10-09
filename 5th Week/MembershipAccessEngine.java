import java.util.HashMap;
import java.util.Map;

public class MembershipAccessEngine {

    // Classifies access based on Java visibility rules across 3 contexts
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        switch (fieldModifier) {
            case "public":
                return "ALLOWED";

            case "protected":
            case "default":
                if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "private":
                if ("SAME_CLASS".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";

            default:
                return "DENIED";
        }
    }

    // Summarizes access attempts grouped per field modifier
    public static String summarizeByModifier(String[][] attempts) {
        String[] modifiers = {"private", "default", "protected", "public"};
        Map<String, Integer> allowedMap = new HashMap<>();
        Map<String, Integer> deniedMap = new HashMap<>();

        for (String mod : modifiers) {
            allowedMap.put(mod, 0);
            deniedMap.put(mod, 0);
        }

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String mod = attempt[0];
                    String context = attempt[1];

                    if (allowedMap.containsKey(mod)) {
                        String res = classifyAccess(mod, context);
                        if ("ALLOWED".equals(res)) {
                            allowedMap.put(mod, allowedMap.get(mod) + 1);
                        } else {
                            deniedMap.put(mod, deniedMap.get(mod) + 1);
                        }
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < modifiers.length; i++) {
            String mod = modifiers[i];
            sb.append(mod).append(": ")
              .append(allowedMap.get(mod)).append(" allowed / ")
              .append(deniedMap.get(mod)).append(" denied");

            if (i < modifiers.length - 1) {
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    // Domain model class applying appropriate field modifiers
    public static class LibraryMember {
        private String membershipId;     // Encapsulated
        String branchCode;              // Package-private / default
        protected double finesOwed;     // Accessible to package and subclasses
        public String displayName;      // Publicly accessible

        // Parameterized constructor acting as an intake gate
        public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
            if (membershipId == null) {
                throw new IllegalArgumentException("construction rejected");
            }
            String trimmedId = membershipId.trim();
            if (trimmedId.isEmpty() || trimmedId.length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }

            this.membershipId = trimmedId;
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }

        public String getMembershipId() {
            return membershipId;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 1 Tests ===");
        System.out.println("private in SAME_CLASS: " + classifyAccess("private", "SAME_CLASS"));
        System.out.println("protected in DIFFERENT_PACKAGE: " + classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] batch = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println("Summary by Modifier: " + summarizeByModifier(batch));

        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (IllegalArgumentException e) {
            System.out.println("Construction test (\"LB9\"): " + e.getMessage());
        }

        LibraryMember valid = new LibraryMember("LB94", "BR1", 0, "Priya Nair");
        System.out.println("Construction test (\"LB94\"): Success (ID: " + valid.getMembershipId() + ")");
    }
}