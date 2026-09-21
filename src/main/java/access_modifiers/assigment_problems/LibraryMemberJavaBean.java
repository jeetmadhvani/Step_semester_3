package access_modifiers.assigment_problems;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PROBLEM 4 • Intermediate → Advanced: LibraryMember JavaBean, Chained Constructors & Security Answer
 */
public class LibraryMemberJavaBean {

    public static class LibraryMember {
        private String membershipId;
        private String name;
        private boolean premiumMember;
        private String securityAnswerHash;
        private boolean membershipIdInitialized = false;

        // 1. No-arg constructor
        public LibraryMember() {
            this(null, null);
        }

        // 2. Name-only constructor
        public LibraryMember(String name) {
            this(null, name);
        }

        // 3. Id and Name constructor: primary initialization path
        public LibraryMember(String membershipId, String name) {
            this.name = name;
            if (membershipId != null) {
                this.membershipId = membershipId;
                this.membershipIdInitialized = true;
            }
        }

        public String getMembershipId() {
            return membershipId;
        }

        // Write-once setter
        public void setMembershipId(String id) {
            if (!membershipIdInitialized) {
                this.membershipId = id;
                this.membershipIdInitialized = true;
            }
            // Silently ignored if already initialized
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            this.premiumMember = premium;
        }

        // Write-only property: deterministic one-way SHA-256 hash, no getter anywhere
        public void setSecurityAnswer(String answer) {
            if (answer == null) {
                this.securityAnswerHash = null;
                return;
            }
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(answer.getBytes(StandardCharsets.UTF_8));
                StringBuilder hex = new StringBuilder();
                for (byte b : hash) {
                    hex.append(String.format("%02x", b));
                }
                this.securityAnswerHash = hex.toString();
            } catch (NoSuchAlgorithmException e) {
                this.securityAnswerHash = Integer.toHexString(answer.hashCode());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new LibraryMember("Priya Nair").getMembershipId()); // Expected: null
        System.out.println(new LibraryMember("LIB-8841", "Priya Nair").getMembershipId()); // Expected: "LIB-8841"

        LibraryMember m = new LibraryMember();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000"); // Silently ignored
        System.out.println(m.getMembershipId()); // Expected: "LIB-8841"
    }
}
