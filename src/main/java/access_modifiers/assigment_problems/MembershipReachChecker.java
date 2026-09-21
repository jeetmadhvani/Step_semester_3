package access_modifiers.assigment_problems;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * PROBLEM 1 • Basic → Intermediate: Membership Field Reach Checker
 */
public class MembershipReachChecker {

    public static class LibraryMember {
        // Four fields with chosen access levels
        private String membershipId;
        String branchCode;              // default / package-private
        protected double finesOwed;
        public String displayName;

        // No usable no-arg constructor
        private LibraryMember() {}

        public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
            if (membershipId == null || membershipId.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid membershipId: must be at least 4 non-whitespace characters");
            }
            this.membershipId = membershipId.trim();
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }

        public String getMembershipId() {
            return membershipId;
        }
    }

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        switch (fieldModifier) {
            case "public":
                return "ALLOWED";
            case "protected":
                if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";
            case "default":
                if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";
            case "private":
                if ("SAME_CLASS".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";
            default:
                return "DENIED";
        }
    }

    public static String summarizeByModifier(String[][] attempts) {
        Map<String, int[]> counts = new LinkedHashMap<>();
        counts.put("private", new int[]{0, 0});   // [allowed, denied]
        counts.put("default", new int[]{0, 0});
        counts.put("protected", new int[]{0, 0});
        counts.put("public", new int[]{0, 0});

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String modifier = attempt[0];
                    String context = attempt[1];
                    String decision = classifyAccess(modifier, context);

                    int[] stats = counts.get(modifier);
                    if (stats != null) {
                        if ("ALLOWED".equals(decision)) {
                            stats[0]++;
                        } else {
                            stats[1]++;
                        }
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, int[]> entry : counts.entrySet()) {
            if (!first) {
                sb.append(" | ");
            }
            first = false;
            sb.append(entry.getKey()).append(": ")
              .append(entry.getValue()[0]).append(" allowed / ")
              .append(entry.getValue()[1]).append(" denied");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS")); // Expected: ALLOWED
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE")); // Expected: DENIED

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeByModifier(attempts));
        // Expected: "private: 1 allowed / 1 denied | default: 1 allowed / 1 denied | protected: 2 allowed / 0 denied | public: 1 allowed / 0 denied"

        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
            System.out.println("construction succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected"); // Expected
        }
    }
}
