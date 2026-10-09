public class Problem4Demo {

    public static class EventTicket {
        private final double basePrice;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
        }

        public double getBalanceDue() {
            return basePrice;
        }

        public String printTicket() {
            return "Standard | Balance: " + getBalanceDue();
        }
    }

    public static class WorkshopTicket extends EventTicket {
        private final String track;

        public WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }

        @Override
        public String printTicket() {
            return "Workshop | Track: " + track + " | Balance: " + getBalanceDue();
        }
    }

    public static String batchPrint(EventTicket[] tickets) {
        if (tickets == null || tickets.length == 0) {
            return "";
        }

        StringBuilder report = new StringBuilder();

        for (EventTicket ticket : tickets) {
            if (ticket == null) continue;

            // 1. Polymorphic call
            report.append(ticket.printTicket());

            // 2. Safe downcast using instanceof
            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket wt = (WorkshopTicket) ticket;
                report.append(" [Track via downcast: ").append(wt.getTrack()).append("]");
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 4 Tests ===");

        EventTicket[] tickets = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };

        System.out.println(batchPrint(tickets));

        // ClassCastException demonstration
        try {
            EventTicket plain = new EventTicket(500);
            WorkshopTicket bad = (WorkshopTicket) plain; // Fails at runtime
        } catch (ClassCastException e) {
            System.out.println("\nDemonstrated expected runtime failure: " + e.toString());
        }
    }
}