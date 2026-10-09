import java.util.Arrays;

// Immutable final class
public final class DischargeSummary {
    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new IllegalArgumentException("construction rejected");
        }
        if (medicationCodes == null) {
            throw new IllegalArgumentException("construction rejected");
        }

        // Validate all medication codes: format "MED-" + 1 uppercase letter
        String[] validatedCodes = new String[medicationCodes.length];
        for (int i = 0; i < medicationCodes.length; i++) {
            String code = medicationCodes[i];
            if (code == null || !code.matches("^MED-[A-Z]$")) {
                throw new IllegalArgumentException("construction rejected");
            }
            validatedCodes[i] = code;
        }

        this.patientId = patientId;
        // Defensive copy on intake
        this.medicationCodes = validatedCodes.clone();
    }

    public String getPatientId() {
        return patientId;
    }

    // Defensive copy on outgo
    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    // "Wither" pattern method for functional immutability updates
    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException("Invalid medication index");
        }
        String[] updatedCodes = medicationCodes.clone();
        updatedCodes[index] = newCode;
        return new DischargeSummary(this.patientId, updatedCodes);
    }

    // Subclass representing critical care variant
    public static class CriticalCareDischargeSummary extends DischargeSummary {
        private final int icuDays;

        public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
            super(patientId, medicationCodes);
            if (icuDays < 0) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.icuDays = icuDays;
        }

        public int getIcuDays() {
            return icuDays;
        }
    }

    // Static block for class-level setup
    static {
        // System initialization logger/config setup
    }

    // Nightly batch processor
    public static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries != null) {
            for (DischargeSummary summary : summaries) {
                if (summary == null) {
                    nullSkipped++;
                    continue;
                }

                processed++;
                if (summary instanceof CriticalCareDischargeSummary) {
                    criticalCare++;
                } else {
                    routine++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " 
                + criticalCare + " critical-care | " + routine + " routine";
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 5 Tests ===");

        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid code rejection test: " + e.getMessage());
        }

        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println("Defensive copy test (original unchanged): " + d.getMedicationCodes()[0]);

        DischargeSummary[] batch = new DischargeSummary[]{
            new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[]{"MED-Y"})
        };

        System.out.println("Batch execution summary: " + processNightlyBatch(batch));
    }
}