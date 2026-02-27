import java.util.ArrayList;

public class StudentRepository {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student s) {
        students.add(s);
    }

    public boolean removeStudent(String id) {
        Student s = findById(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }

    public Student findById(String id) {
        for (Student s : students)
            if (s.id.equalsIgnoreCase(id))
                return s;
        return null;
    }

    public Student findByName(String name) {
        for (Student s : students)
            if (s.name.equalsIgnoreCase(name))
                return s;
        return null;
    }

    public Student findByEmail(String email) {
        for (Student s : students)
            if (s.email.equalsIgnoreCase(email))
                return s;
        return null;
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public int getTotalStudents() {
        return students.size();
    }

    public int getTotalEnrollments() {
        int total = 0;
        for (Student s : students)
            total += s.courseNames.size();
        return total;
    }

    public ArrayList<Student> getTopStudentsByGPA(int count) {
        ArrayList<Student> sorted = new ArrayList<>(students);
        // Bubble sort descending by GPA
        for (int i = 0; i < sorted.size() - 1; i++)
            for (int j = 0; j < sorted.size() - i - 1; j++)
                if (sorted.get(j).calculateGPA() < sorted.get(j + 1).calculateGPA()) {
                    Student temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }

        ArrayList<Student> result = new ArrayList<>();
        for (int i = 0; i < Math.min(count, sorted.size()); i++)
            result.add(sorted.get(i));
        return result;
    }

    public ArrayList<Student> getStudentsInCourse(String courseName) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student s : students)
            for (String c : s.courseNames)
                if (c.equalsIgnoreCase(courseName)) {
                    result.add(s);
                    break;
                }
        return result;
    }
}