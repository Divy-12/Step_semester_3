public class AttendanceSystem {

    static class SrmStudent {
        private String name;
        private String regNo;
        private int attendance;

        public SrmStudent(String name, String regNo, int attendance) {
            this.name = name;
            this.regNo = regNo;
            this.attendance = attendance;
        }

        public void addAttendanceUpdate(int newAttendance) {
            this.attendance = newAttendance;
        }

        public boolean isEligible() {
            return this.attendance >= 75;
        }

        public String getName() {
            return name;
        }

        public int getAttendance() {
            return attendance;
        }

        /*
         * DESIGN JUSTIFICATION:
         * 1. 'isEligible()' is an INSTANCE method because eligibility depends on the specific 
         *    attendance data of an individual student object.
         * 2. 'classAverage()' is a STATIC method because calculating the overall average is a 
         *    utility operation over an entire collection of students. It belongs to the class 
         *    conceptually rather than to any single student instance.
         */
        public static double classAverage(SrmStudent[] students) {
            if (students == null || students.length == 0) {
                return 0.0;
            }
            int totalAttendance = 0;
            for (SrmStudent student : students) {
                totalAttendance += student.getAttendance();
            }
            return (double) totalAttendance / students.length;
        }
    }

    public static void main(String[] args) {
        SrmStudent[] students = new SrmStudent[] {
            new SrmStudent("Ravi", "RA01", 82),
            new SrmStudent("Anitha", "RA02", 68),
            new SrmStudent("Karthik", "RA03", 91),
            new SrmStudent("Meera", "RA04", 74),
            new SrmStudent("Suresh", "RA05", 60)
        };

        for (SrmStudent student : students) {
            String status = student.isEligible() ? "Eligible" : "Detained";
            System.out.println(student.getName() + " - " + student.getAttendance() + "% - " + status);
        }

        double avg = SrmStudent.classAverage(students);
        System.out.printf("Class average: %.1f%%%n", avg);
    }
}