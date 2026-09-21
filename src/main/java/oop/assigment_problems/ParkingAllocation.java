package oop.assigment_problems;

/**
 * F3. Object References, Null Safety, and a Mutating Method
 */
public class ParkingAllocation {

    public static class ParkingSlot {
        private String slotNo;
        private int capacity;
        private int occupiedCount;

        public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        public String getSlotNo() {
            return slotNo;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getOccupiedCount() {
            return occupiedCount;
        }

        public boolean allot(String vehicleNo) {
            if (occupiedCount < capacity) {
                occupiedCount++;
                return true;
            }
            return false;
        }
    }

    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) {
            return null;
        }
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.getOccupiedCount() < slot.getCapacity()) {
                return slot;
            }
        }
        return null;
    }

    /*
     * EXPLANATION ON PASSING PARKINGSLOT ARRAY:
     * In Java, all object parameters (including arrays) are passed by value of the reference.
     * When the ParkingSlot[] array is passed into findAvailableSlot() or safeAllot(), a copy
     * of the memory address reference is passed, NOT a copy of the array contents or individual
     * ParkingSlot objects. Therefore, invoking slot.allot() mutates the exact same ParkingSlot
     * object in the caller's array on the heap.
     */
    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);
        if (slot != null) {
            boolean success = slot.allot(vehicleNo);
            if (success) {
                System.out.println(vehicleNo + " allotted to slot " + slot.getSlotNo());
            } else {
                System.out.println("No slots available for " + vehicleNo);
            }
        } else {
            System.out.println("No slots available for " + vehicleNo);
        }
    }

    public static void main(String[] args) {
        // Run 1: available slot present
        ParkingSlot[] slots1 = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots1, "TN09AB1234"); // Expected: TN09AB1234 allotted to slot A1

        // Run 2: all slots full
        ParkingSlot[] slots2 = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots2, "TN09AB1234"); // Expected: No slots available for TN09AB1234
    }
}
