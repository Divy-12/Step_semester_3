public class Problem2Demo {

    public static class EventTicket {
        private final String attendeeId;
        private final double basePrice;
        private double amountPaid;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid attendeeId");
            }
            this.attendeeId = attendeeId.trim();
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
        }

        public void pay(double amount) {
            if (amount > 0) this.amountPaid += amount;
        }

        public double getBalanceDue() {
            return Math.max(0.0, basePrice - amountPaid);
        }

        public String printTicket() {
            return "Standard Event Ticket | Balance Due: " + getBalanceDue();
        }

        public String getAttendeeId() { return attendeeId; }
        public double getBasePrice() { return basePrice; }
    }

    // First Level Subclass
    public static class WorkshopTicket extends EventTicket {
        private final String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        public String getTrack() { return track; }

        @Override
        public String printTicket() {
            return "Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue();
        }
    }

    // Multilevel Subclass (3 Generations Deep)
    public static class PremiumWorkshopTicket extends WorkshopTicket {
        private final double kitFee;

        public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
            super(attendeeId, basePrice, track);
            this.kitFee = kitFee;
        }

        @Override
        public double getBalanceDue() {
            return super.getBalanceDue() + kitFee;
        }

        @Override
        public String printTicket() {
            return "Premium Workshop Ticket | Track: " + getTrack() + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue();
        }
    }

    // Hierarchical Subclass (Direct Branch from EventTicket)
    public static class HackathonTicket extends EventTicket {
        private final String teamName;

        public HackathonTicket(String attendeeId, double basePrice, String teamName) {
            super(attendeeId, basePrice);
            this.teamName = teamName;
        }

        @Override
        public String printTicket() {
            return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue();
        }
    }

    // Classifies inheritance tree shape using instanceof checks
    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        } else if (ticket instanceof WorkshopTicket) {
            return "Single-level descendant";
        } else {
            return "Root base class";
        }
    }

    // Polymorphic total balance calculation
    public static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0.0;
        if (tickets != null) {
            for (EventTicket t : tickets) {
                if (t != null) {
                    total += t.getBalanceDue(); // Polymorphic method call
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 2 Tests ===");

        EventTicket standard = new EventTicket("STU1", 500);
        WorkshopTicket workshop = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premium = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathon = new HackathonTicket("STU4", 800, "Byte Force");

        System.out.println(standard.printTicket());
        System.out.println(workshop.printTicket());
        System.out.println(premium.printTicket());
        System.out.println(hackathon.printTicket());

        System.out.println("\nClassification:");
        System.out.println(classifyGeneration(premium));
        System.out.println(classifyGeneration(hackathon));

        EventTicket[] family = { standard, workshop, premium, hackathon };
        System.out.println("\nTotal Outstanding Balance: " + getTotalBalanceDue(family));
    }
}