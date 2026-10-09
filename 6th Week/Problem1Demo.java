import java.util.Objects;

public class Problem1Demo {

    // Shared Foundation Class
    public static class EventTicket {
        private final String attendeeId;
        private final double basePrice;
        private double amountPaid;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null) {
                throw new IllegalArgumentException("construction rejected");
            }
            String trimmedId = attendeeId.trim();
            if (trimmedId.length() < 4) {
                throw new IllegalArgumentException("construction rejected");
            }
            if (basePrice < 0) {
                throw new IllegalArgumentException("basePrice must be non-negative");
            }

            this.attendeeId = trimmedId;
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
        }

        public void pay(double amount) {
            if (amount > 0) {
                this.amountPaid += amount;
            }
        }

        public double getBalanceDue() {
            return Math.max(0.0, basePrice - amountPaid);
        }

        public String getAttendeeId() {
            return attendeeId;
        }

        public double getBasePrice() {
            return basePrice;
        }
    }

    // Single Inheritance Specialization
    public static class WorkshopTicket extends EventTicket {
        private final String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice); // Forwards shared fields without duplication
            this.track = track;
        }

        public String getTrack() {
            return track;
        }
    }

    // Batch Registration Processor
    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        if (attendeeIds != null) {
            for (String id : attendeeIds) {
                try {
                    // Try to construct object; rely on constructor's validation gate
                    new EventTicket(id, basePrice);
                    registered++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 1 Tests ===");

        // Validation test
        try {
            new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("Construction (\"ST1\"): " + e.getMessage());
        }

        // WorkshopTicket payments test
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println("Balance due for STU2: " + w.getBalanceDue());

        // Batch test
        String[] batch = {"STU1", "ST1", "STU2", " ", "STU3"};
        System.out.println(registerBatch(batch, 500));
    }
}