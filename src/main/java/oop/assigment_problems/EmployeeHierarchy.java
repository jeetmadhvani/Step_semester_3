package oop.assigment_problems;

/**
 * F2. Extending Employee Without Touching It
 */
public class EmployeeHierarchy {

    public static class Employee {
        private String empId;
        private String empName;
        private double salary;

        public Employee(String empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        public double getSalary() {
            return salary;
        }

        public String getEmpId() {
            return empId;
        }

        public String getEmpName() {
            return empName;
        }
    }

    public static class ManagerEmployee extends Employee {
        private double teamBonus;

        public ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        public double effectiveSalary() {
            return getSalary() + teamBonus;
        }
    }

    public static class InternEmployee extends Employee {
        private double stipendCap;

        public InternEmployee(String empId, String empName, double salary, double stipendCap) {
            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        public double effectiveSalary() {
            return Math.min(getSalary(), stipendCap);
        }
    }

    public static void main(String[] args) {
        Employee plain = new Employee("EMP101", "Plain Employee", 40000);
        Employee manager = new ManagerEmployee("MGR201", "Manager", 70000, 8000);
        Employee intern = new InternEmployee("INT301", "Intern", 12000, 10000);

        Employee[] employees = { plain, manager, intern };

        for (Employee emp : employees) {
            if (emp instanceof ManagerEmployee) {
                ManagerEmployee m = (ManagerEmployee) emp;
                System.out.println("Manager effective pay: Rs " + m.effectiveSalary());
            } else if (emp instanceof InternEmployee) {
                InternEmployee i = (InternEmployee) emp;
                System.out.println("Intern effective pay: Rs " + i.effectiveSalary());
            } else if (emp instanceof Employee) {
                System.out.println("Plain employee pay: Rs " + emp.getSalary());
            }
        }
    }
}
