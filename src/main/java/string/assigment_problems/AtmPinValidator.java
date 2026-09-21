package string.assigment_problems;

/**
 * Problem 1: ATM PIN Length Validator
 * Validates that an entered PIN string is exactly 4 digits long.
 */
public class AtmPinValidator {

    public static void checkPinLength(String pin) {
        if (pin == null || pin.length() != 4) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static void main(String[] args) {
        // Sample test cases
        checkPinLength("482");   // Expected: Invalid PIN — must be exactly 4 digits.
        checkPinLength("4820");  // Expected: PIN length OK.
    }
}
