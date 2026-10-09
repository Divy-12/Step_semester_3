public class StaticMisuseDemo {

    // --- BROKEN VERSION WITH MISUSED STATIC FIELDS ---
    static class BrokenSrmStudent {
        static String name;
        static String regNo;
        static int attendance;

        public BrokenSrmStudent(String name, String regNo, int attendance) {
            BrokenSrmStudent.name = name;
            BrokenSrmStudent.regNo = regNo;
            BrokenSrmStudent.attendance = attendance;
        }

        public void printName() {
            System.out.println(name);
        }
    }

    // --- FIXED VERSION WITH CORRECT INSTANCE / STATIC BOUNDARY ---
    static class FixedSrmStudent {
        /*
         * FIELD BREAKDOWN JUSTIFICATION:
         * 1. 'name', 'regNo', 'attendance': INSTANCE fields because every student has unique,
         *    independent individual values.
         * 2. 'university', 'admissionCount': STATIC fields because 'university' is shared 
         *    identically across all students, and 'admissionCount' tracks system-wide stats.
         */
        private String name;
        private String regNo;
        private int attendance;

        private static String university = "SRM Institute of Science and Technology";
        private static int admissionCount = 0;

        public FixedSrmStudent(String name, int attendance) {
            this.name = name;
            this.attendance = attendance;
            admissionCount++;
            // Auto-generates unique registration numbers sequentially
            this.regNo = "RA2311003010" + (10 + admissionCount);
        }

        public void printIdCard() {
            System.out.println(name + " | " + regNo);
        }

        public static void printTotalAdmissions() {
            System.out.println("Students admitted so far: " + admissionCount);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Broken Version Output ===");
        BrokenSrmStudent student1 = new BrokenSrmStudent("Ravi", "RA01", 85);
        BrokenSrmStudent student2 = new BrokenSrmStudent("Meera", "RA02", 90);

        student1.printName(); // Prints "Meera" because static fields are shared class-wide!
        student2.printName();

        System.out.println("\n=== Fixed Version Output ===");
        FixedSrmStudent fixedStudent1 = new FixedSrmStudent("Ravi", 85);
        FixedSrmStudent fixedStudent2 = new FixedSrmStudent("Meera", 90);

        fixedStudent1.printIdCard();
        fixedStudent2.printIdCard();
        FixedSrmStudent.printTotalAdmissions();
    }
}