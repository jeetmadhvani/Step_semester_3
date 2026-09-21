package oop.assigment_problems;

/**
 * F1. From Procedural Mess to a Working Library Fine System
 */
public class BookIssue {
    private String title;
    private String borrowerName;
    private int daysOverdue;

    public BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    public String getTitle() {
        return title;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public int getDaysOverdue() {
        return daysOverdue;
    }

    // Instance method: depends on each individual book's overdue status
    public double fineAmount() {
        return daysOverdue > 0 ? daysOverdue * 5.0 : 0.0;
    }

    // Instance method: checks if this particular book is severely overdue
    public boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    /*
     * JUSTIFICATION:
     * totalFineCollected is marked 'static' because calculating the aggregate fine across
     * multiple book issues is a batch/utility operation that belongs to the library system
     * as a whole, rather than to any single book issue instance.
     *
     * In contrast, fineAmount() is an instance method because it directly computes the fine
     * for a specific book based on that individual book's daysOverdue field.
     */
    public static double totalFineCollected(BookIssue[] issues) {
        if (issues == null) {
            return 0.0;
        }
        double total = 0.0;
        for (BookIssue issue : issues) {
            if (issue != null) {
                total += issue.fineAmount();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Student 1", 18),
            new BookIssue("Effective Java", "Student 2", 5),
            new BookIssue("Refactoring", "Student 3", 0),
            new BookIssue("DSA Handbook", "Student 4", 21),
            new BookIssue("Design Patterns", "Student 5", 9)
        };

        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.println(issue.getTitle() + " - " + issue.getDaysOverdue() + " days - " + status);
        }

        double total = BookIssue.totalFineCollected(issues);
        System.out.println("Total fine collected: Rs " + total);
    }
}
