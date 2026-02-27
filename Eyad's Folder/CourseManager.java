import java.util.ArrayList;

public class CourseManager {
    private ArrayList<String> catalog = new ArrayList<>();

    public boolean addCourse(String courseName) {
        for (String c : catalog)
            if (c.equalsIgnoreCase(courseName))
                return false; // already exists
        catalog.add(courseName);
        return true;
    }

    public boolean courseExists(String courseName) {
        for (String c : catalog)
            if (c.equalsIgnoreCase(courseName))
                return true;
        return false;
    }

    public ArrayList<String> getCatalog() {
        return catalog;
    }

    public int getTotalCourses() {
        return catalog.size();
    }

    public void displayCatalog() {
        if (catalog.isEmpty()) {
            System.out.println("No courses in the catalog yet.");
        } else {
            System.out.println("--- Course Catalog ---");
            for (int i = 0; i < catalog.size(); i++)
                System.out.println((i + 1) + ". " + catalog.get(i));
        }
    }
}