import java.util.Objects;

public class AccessRuleEngine {

    // Classifies access based on Java visibility rules (3 contexts)
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

    // Summarizes a batch of access attempts
    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String result = classifyAccess(attempt[0], attempt[1]);
                    if ("ALLOWED".equals(result)) {
                        allowed++;
                    } else {
                        denied++;
                    }
                } else {
                    denied++;
                }
            }
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    // Protected domain model class
    public static class PatientRecord {
        private String patientId;       // Strictly encapsulated
        String wardCode;                // Package-private / default
        protected double vitalsScore;   // Accessible to subclasses & package
        public String facilityName;     // Fully public

        // Parameterized constructor acting as an intake gate
        public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
            if (patientId == null) {
                throw new IllegalArgumentException("construction rejected");
            }
            String trimmedId = patientId.trim();
            if (trimmedId.isEmpty() || trimmedId.length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }

            this.patientId = trimmedId;
            this.wardCode = wardCode;
            this.vitalsScore = vitalsScore;
            this.facilityName = facilityName;
        }

        public String getPatientId() {
            return patientId;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 1 Tests ===");
        System.out.println("private in SAME_CLASS: " + classifyAccess("private", "SAME_CLASS"));
        System.out.println("default in DIFFERENT_PACKAGE: " + classifyAccess("default", "DIFFERENT_PACKAGE"));

        String[][] batch = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println("Batch Summary: " + summarizeBatch(batch));

        try {
            new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
        } catch (IllegalArgumentException e) {
            System.out.println("Construction test (\"MT9\"): " + e.getMessage());
        }

        PatientRecord valid = new PatientRecord("MT94", "W3", 98.2, "MediTrack Central");
        System.out.println("Construction test (\"MT94\"): Success (ID: " + valid.getPatientId() + ")");
    }
}