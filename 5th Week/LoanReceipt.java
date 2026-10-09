import java.util.Arrays;

// Truly immutable final class
public final class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("construction rejected");
        }
        if (bookIds == null) {
            throw new IllegalArgumentException("construction rejected");
        }

        // Validate each book ID format: "BK-" followed by exactly 3 digits
        String[] validatedIds = new String[bookIds.length];
        for (int i = 0; i < bookIds.length; i++) {
            String id = bookIds[i];
            if (id == null || !id.matches("^BK-\\d{3}$")) {
                throw new IllegalArgumentException("construction rejected");
            }
            validatedIds[i] = id;
        }

        this.memberId = memberId;
        // Defensive copy on intake
        this.bookIds = validatedIds.clone();
    }

    public String getMemberId() {
        return memberId;
    }

    // Defensive copy on return
    public String[] getBookIds() {
        return bookIds.clone();
    }

    // Wither method providing functional immutability update capability
    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid book index");
        }
        String[] updatedIds = bookIds.clone();
        updatedIds[index] = newId;
        return new LoanReceipt(this.memberId, updatedIds);
    }

    // Subclass representing reference-only reading room receipt
    public static class ReferenceOnlyLoanReceipt extends LoanReceipt {
        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("construction rejected");
            }
            this.roomNumber = roomNumber.trim();
        }

        public String getRoomNumber() {
            return roomNumber;
        }
    }

    // Static block for class-level configuration setup
    static {
        // Shared ledger configuration initialization
    }

    // Nightly circulation batch processor
    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts != null) {
            for (LoanReceipt receipt : receipts) {
                if (receipt == null) {
                    nullSkipped++;
                    continue;
                }

                processed++;
                if (receipt instanceof ReferenceOnlyLoanReceipt) {
                    referenceOnly++;
                } else {
                    regular++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " 
                + referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 5 Tests ===");

        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID format rejection test: " + e.getMessage());
        }

        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println("Defensive copy test (original unchanged): " + r.getBookIds()[0]);

        LoanReceipt[] batch = new LoanReceipt[]{
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };

        System.out.println("Batch execution summary: " + processNightlyCirculation(batch));
    }
}