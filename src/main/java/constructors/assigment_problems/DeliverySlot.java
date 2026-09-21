package constructors.assigment_problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * PROBLEM 2 • Basic: ASAP or Scheduled — Delivery Slot Booking
 */
public class DeliverySlot {
    private static final String DEFAULT_SLOT = "ASAP";
    private static final Set<String> PEAK_HOURS = new HashSet<>(
            Arrays.asList("12:00-13:00", "13:00-14:00", "19:00-20:00", "20:00-21:00")
    );

    private final String orderId;
    private final String timeSlot;

    // Full constructor
    public DeliverySlot(String orderId, String timeSlot) {
        this.orderId = orderId;
        this.timeSlot = (timeSlot != null) ? timeSlot : DEFAULT_SLOT;
    }

    // Chained constructor: defaults to "ASAP"
    public DeliverySlot(String orderId) {
        this(orderId, DEFAULT_SLOT);
    }

    public boolean isPeakHour() {
        return PEAK_HOURS.contains(timeSlot);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public static void main(String[] args) {
        DeliverySlot slot1 = new DeliverySlot("ORD101", "13:00-14:00");
        System.out.println(slot1.isPeakHour()); // Expected: true

        DeliverySlot slot2 = new DeliverySlot("ORD102");
        System.out.println(slot2.isPeakHour()); // Expected: false
    }
}
