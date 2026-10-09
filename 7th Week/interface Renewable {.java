interface Renewable {
    String renew();
}

interface Reservable {
    String reserve();
}

public abstract class LibraryItem {
    private static int counter = 1000;
    private final String itemId;

    public LibraryItem() {
        counter++;
        this.itemId = "LIB-" + counter;
    }

    public String getItemId() {
        return itemId;
    }

    public abstract int getLoanPeriodDays();

    public static void processCheckouts(LibraryItem[] items) {
        if (items != null) {
            for (LibraryItem item : items) {
                if (item != null) {
                    System.out.println("Loan Period: " + item.getLoanPeriodDays() + " days (ID: " + item.getItemId() + ")");
                }
            }
        }
    }

    public static String reserveIfSupported(Object o) {
        if (o instanceof Reservable) {
            Reservable r = (Reservable) o; // Safe cast
            return r.reserve();
        }
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 5: Community Library Checkout System ===");

        Textbook t = new Textbook("Java Fundamentals");
        System.out.println("Loan Period: " + t.getLoanPeriodDays());
        System.out.println(t.renew());
        System.out.println(t.reserve());

        Magazine m = new Magazine("Tech Monthly");
        DigitalPass d = new DigitalPass("E-Journal Access");

        System.out.println("\n--- Testing Reservation Capability ---");
        System.out.println("Magazine: " + reserveIfSupported(m));
        System.out.println("DigitalPass: " + reserveIfSupported(d));

        LibraryItem ref = t; // Upcasting Textbook to LibraryItem
        System.out.println("Upcasted Textbook: " + reserveIfSupported(ref));
    }
}

class Textbook extends LibraryItem implements Renewable, Reservable {
    private final String title;

    public Textbook(String title) {
        super();
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 14;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }

    @Override
    public String reserve() {
        return title + " reserved";
    }
}

class Magazine extends LibraryItem implements Renewable {
    private final String title;

    public Magazine(String title) {
        super();
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 7;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }
}

class DigitalPass implements Renewable {
    private final String resourceName;

    public DigitalPass(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String renew() {
        return resourceName + " renewed";
    }
}