package access_modifiers.assigment_problems;

import java.util.Arrays;

/**
 * PROBLEM 5 • Advanced: Immutable Loan Receipt & Nightly Circulation Ledger
 */
public class ImmutableCirculation {

    public static class LoanReceipt {
        private static final String SYSTEM_PREFIX;
        static {
            SYSTEM_PREFIX = "BK-";
        }

        private final String memberId;
        private final String[] bookIds;

        public LoanReceipt(String memberId, String[] bookIds) {
            if (bookIds == null) {
                throw new IllegalArgumentException("bookIds array cannot be null");
            }

            // Validate all book IDs match "BK-" followed by exactly 3 digits
            for (String bookId : bookIds) {
                if (bookId == null || !bookId.matches("^BK-\\d{3}$")) {
                    throw new IllegalArgumentException("Invalid book ID format: " + bookId + " (Must be BK- followed by 3 digits)");
                }
            }

            this.memberId = memberId;
            // Defensive copying on input
            this.bookIds = bookIds.clone();
        }

        public String getMemberId() {
            return memberId;
        }

        // Defensive copying on output
        public String[] getBookIds() {
            return bookIds.clone();
        }

        // Wither method: returns brand-new immutable instance
        public LoanReceipt withCorrectedBookId(int index, String newId) {
            if (index < 0 || index >= bookIds.length) {
                throw new IndexOutOfBoundsException("Invalid index: " + index);
            }
            if (newId == null || !newId.matches("^BK-\\d{3}$")) {
                throw new IllegalArgumentException("Invalid book ID format: " + newId);
            }

            String[] newBookIds = bookIds.clone();
            newBookIds[index] = newId;
            return new LoanReceipt(this.memberId, newBookIds);
        }
    }

    public static final class ReferenceOnlyLoanReceipt extends LoanReceipt {
        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            this.roomNumber = roomNumber;
        }

        public String getRoomNumber() {
            return roomNumber;
        }
    }

    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        int processed = 0;
        int nullSkipped = 0;
        int refOnlyCount = 0;
        int regularCount = 0;

        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                refOnlyCount++;
            } else if (receipt instanceof LoanReceipt) {
                regularCount++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " +
               refOnlyCount + " reference-only | " + regularCount + " regular";
    }

    public static void main(String[] args) {
        // Example 1: Format validation
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
            System.out.println("construction succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected"); // Expected
        }

        // Example 2: Immutability / defensive copying
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println(r.getBookIds()[0]); // Expected: "BK-100"

        // Example 3: Nightly circulation reconciliation
        LoanReceipt[] batch = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(processNightlyCirculation(batch));
        // Expected: "2 processed | 1 null skipped | 1 reference-only | 1 regular"
    }
}
