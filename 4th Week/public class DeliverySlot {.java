public class DeliverySlot {
    private final String orderId;
    private final String timeSlot;

    private static final String DEFAULT_SLOT = "ASAP";

    // Master Constructor
    public DeliverySlot(String orderId, String timeSlot) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty.");
        }
        this.orderId = orderId.trim();
        this.timeSlot = (timeSlot == null || timeSlot.trim().isEmpty()) ? DEFAULT_SLOT : timeSlot.trim();
    }

    // Constructor Chaining via this(...)
    public DeliverySlot(String orderId) {
        this(orderId, DEFAULT_SLOT);
    }

    public boolean isPeakHour() {
        return "12:00-13:00".equals(timeSlot) ||
               "13:00-14:00".equals(timeSlot) ||
               "19:00-20:00".equals(timeSlot) ||
               "20:00-21:00".equals(timeSlot);
    }

    public String getOrderId() { return orderId; }
    public String getTimeSlot() { return timeSlot; }

    public static void main(String[] args) {
        System.out.println("=== Problem 2: Delivery Slot Booking ===");

        DeliverySlot slot1 = new DeliverySlot("ORD101", "13:00-14:00");
        System.out.println("ORD101 isPeakHour: " + slot1.isPeakHour());

        DeliverySlot slot2 = new DeliverySlot("ORD102");
        System.out.println("ORD102 (Slot: " + slot2.getTimeSlot() + ") isPeakHour: " + slot2.isPeakHour());
    }
}