package constructors.assigment_problems;

/**
 * PROBLEM 1 • Basic: Ghost Order Validator
 */
public class FoodOrder {
    private String studentName;
    private String dishName;
    private boolean delivered;

    // No usable no-arg constructor: private to prevent construction
    private FoodOrder() {}

    // Parameterized constructor validating both fields
    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student name: must not be null or blank");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid dish name: must not be null or blank");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }

    public void markDelivered() {
        if (!delivered) {
            delivered = true;
            System.out.println("Order for " + studentName + " (" + dishName + ") marked delivered.");
        } else {
            System.out.println("WARNING: Order for " + studentName + " was already marked delivered previously! Possible duplicate delivery.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        if (rawOrders == null) {
            System.out.println("Valid: 0 | Rejected: 0");
            return;
        }

        int valid = 0;
        int rejected = 0;

        for (String[] raw : rawOrders) {
            if (raw == null || raw.length < 2) {
                rejected++;
                continue;
            }
            try {
                FoodOrder order = new FoodOrder(raw[0], raw[1]);
                valid++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };
        processBatch(rawOrders); // Expected: Valid: 2 | Rejected: 2
    }
}
