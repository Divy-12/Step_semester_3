public class Problem5Demo {

    public static class EventTicket {
        private static int counter;
        private final String ticketId;
        private final double basePrice;
        private double amountPaid;

        // Static initialization block
        static {
            counter = 1000;
        }

        public EventTicket(double basePrice) {
            if (basePrice < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            counter++;
            this.ticketId = "TCK-" + counter; // Auto-generated final field
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
        }

        // Method Overloading: Flat payment
        public void pay(double amount) {
            if (amount > 0) {
                this.amountPaid += amount;
            }
        }

        // Method Overloading: Mode-aware payment reusing flat version
        public void pay(double amount, String mode) {
            System.out.println("Payment of Rs " + amount + " recorded via " + mode);
            pay(amount); // Reuse single-parameter payment logic
        }

        public double getBalanceDue() {
            return Math.max(0.0, basePrice - amountPaid);
        }

        public String getTicketId() {
            return ticketId;
        }

        public static int getTicketsIssued() {
            return counter - 1000;
        }

        // Exact character-by-character promo code validation without regex
        public static boolean isValidPromoCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'F') {
                return false;
            }
            if (!Character.isDigit(code.charAt(1)) ||
                !Character.isDigit(code.charAt(2)) ||
                !Character.isDigit(code.charAt(3))) {
                return false;
            }
            return Character.isUpperCase(code.charAt(4));
        }
    }

    public static class GroupTicket extends EventTicket {
        private final int groupSize;

        public GroupTicket(double basePrice, int groupSize) {
            super(basePrice * groupSize);
            this.groupSize = groupSize;
        }

        public int getGroupSize() {
            return groupSize;
        }
    }

    // Nightly Settlement Processor with null safety & instanceof dispatch
    public static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        if (tickets != null) {
            for (EventTicket ticket : tickets) {
                if (ticket == null) {
                    nullSkipped++;
                    continue;
                }

                processed++;
                if (ticket instanceof GroupTicket) {
                    groupCount++;
                } else {
                    individualCount++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " +
               groupCount + " group | " + individualCount + " individual";
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 5 Tests ===");

        EventTicket t1 = new EventTicket(500);
        System.out.println("Generated Ticket ID: " + t1.getTicketId());
        System.out.println("Total Tickets Issued: " + EventTicket.getTicketsIssued());

        System.out.println("\nPromo Code Validations:");
        System.out.println("F123A: " + EventTicket.isValidPromoCode("F123A"));
        System.out.println("F12A:  " + EventTicket.isValidPromoCode("F12A"));
        System.out.println("X123A: " + EventTicket.isValidPromoCode("X123A"));

        System.out.println("\nPayment Overload Test:");
        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println("Remaining Balance Due: " + t1.getBalanceDue());

        System.out.println("\nNightly Settlement:");
        EventTicket[] batch = {
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        };
        System.out.println(processNightlySettlement(batch));
    }
}