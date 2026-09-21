package constructors.assigment_problems;

/**
 * PROBLEM 5 • Advanced: Nightly Multi-Kitchen Reconciliation Engine
 */
public class KitchenReconciliation {

    public static class DeliveryAccount {
        // One-time class-level state initialized via static block
        private static final double STANDARD_DISCOUNT_RATE;
        static {
            STANDARD_DISCOUNT_RATE = 0.0;
            // Static configuration can also load system rules or environment flags
        }

        private final String studentId;
        private final double orderValue;

        public DeliveryAccount(String studentId, double orderValue) {
            this.studentId = studentId;
            this.orderValue = orderValue;
        }

        // Chained constructor
        public DeliveryAccount(String studentId) {
            this(studentId, 0.0);
        }

        public String getStudentId() {
            return studentId;
        }

        public double getOrderValue() {
            return orderValue;
        }

        // Reusing tiered calculation from Problem 4
        public final double calculateSurgeFee(int delayMinutes) {
            SurgeFeeCalculator calculator = new SurgeFeeCalculator(1.0);
            return calculator.calculateSurgeFee(this.orderValue, delayMinutes);
        }
    }

    public static class Premium extends DeliveryAccount {
        public Premium(String studentId, double orderValue) {
            super(studentId, orderValue);
        }
    }

    /*
     * JUSTIFICATION FOR ARRAY LENGTH MISMATCH:
     * As an operations manager, parallel array length mismatches indicate an upstream data corruption
     * or partial packet drop. Rather than crashing the entire reconciliation run mid-way or processing
     * truncated data silently, we safely process up to Math.min length while logging a critical operational
     * alert so the missing records can be investigated and resynced.
     */
    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        int processedCount = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotalSurgeFees = 0.0;

        int limit = accounts.length;
        if (amounts.length != limit || delayMinutesArray.length != limit) {
            limit = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));
            System.err.println("OPERATIONAL WARNING: Array length mismatch detected. Processing up to safe limit: " + limit);
        }

        for (int i = 0; i < limit; i++) {
            DeliveryAccount account = accounts[i];
            if (account == null) {
                nullSkipped++;
                continue;
            }

            int delay = delayMinutesArray[i];
            double surgeFee = account.calculateSurgeFee(delay);

            if (account instanceof Premium) {
                premiumCount++;
                // Premium accounts get special discount (e.g. 50% discount on surge fees)
                surgeFee = surgeFee * 0.5;
            } else if (account instanceof DeliveryAccount) {
                regularCount++;
            }

            grandTotalSurgeFees += surgeFee;
            processedCount++;
        }

        System.out.println(processedCount + " processed | " +
                nullSkipped + " null skipped | " +
                premiumCount + " premium | " +
                regularCount + " regular | grand total surge fees = Rs " + grandTotalSurgeFees);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new Premium("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        processBatch(accounts, amounts, delayMinutesArray);
    }
}
