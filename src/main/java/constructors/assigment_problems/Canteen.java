package constructors.assigment_problems;

import java.util.Arrays;

/**
 * PROBLEM 3 • Intermediate: Canteen Trust-Score Ranking Engine
 */
public class Canteen implements Comparable<Canteen> {
    private static final int DEFAULT_TRUST_SCORE = 3;

    private String canteenCode;
    private String canteenName;
    private int trustScore;

    // Primary constructor: resolves naming clash with 'this'
    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    // Chained constructor
    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, DEFAULT_TRUST_SCORE);
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public int getTrustScore() {
        return trustScore;
    }

    /*
     * Tie-breaking order:
     * 1. Higher trust score first (descending).
     * 2. Case-insensitive canteenCode order (ascending).
     * 3. Canteen name length (ascending).
     */
    @Override
    public int compareTo(Canteen other) {
        if (other == null) {
            return -1;
        }

        // Higher trust score first
        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }

        // Tie-breaker 1: Canteen code case-insensitively
        int codeComparison = this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeComparison != 0) {
            return codeComparison;
        }

        // Tie-breaker 2: Name length
        return Integer.compare(this.canteenName.length(), other.canteenName.length());
    }

    // Custom O(n^2) sorting implementation (no built-in sort utility)
    public static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null) {
            return new Canteen[0];
        }

        Canteen[] sorted = canteens.clone();
        int n = sorted.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (sorted[j].compareTo(sorted[j + 1]) > 0) {
                    Canteen temp = sorted[j];
                    sorted[j] = sorted[j + 1];
                    sorted[j + 1] = temp;
                }
            }
        }
        return sorted;
    }

    public static void main(String[] args) {
        Canteen[] input = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats") // defaults to trustScore 3
        };

        Canteen[] ranked = rankCanteens(input);
        String[] codes = new String[ranked.length];
        for (int i = 0; i < ranked.length; i++) {
            codes[i] = ranked[i].getCanteenCode();
        }

        System.out.println(Arrays.toString(codes)); // Expected: [hb1-c, HB2-C, HB3-C]
    }
}
