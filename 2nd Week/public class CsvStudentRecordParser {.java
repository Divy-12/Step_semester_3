public class CsvStudentRecordParser {

    public static void parseStudentRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        String[] fields = csvLine.split(",");

        // Validate that exactly 3 fields exist
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        String name = fields[0].trim();
        String rollNo = fields[1].trim();
        String dept = fields[2].trim();

        System.out.println("Name: " + name + " | Roll No: " + rollNo + " | Dept: " + dept);
    }

    public static void main(String[] args) {
        System.out.println("=== CSV Student Record Parser ===");

        System.out.print("Test 1: ");
        parseStudentRecord("Ananya Verma,RA2211003010123,CSE");

        System.out.print("Test 2: ");
        parseStudentRecord("Ananya Verma,CSE");
    }
}