import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagementSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentRepository repo = new StudentRepository();
        CourseManager courseManager = new CourseManager();

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1.  Add Student");
            System.out.println("2.  Add Course to Student");
            System.out.println("3.  Show Student Info");
            System.out.println("4.  Edit Student Info");
            System.out.println("5.  Delete Student");
            System.out.println("6.  Search Student");
            System.out.println("7.  Remove Course from Student");
            System.out.println("8.  View Course Catalog");
            System.out.println("9.  Add Course to Catalog");
            System.out.println("10. Top Students by GPA");
            System.out.println("11. Students Enrolled in a Course");
            System.out.println("12. Count Total Students and Courses");
            System.out.println("13. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.hasNextInt() ? sc.nextInt() : -1;
            sc.nextLine();

            // 1. Add Student
            if (choice == 1) {
                System.out.print("Enter student name: ");
                String name = sc.nextLine().trim();
                System.out.print("Enter student ID: ");
                String id = sc.nextLine().trim();

                if (repo.findById(id) != null) {
                    System.out.println("A student with this ID already exists!");
                    continue;
                }

                System.out.print("Enter email: ");
                String email = sc.nextLine().trim();
                System.out.print("Enter contact number: ");
                String contact = sc.nextLine().trim();
                System.out.print("Enter address: ");
                String address = sc.nextLine().trim();

                repo.addStudent(new Student(name, id, email, contact, address));
                System.out.println("Student added successfully!");

                // 2. Add Course to Student
            } else if (choice == 2) {
                System.out.print("Enter student ID: ");
                String id = sc.nextLine().trim();
                Student found = repo.findById(id);

                if (found != null) {
                    System.out.print("Enter course name: ");
                    String courseName = sc.nextLine().trim();
                    System.out.print("Enter grade: ");
                    String grade = sc.nextLine().trim();
                    System.out.print("Enter credit hours: ");
                    int credits = sc.hasNextInt() ? sc.nextInt() : -1;
                    sc.nextLine();

                    if (credits <= 0) {
                        System.out.println("Credit hours must be a positive number!");
                        continue;
                    }

                    found.addCourse(courseName, grade, credits);
                    courseManager.addCourse(courseName); // auto-add to catalog
                    System.out.println("Course added successfully!");
                } else {
                    System.out.println("Student not found!");
                }

                // 3. Show Student Info
            } else if (choice == 3) {
                System.out.print("Enter student ID: ");
                String id = sc.nextLine().trim();
                Student found = repo.findById(id);
                if (found != null)
                    found.displayInfo();
                else
                    System.out.println("Student not found!");

                // 4. Edit Student Info
            } else if (choice == 4) {
                System.out.print("Enter student ID to edit: ");
                String id = sc.nextLine().trim();
                Student found = repo.findById(id);

                if (found != null) {
                    System.out.println("Leave a field blank to keep the current value.");

                    System.out.print("New name (" + found.name + "): ");
                    String newName = sc.nextLine().trim();
                    if (!newName.isEmpty())
                        found.name = newName;

                    System.out.print("New email (" + found.email + "): ");
                    String newEmail = sc.nextLine().trim();
                    if (!newEmail.isEmpty())
                        found.email = newEmail;

                    System.out.print("New contact (" + found.contact + "): ");
                    String newContact = sc.nextLine().trim();
                    if (!newContact.isEmpty())
                        found.contact = newContact;

                    System.out.print("New address (" + found.address + "): ");
                    String newAddress = sc.nextLine().trim();
                    if (!newAddress.isEmpty())
                        found.address = newAddress;

                    System.out.println("Student info updated successfully!");
                } else {
                    System.out.println("Student not found!");
                }

                // 5. Delete Student
            } else if (choice == 5) {
                System.out.print("Enter student ID to delete: ");
                String id = sc.nextLine().trim();
                Student found = repo.findById(id);

                if (found != null) {
                    System.out.print("Are you sure you want to delete " + found.name + "? (yes/no): ");
                    String confirm = sc.nextLine().trim();
                    if (confirm.equalsIgnoreCase("yes")) {
                        repo.removeStudent(id);
                        System.out.println("Student deleted successfully!");
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                } else {
                    System.out.println("Student not found!");
                }

                // 6. Search Student
            } else if (choice == 6) {
                System.out.println("Search by: 1) Name  2) ID  3) Email");
                System.out.print("Choose: ");
                int searchChoice = sc.hasNextInt() ? sc.nextInt() : -1;
                sc.nextLine();

                Student result = null;

                if (searchChoice == 1) {
                    System.out.print("Enter name: ");
                    result = repo.findByName(sc.nextLine().trim());
                } else if (searchChoice == 2) {
                    System.out.print("Enter ID: ");
                    result = repo.findById(sc.nextLine().trim());
                } else if (searchChoice == 3) {
                    System.out.print("Enter email: ");
                    result = repo.findByEmail(sc.nextLine().trim());
                } else {
                    System.out.println("Invalid choice.");
                    continue;
                }

                if (result != null)
                    result.displayInfo();
                else
                    System.out.println("Student not found!");

                // 7. Remove Course from Student
            } else if (choice == 7) {
                System.out.print("Enter student ID: ");
                String id = sc.nextLine().trim();
                Student found = repo.findById(id);

                if (found != null) {
                    if (found.courseNames.isEmpty()) {
                        System.out.println("This student has no courses.");
                        continue;
                    }
                    System.out.println("Enrolled courses: " + String.join(", ", found.courseNames));
                    System.out.print("Enter course name to remove: ");
                    String courseName = sc.nextLine().trim();

                    if (found.removeCourse(courseName))
                        System.out.println("Course removed successfully!");
                    else
                        System.out.println("Course not found for this student!");
                } else {
                    System.out.println("Student not found!");
                }

                // 8. View Course Catalog
            } else if (choice == 8) {
                courseManager.displayCatalog();

                // 9. Add Course to Catalog
            } else if (choice == 9) {
                System.out.print("Enter course name to add to catalog: ");
                String courseName = sc.nextLine().trim();
                if (courseManager.addCourse(courseName))
                    System.out.println("Course added to catalog!");
                else
                    System.out.println("Course already exists in catalog.");

                // 10. Top Students by GPA
            } else if (choice == 10) {
                if (repo.getTotalStudents() == 0) {
                    System.out.println("No students available.");
                    continue;
                }

                System.out.print("How many top students to show? ");
                int top = sc.hasNextInt() ? sc.nextInt() : 3;
                sc.nextLine();

                ArrayList<Student> topStudents = repo.getTopStudentsByGPA(top);
                System.out.println("--- Top " + top + " Students by GPA ---");
                for (int i = 0; i < topStudents.size(); i++)
                    System.out.printf("%d. %s (ID: %s) - GPA: %.2f%n",
                            i + 1, topStudents.get(i).name, topStudents.get(i).id,
                            topStudents.get(i).calculateGPA());

                // 11. Students Enrolled in a Course
            } else if (choice == 11) {
                System.out.print("Enter course name: ");
                String courseName = sc.nextLine().trim();
                ArrayList<Student> enrolled = repo.getStudentsInCourse(courseName);

                System.out.println("--- Students enrolled in " + courseName + " ---");
                if (enrolled.isEmpty())
                    System.out.println("No students found for this course.");
                else
                    for (Student s : enrolled)
                        System.out.println("- " + s.name + " (ID: " + s.id + ")");

                // 12. Count Total Students and Courses
            } else if (choice == 12) {
                System.out.println("Total Students           : " + repo.getTotalStudents());
                System.out.println("Total Courses in Catalog : " + courseManager.getTotalCourses());
                System.out.println("Total Course Enrollments : " + repo.getTotalEnrollments());

                // 13. Exit
            } else if (choice == 13) {
                System.out.println("Exiting program. Goodbye!");
                break;

            } else {
                System.out.println("Invalid choice, try again.");
            }
        }

        sc.close();
    }
}