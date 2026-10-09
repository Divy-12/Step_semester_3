public class BookInventory {
    private final int copiesTotal;
    private int copiesAvailable;

    // Parameterized constructor ensuring valid initial inventory
    public BookInventory(int copiesTotal) {
        if (copiesTotal <= 0) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    // Safely handles check-out attempts while upholding state invariants
    public void checkOut() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    // Safely handles check-in attempts without exceeding initial capacity
    public void checkIn() {
        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public int getCopiesTotal() {
        return copiesTotal;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 3 Tests ===");

        try {
            new BookInventory(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Construction test (copiesTotal = 0): " + e.getMessage());
        }

        BookInventory b = new BookInventory(3);
        b.checkOut(); b.checkOut(); b.checkOut(); b.checkOut(); // 4 checkouts
        System.out.println("Copies available after 4 checkouts (Capacity 3): " + b.getCopiesAvailable());

        b.checkIn(); b.checkIn(); b.checkIn(); b.checkIn(); // 4 checkins
        System.out.println("Copies available after 4 checkins (Capacity 3): " + b.getCopiesAvailable());
    }
}