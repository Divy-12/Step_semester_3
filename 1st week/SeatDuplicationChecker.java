public class SeatDuplicationChecker {

    public static void checkDuplicateSeats(int[] seatNumbers) {
        boolean duplicateFound = false;

        // Outer loop selects a seat number
        for (int i = 0; i < seatNumbers.length; i++) {
            // Inner loop compares it with all subsequent seat numbers
            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    // Avoid printing the same duplicate seat multiple times
                    boolean alreadyPrinted = false;
                    for (int k = 0; k < i; k++) {
                        if (seatNumbers[k] == seatNumbers[i]) {
                            alreadyPrinted = true;
                            break;
                        }
                    }
                    if (!alreadyPrinted) {
                        System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                        duplicateFound = true;
                    }
                    break;
                }
            }
        }

        if (!duplicateFound) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exam Hall Seat Duplication Checker ===");

        int[] sample1 = {101, 102, 103, 102, 105};
        System.out.print("Input: {101, 102, 103, 102, 105} -> Output: ");
        checkDuplicateSeats(sample1);

        int[] sample2 = {101, 102, 103, 104, 105};
        System.out.print("Input: {101, 102, 103, 104, 105} -> Output: ");
        checkDuplicateSeats(sample2);
    }
}