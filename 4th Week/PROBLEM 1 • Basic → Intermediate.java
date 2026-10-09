import java.util.ArrayList;
import java.util.List;

public class BusTicket {
    private final String passengerName;
    private final String destination;
    private boolean checkedIn;

    // Parameterized constructor acting as a validation gate
    public BusTicket(String passengerName, String destination) {
        if (!isValidName(passengerName)) {
            throw new IllegalArgumentException("Invalid passenger name: " + passengerName);
        }
        if (!isValidDestination(destination)) {
            throw new IllegalArgumentException("Invalid destination: " + destination);
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.checkedIn = false;
    }

    private static boolean isValidName(String name) {
        if (name == null) return false;
        String trimmed = name.trim();
        if (trimmed.isEmpty()) return false;
        // Name must contain only alphabetic characters and spaces
        for (char c : trimmed.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidDestination(String dest) {
        if (dest == null) return false;
        return !dest.trim().isEmpty();
    }

    // Idempotent check-in state change
    public void markCheckedIn() {
        if (this.checkedIn) {
            System.out.println("Warning: Passenger " + passengerName + " is already checked in.");
            return;
        }
        this.checkedIn = true;
    }

    public String getPassengerName() { return passengerName; }
    public String getDestination() { return destination; }

    public static void processBatch(String[][] rawBookings) {
        int valid = 0;
        int rejected = 0;
        int duplicatesSkipped = 0;

        List<BusTicket> acceptedTickets = new ArrayList<>();

        if (rawBookings != null) {
            for (String[] booking : rawBookings) {
                if (booking == null || booking.length < 2) {
                    rejected++;
                    continue;
                }

                String name = booking[0];
                String dest = booking[1];

                // Attempt construction - fails fast if invalid
                BusTicket ticket;
                try {
                    ticket = new BusTicket(name, dest);
                } catch (IllegalArgumentException e) {
                    rejected++;
                    continue;
                }

                // Check for duplicate passengerName + destination pair (case-insensitive)
                boolean isDuplicate = false;
                for (BusTicket existing : acceptedTickets) {
                    if (existing.getPassengerName().equalsIgnoreCase(ticket.getPassengerName()) &&
                        existing.getDestination().equalsIgnoreCase(ticket.getDestination())) {
                        isDuplicate = true;
                        break;
                    }
                }

                if (isDuplicate) {
                    duplicatesSkipped++;
                } else {
                    acceptedTickets.add(ticket);
                    valid++;
                }
            }
        }

        System.out.printf("Valid: %d | Rejected: %d | Duplicates skipped: %d%n",
                valid, rejected, duplicatesSkipped);
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 1: Bus Ticket Booking Validator ===");

        String[][] batch1 = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };

        System.out.print("Output: ");
        processBatch(batch1);
    }
}