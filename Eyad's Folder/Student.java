import java.util.ArrayList;

public class Student {
    public String name;
    public String id;
    public String email;
    public String contact;
    public String address;
    public ArrayList<String> courseNames = new ArrayList<>();
    public ArrayList<String> grades = new ArrayList<>();
    public ArrayList<Integer> creditHours = new ArrayList<>();

    public Student(String name, String id, String email, String contact, String address) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.contact = contact;
        this.address = address;
    }

    public void addCourse(String courseName, String grade, int credits) {
        courseNames.add(courseName.trim());
        grades.add(grade.trim());
        creditHours.add(credits);
    }

    public boolean removeCourse(String courseName) {
        for (int i = 0; i < courseNames.size(); i++) {
            if (courseNames.get(i).equalsIgnoreCase(courseName)) {
                courseNames.remove(i);
                grades.remove(i);
                creditHours.remove(i);
                return true;
            }
        }
        return false;
    }

    public double calculateGPA() {
        double totalPoints = 0.0;
        int totalCredits = 0;

        for (int i = 0; i < grades.size(); i++) {
            double scale = 0.0;
            String grade = grades.get(i);
            int credits = creditHours.get(i);

            if (grade.equalsIgnoreCase("A+"))
                scale = 4.0;
            else if (grade.equalsIgnoreCase("A"))
                scale = 3.7;
            else if (grade.equalsIgnoreCase("A-"))
                scale = 3.4;
            else if (grade.equalsIgnoreCase("B+"))
                scale = 3.2;
            else if (grade.equalsIgnoreCase("B"))
                scale = 3.0;
            else if (grade.equalsIgnoreCase("B-"))
                scale = 2.8;
            else if (grade.equalsIgnoreCase("C+"))
                scale = 2.6;
            else if (grade.equalsIgnoreCase("C"))
                scale = 2.4;
            else if (grade.equalsIgnoreCase("C-"))
                scale = 2.2;
            else if (grade.equalsIgnoreCase("D+"))
                scale = 2.0;
            else if (grade.equalsIgnoreCase("D"))
                scale = 1.5;
            else if (grade.equalsIgnoreCase("D-"))
                scale = 1.0;
            else if (grade.equalsIgnoreCase("F"))
                scale = 0.0;
            else {
                System.out.println("Invalid grade '" + grade + "' ignored.");
                continue;
            }

            if (credits <= 0) {
                System.out.println("Invalid credit hours (" + credits + ") ignored.");
                continue;
            }

            totalPoints += scale * credits;
            totalCredits += credits;
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }

    public int getTotalCredits() {
        int sum = 0;
        for (int c : creditHours)
            if (c > 0)
                sum += c;
        return sum;
    }

    public void displayInfo() {
        System.out.println("-----------------------------");
        System.out.println("Name    : " + name);
        System.out.println("ID      : " + id);
        System.out.println("Email   : " + email);
        System.out.println("Contact : " + contact);
        System.out.println("Address : " + address);
        System.out.println("Courses : " + (courseNames.isEmpty() ? "None" : String.join(", ", courseNames)));
        System.out.println("Total Credit Hours: " + getTotalCredits());
        System.out.printf("GPA     : %.2f%n", calculateGPA());
        System.out.println("-----------------------------");
    }
}