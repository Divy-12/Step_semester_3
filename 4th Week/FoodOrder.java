import java.util.Objects;

public class FoodOrder {
    private final String studentName;
    private final String dishName;
    private boolean isDelivered;

    // Parameterized constructor acting as a validation gate
    public FoodOrder(String studentName, String dishName) {
        if (!isValid(studentName)) {
            throw new IllegalArgumentException("Invalid student name: must not be null or whitespace.");
        }
        if (!isValid(dishName)) {
            throw new IllegalArgumentException("Invalid dish name: must not be null or whitespace.");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.isDelivered = false;
    }

    private static boolean isValid(String str) {
        return str != null && !str.trim().isEmpty();
    }

    // Idempotent state change handling
    public void markDelivered() {
        if (this.isDelivered) {
            System.out.println("ALERT: Order for " + studentName + " (" + dishName + ") was ALREADY marked delivered!");
            return;
        }
        this.isDelivered = true;
        System.out.println("Order for " + studentName + " (" + dishName + ") marked delivered successfully.");
    }

    public String getStudentName() { return studentName; }
    public String getDishName() { return dishName; }

    public static void processBatch(String[][] rawOrders) {
        int accepted = 0;
        int rejected = 0;

        if (rawOrders != null) {
            for (String[] rawOrder : rawOrders) {
                if (rawOrder == null || rawOrder.length < 2) {
                    rejected++;
                    continue;
                }

                try {
                    // Fail-fast construction
                    new FoodOrder(rawOrder[0], rawOrder[1]);
                    accepted++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        System.out.printf("Valid: %d | Rejected: %d%n", accepted, rejected);
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 1: Ghost Order Validator ===");

        String[][] batch = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };

        System.out.print("Output: ");
        processBatch(batch);
    }
}