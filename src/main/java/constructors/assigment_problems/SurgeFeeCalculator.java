package constructors.assigment_problems;

/**
 * PROBLEM 4 • Intermediate: Exam-Week Surge Fee Calculator
 * The class and calculation method are locked with 'final'.
 */
public final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge percent cannot be negative");
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0) {
            throw new IllegalArgumentException("Order value cannot be negative: " + orderValue);
        }
        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Delay minutes cannot be negative: " + delayMinutes);
        }

        // On-time orders never incur surge fee or minimum floor
        if (delayMinutes == 0) {
            return 0.0;
        }

        // Closed-form O(1) tiered rate calculation:
        // Tier 1: minutes 1 to 5 at 0.5% per minute
        int tier1Minutes = Math.min(delayMinutes, 5);
        // Tier 2: minutes 6 to 15 at 1.0% per minute
        int tier2Minutes = Math.max(0, Math.min(delayMinutes - 5, 10));
        // Tier 3: minutes 16+ at 2.0% per minute
        int tier3Minutes = Math.max(0, delayMinutes - 15);

        double tieredSurge = (tier1Minutes * 0.005 + tier2Minutes * 0.010 + tier3Minutes * 0.020) * orderValue;

        // Configured minimum surge floor applies only when order is delayed
        double floor = (minimumSurgePercent / 100.0) * orderValue;

        return Math.max(tieredSurge, floor);
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0); // 1% minimum floor

        System.out.println("Rs " + calc.calculateSurgeFee(500, 0));  // Expected: Rs 0.0
        System.out.println("Rs " + calc.calculateSurgeFee(500, 1));  // Expected: Rs 5.0
        System.out.println("Rs " + calc.calculateSurgeFee(500, 16)); // Expected: Rs 72.5
    }
}
