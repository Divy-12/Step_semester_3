public class Problem2Demo {

    public static class RaceEntry {
        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid bibNumber");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.amountPaid = 0.0;
        }

        public void pay(double amount) {
            if (amount > 0) this.amountPaid += amount;
        }

        public double getBalanceDue() {
            return Math.max(0.0, entryFee - amountPaid);
        }

        public String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
        }

        public String getBibNumber() { return bibNumber; }
        public double getEntryFee() { return entryFee; }
    }

    // Single-level subclass
    public static class RunnerEntry extends RaceEntry {
        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        public String getCategory() { return category; }

        @Override
        public String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category + " | Balance: " + getBalanceDue();
        }
    }

    // Multilevel Subclass (3 Generations Deep)
    public static class EliteRunnerEntry extends RunnerEntry {
        private final double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        public double getSponsorBonus() { return sponsorBonus; }

        @Override
        public String announce() {
            return "Elite Runner | Bib: " + getBibNumber() + " | Category: " + getCategory() 
                    + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
        }
    }

    // Hierarchical Subclass (Direct Child of RaceEntry)
    public static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        public int getTeamSize() { return teamSize; }

        @Override
        public String announce() {
            return "Relay Team | Bib: " + getBibNumber() + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue();
        }
    }

    // Classifies family hierarchy using instanceof
    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else if (entry instanceof RunnerEntry) {
            return "Single-level descendant";
        } else {
            return "Root base entry";
        }
    }

    // Polymorphic balance accumulator
    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0.0;
        if (entries != null) {
            for (RaceEntry e : entries) {
                if (e != null) {
                    total += e.getBalanceDue(); // Polymorphic invocation
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 2 Tests ===");

        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry elite = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relay = new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(runner.announce());
        System.out.println(elite.announce());
        System.out.println(relay.announce());

        System.out.println("\nClassification:");
        System.out.println(classifyGeneration(elite));
        System.out.println(classifyGeneration(relay));

        RaceEntry[] entries = { runner, elite, relay };
        System.out.println("\nTotal Outstanding Balance: " + getTotalBalanceDue(entries));
    }
}