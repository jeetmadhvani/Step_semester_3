package oop.assigment_problems;

/**
 * F5. Capstone: A Small HR + Parking Allocation Mini-System
 */
public class CompanyRecordSystem {

    public static class CompanyEmployeeRecord {
        private String name;
        private String empId;
        private EmployeeHierarchy.Employee employee;
        private ParkingAllocation.ParkingSlot slot;

        public static int totalRecords = 0;

        public CompanyEmployeeRecord(String name, String empId, EmployeeHierarchy.Employee employee, ParkingAllocation.ParkingSlot slot) {
            this.name = name;
            this.empId = empId;
            this.employee = employee;
            this.slot = slot;
            totalRecords++;
        }

        public String fullProfile() {
            double pay;
            if (employee instanceof EmployeeHierarchy.ManagerEmployee) {
                pay = ((EmployeeHierarchy.ManagerEmployee) employee).effectiveSalary();
            } else if (employee instanceof EmployeeHierarchy.InternEmployee) {
                pay = ((EmployeeHierarchy.InternEmployee) employee).effectiveSalary();
            } else {
                pay = employee.getSalary();
            }

            String slotInfo = (slot != null) ? slot.getSlotNo() : "no parking assigned";
            return name + " | Pay: Rs " + pay + " | Slot: " + slotInfo;
        }
    }

    public static void main(String[] args) {
        // Parking slots
        ParkingAllocation.ParkingSlot slotA1 = new ParkingAllocation.ParkingSlot("A1", 1, 0);
        ParkingAllocation.ParkingSlot slotA2 = new ParkingAllocation.ParkingSlot("A2", 1, 0);

        // Allot parking to Divya and Karan
        slotA1.allot("DL01AB1234");
        slotA2.allot("MH02CD5678");

        // Employees
        EmployeeHierarchy.Employee divya = new EmployeeHierarchy.ManagerEmployee("EMP001", "Divya", 70000.0, 8000.0);
        EmployeeHierarchy.Employee karan = new EmployeeHierarchy.Employee("EMP002", "Karan", 40000.0);
        EmployeeHierarchy.Employee meera = new EmployeeHierarchy.InternEmployee("EMP003", "Meera", 12000.0, 10000.0);

        // Records: slot allotted to 2 of them, 3rd unallotted (null)
        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "EMP001", divya, slotA1);
        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "EMP002", karan, slotA2);
        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "EMP003", meera, null);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
