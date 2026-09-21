package oop.assigment_problems;

/**
 * F4. Designing the Instance/Static Boundary for a Library Membership System
 */
public class LibraryMembership {

    /*
     * BROKEN VERSION REPRODUCING THE STATIC-MISUSE BUG:
     * Marking name, memberId, and booksIssued as static causes every instance of
     * LibraryMember to share the exact same variable in memory.
     *
     * RATIONALE FOR EACH FIELD:
     * - name: A member's name is unique to that individual. Marking it static means
     *   creating a second member overwrites the first member's name everywhere.
     * - memberId: A member ID uniquely identifies one patron. Marking it static destroys
     *   member identity, assigning all members whichever ID was created last.
     * - booksIssued: Each member tracks their own checked-out books. If static, any book
     *   issued by one student increments or overwrites every other student's loan tally.
     */
    public static class BrokenLibraryMember {
        public static String name;
        public static String memberId;
        public static int booksIssued;

        public BrokenLibraryMember(String name, String memberId, int booksIssued) {
            BrokenLibraryMember.name = name;
            BrokenLibraryMember.memberId = memberId;
            BrokenLibraryMember.booksIssued = booksIssued;
        }
    }

    // CORRECT REDESIGNED VERSION:
    public static class LibraryMember {
        // Instance fields: unique per member
        private String name;
        private String memberId;
        private int booksIssued;

        // Static fields: shared across all members of the library
        public static String libraryName = "City Central Library";
        private static int memberCount = 1000;

        public LibraryMember(String name, int booksIssued) {
            memberCount++;
            this.memberId = "LM-" + memberCount;
            this.name = name;
            this.booksIssued = booksIssued;
        }

        public String getName() {
            return name;
        }

        public String getMemberId() {
            return memberId;
        }

        public int getBooksIssued() {
            return booksIssued;
        }

        public void printMemberCard() {
            System.out.println(name + " | " + memberId);
        }

        public static void printTotalMembers() {
            System.out.println("Total members: " + (memberCount - 1000));
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Broken version: ---");
        BrokenLibraryMember b1 = new BrokenLibraryMember("Aditi", "LM-01", 1);
        BrokenLibraryMember b2 = new BrokenLibraryMember("Rohan", "LM-02", 2);
        System.out.println(BrokenLibraryMember.name);
        System.out.println(BrokenLibraryMember.name);
        System.out.println("(Aditi's data was overwritten — both members now show \"Rohan\")");

        System.out.println("\n--- Fixed version: ---");
        LibraryMember m1 = new LibraryMember("Aditi", 1);
        LibraryMember m2 = new LibraryMember("Rohan", 2);
        m1.printMemberCard();
        m2.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}
