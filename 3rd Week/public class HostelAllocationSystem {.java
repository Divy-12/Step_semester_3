public class HostelAllocationSystem {

    static class HostelRoom {
        private String roomNo;
        private int beds;
        private int occupied;

        public HostelRoom(String roomNo, int beds, int occupied) {
            this.roomNo = roomNo;
            this.beds = beds;
            this.occupied = occupied;
        }

        public boolean allot(String name) {
            if (occupied < beds) {
                occupied++;
                System.out.println(name + " allotted to room " + roomNo);
                return true;
            }
            return false;
        }

        public boolean isFull() {
            return occupied >= beds;
        }

        public String getRoomNo() {
            return roomNo;
        }
    }

    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) return null;
        for (HostelRoom room : rooms) {
            if (room != null && !room.isFull()) {
                return room; // Returns reference to the first room with free capacity
            }
        }
        return null;
    }

    public static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom availableRoom = findAvailableRoom(rooms);
        
        // Explicit null check guarantees complete NPE protection
        if (availableRoom != null) {
            availableRoom.allot(studentName);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    /*
     * ARCHITECTURAL EXPLANATION:
     * In Java, objects are passed by reference value. Passing the 'HostelRoom[]' array 
     * into 'safeAllot' copies the memory references pointing to the array and room objects, 
     * NOT the actual room objects themselves. Consequently, calling 'availableRoom.allot()' 
     * directly mutates the state of the original 'HostelRoom' object stored in memory.
     */

    public static void main(String[] args) {
        // Test Case 1: Array with an available room
        HostelRoom[] set1 = new HostelRoom[] {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };

        System.out.println("Test 1 (Available room exists):");
        safeAllot(set1, "Divya");

        System.out.println();

        // Test Case 2: Array where all rooms are full
        HostelRoom[] set2 = new HostelRoom[] {
            new HostelRoom("C-214", 3, 3),
            new HostelRoom("C-507", 2, 2)
        };

        System.out.println("Test 2 (All rooms full):");
        safeAllot(set2, "Divya");
    }
}