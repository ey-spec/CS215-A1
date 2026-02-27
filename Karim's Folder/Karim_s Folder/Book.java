public class Book {
    private String title;
    private String author;
    private int id;
    private int availableCopies;

    enum Category {
        FICTION, NON_FICTION, SCIENCE, HISTORY, FANTASY, BIOGRAPHY, MYSTERY, ROMANCE, THRILLER, HORROR, OTHER
    }

    private Category category;

    public Book(String title, String author, int id, int availableCopies, Category category) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.availableCopies = availableCopies;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book[id=" + id + ", title=" + title + ", author=" + author + ", available=" + availableCopies
                + ", category=" + category + "]";
    }


    public boolean borrowOne() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public void returnOne() {
        availableCopies++;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public Category getCategory() {
        return category;
    }

    public void setAvailableCopies(int availableCopies) {
        if (availableCopies >= 0) {
            this.availableCopies = availableCopies;
        } else {
            System.out.println("Available copies cannot be negative.");
        }
    }
}
