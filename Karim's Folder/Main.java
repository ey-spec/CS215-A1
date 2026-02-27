import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library("City Library", "100 Library Ave");
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Select an option: ");
            String line = scanner.nextLine().trim();
            switch (line) {
                case "1":
                    addBookFlow(library, scanner);
                    break;
                case "2":
                    removeBookFlow(library, scanner);
                    break;
                case "3":
                    searchBooksFlow(library, scanner);
                    break;
                case "4":
                    registerMemberFlow(library, scanner);
                    break;
                case "5":
                    removeMemberFlow(library, scanner);
                    break;
                case "6":
                    borrowBookFlow(library, scanner);
                    break;
                case "7":
                    returnBookFlow(library, scanner);
                    break;
                case "8":
                    library.displayAllBooks();
                    break;
                case "9":
                    library.displayAllMembers();
                    break;
                case "10":
                    library.displayActiveBorrows();
                    break;
                case "11":
                    library.displayOverdueRecords();
                    break;
                case "12":
                    printStatistics(library);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option, please try again.");
            }
            System.out.println();
        }
        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("--- Library Management ---");
        System.out.println("1. Add book");
        System.out.println("2. Remove book");
        System.out.println("3. Search books");
        System.out.println("4. Register member");
        System.out.println("5. Remove member");
        System.out.println("6. Borrow book");
        System.out.println("7. Return book");
        System.out.println("8. Display all books");
        System.out.println("9. Display all members");
        System.out.println("10. Display active borrows");
        System.out.println("11. Display overdue records");
        System.out.println("12. Show statistics");
        System.out.println("0. Exit");
    }

    private static void addBookFlow(Library library, Scanner scanner) {
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Author: ");
            String author = scanner.nextLine();
            System.out.print("ID (integer): ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Available copies: ");
            int copies = Integer.parseInt(scanner.nextLine());
            System.out.println("Categories: " + java.util.Arrays.toString(Book.Category.values()));
            System.out.print("Category: ");
            String catStr = scanner.nextLine().toUpperCase();
            Book.Category category = Book.Category.valueOf(catStr);
            library.addBook(new Book(title, author, id, copies, category));
        } catch (Exception e) {
            System.out.println("Failed to add book: " + e.getMessage());
        }
    }

    private static void removeBookFlow(Library library, Scanner scanner) {
        System.out.print("Book ID to remove: ");
        try {
            int bookId = Integer.parseInt(scanner.nextLine());
            library.removeBook(bookId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID");
        }
    }

    private static void searchBooksFlow(Library library, Scanner scanner) {
        System.out.println("Search by: 1) Title  2) Author  3) Category");
        String opt = scanner.nextLine();
        List<Book> results = List.of();
        switch (opt) {
            case "1":
                System.out.print("Enter title keyword: ");
                results = library.searchBooksByTitle(scanner.nextLine());
                break;
            case "2":
                System.out.print("Enter author keyword: ");
                results = library.searchBooksByAuthor(scanner.nextLine());
                break;
            case "3":
                System.out.println("Categories: " + java.util.Arrays.toString(Book.Category.values()));
                System.out.print("Enter category: ");
                try {
                    Book.Category cat = Book.Category.valueOf(scanner.nextLine().toUpperCase());
                    results = library.searchBooksByCategory(cat);
                } catch (IllegalArgumentException e) {
                    System.out.println("Unknown category");
                }
                break;
        }
        System.out.println("Found " + results.size() + " books");
        results.forEach(System.out::println);
    }

    private static void registerMemberFlow(Library library, Scanner scanner) {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Member ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();
            System.out.print("Status (SUBSCRIBED/UNSUBSCRIBED/BANNED): ");
            Member.Status status = Member.Status.valueOf(scanner.nextLine().toUpperCase());
            library.registerMember(new Member(name, age, id, email, phone, address, status));
        } catch (Exception e) {
            System.out.println("Failed to register member: " + e.getMessage());
        }
    }

    private static void removeMemberFlow(Library library, Scanner scanner) {
        System.out.print("Member ID to remove: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            library.removeMember(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID");
        }
    }

    private static void borrowBookFlow(Library library, Scanner scanner) {
        try {
            System.out.print("Member ID: ");
            int mid = Integer.parseInt(scanner.nextLine());
            System.out.print("Book ID: ");
            int bid = Integer.parseInt(scanner.nextLine());
            library.borrowBook(mid, bid);
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input");
        }
    }

    private static void returnBookFlow(Library library, Scanner scanner) {
        System.out.print("Borrow record ID (or press Enter to search by member/book): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                int rid = Integer.parseInt(input);
                library.returnBook(rid);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID");
                return;
            }
        }
        System.out.print("Member ID: ");
        try {
            int mid = Integer.parseInt(scanner.nextLine());
            System.out.print("Book ID: ");
            int bid = Integer.parseInt(scanner.nextLine());
            library.returnBook(mid, bid);
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input");
        }
    }

    private static void printStatistics(Library library) {
        System.out.println("=== Library Statistics ===");
        library.getLibraryStatistics().forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
