import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class Library {
    private String name;
    private String address;
    private Map<Integer, Book> books;
    private Map<Integer, Member> members;
    private Map<Integer, BorrowRecord> borrowRecords;
    
    // Constructor
    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.borrowRecords = new HashMap<>();
    }
    
    // ==================== BOOK METHODS ====================
    
    // Add a new book
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Book with ID " + book.getId() + " already exists");
        }
        books.put(book.getId(), book);
        System.out.println("Book added: " + book.getTitle());
    }
    
    // Remove a book by ID
    public boolean removeBook(int bookId) {
        Book removed = books.remove(bookId);
        if (removed != null) {
            System.out.println("Book removed: " + removed.getTitle());
            return true;
        }
        System.out.println("Book with ID " + bookId + " not found");
        return false;
    }
    
    // Remove a book by Book object
    public boolean removeBook(Book book) {
        if (book != null) {
            return removeBook(book.getId());
        }
        return false;
    }
    
    // Search books by title (partial match, case insensitive)
    public List<Book> searchBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        String searchTerm = title.toLowerCase().trim();
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    // Search books by author (partial match, case insensitive)
    public List<Book> searchBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        String searchTerm = author.toLowerCase().trim();
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    // Search books by category
    public List<Book> searchBooksByCategory(Book.Category category) {
        if (category == null) {
            return Collections.emptyList();
        }
        
        return books.values().stream()
                .filter(book -> book.getCategory() == category)
                .collect(Collectors.toList());
    }
    
    // Get all books
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    // Get a book by ID
    public Optional<Book> getBookById(int bookId) {
        return Optional.ofNullable(books.get(bookId));
    }
    
    
    // ==================== MEMBER METHODS ====================
    
    // Register a new member
    public void registerMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (members.containsKey(member.getMemberId())) {
            throw new IllegalArgumentException("Member with ID " + member.getMemberId() + " already exists");
        }
        members.put(member.getMemberId(), member);
        System.out.println("Member registered: " + member.getName());
    }
    
    // Remove a member by ID
    public boolean removeMember(int memberId) {
        // Check if member has any active borrows
        Member member = members.get(memberId);
        if (member != null) {
            boolean hasActiveBorrows = member.getBorrowRecords().stream()
                    .anyMatch(record -> record.getStatus() == BorrowRecord.Status.BORROWED);
            
            if (hasActiveBorrows) {
                System.out.println("Cannot remove member with active borrows");
                return false;
            }
        }
        
        Member removed = members.remove(memberId);
        if (removed != null) {
            System.out.println("Member removed: " + removed.getName());
            return true;
        }
        System.out.println("Member with ID " + memberId + " not found");
        return false;
    }
    
    // Find member by ID
    public Optional<Member> findMemberById(int memberId) {
        return Optional.ofNullable(members.get(memberId));
    }
    
    // Count total members
    public int getTotalMembers() {
        return members.size();
    }
    
    // Count active members (SUBSCRIBED status)
    public int getActiveMembers() {
        return (int) members.values().stream()
                .filter(m -> m.getStatus() == Member.Status.SUBSCRIBED)
                .count();
    }
    
    // Get all members
    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }
    
    // Update member status
    public void updateMemberStatus(int memberId, Member.Status newStatus) {
        findMemberById(memberId).ifPresentOrElse(
            member -> {
                member.setStatus(newStatus);
                System.out.println("Member " + member.getName() + " status updated to: " + newStatus);
            },
            () -> System.out.println("Member with ID " + memberId + " not found")
        );
    }
    
    // ==================== BORROWING METHODS ====================
    
    // Borrow a book
    public Optional<BorrowRecord> borrowBook(int memberId, int bookId) {
        Optional<Member> memberOpt = findMemberById(memberId);
        Optional<Book> bookOpt = getBookById(bookId);
        
        if (memberOpt.isEmpty()) {
            System.out.println("Member with ID " + memberId + " not found");
            return Optional.empty();
        }
        
        if (bookOpt.isEmpty()) {
            System.out.println("Book with ID " + bookId + " not found");
            return Optional.empty();
        }
        
        Member member = memberOpt.get();
        Book book = bookOpt.get();
        
        // Check if member is subscribed
        if (member.getStatus() != Member.Status.SUBSCRIBED) {
            System.out.println("Member " + member.getName() + " is not subscribed. Status: " + member.getStatus());
            return Optional.empty();
        }
        
        // Check if member already has this book borrowed
        boolean alreadyBorrowed = member.getBorrowRecords().stream()
                .anyMatch(r -> r.getBook().getId() == bookId && r.getStatus() == BorrowRecord.Status.BORROWED);
        
        if (alreadyBorrowed) {
            System.out.println("Member already has this book borrowed");
            return Optional.empty();
        }
        
        try {
            BorrowRecord record = new BorrowRecord(member, book);
            borrowRecords.put(record.getRecordId(), record);
            System.out.println("Book borrowed successfully: " + book.getTitle() + " by " + member.getName());
            return Optional.of(record);
        } catch (IllegalStateException e) {
            System.out.println("Cannot borrow book: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    // Return a book
    public boolean returnBook(int recordId) {
        BorrowRecord record = borrowRecords.get(recordId);
        
        if (record == null) {
            System.out.println("Borrow record with ID " + recordId + " not found");
            return false;
        }
        
        if (record.getStatus() == BorrowRecord.Status.RETURNED) {
            System.out.println("Book already returned on " + record.getReturnDate());
            return false;
        }
        
        record.markReturned();
        System.out.println("Book returned successfully: " + record.getBook().getTitle() + 
                          " by " + record.getMember().getName());
        
        if (record.isOverdue()) {
            System.out.println("Note: This book was overdue!");
        }
        
        return true;
    }
    
    // Return a book by member and book
    public boolean returnBook(int memberId, int bookId) {
        Optional<Member> memberOpt = findMemberById(memberId);
        
        if (memberOpt.isEmpty()) {
            System.out.println("Member with ID " + memberId + " not found");
            return false;
        }
        
        Member member = memberOpt.get();
        
        // Find the active borrow record for this book
        Optional<BorrowRecord> activeRecord = member.getBorrowRecords().stream()
                .filter(r -> r.getBook().getId() == bookId && r.getStatus() == BorrowRecord.Status.BORROWED)
                .findFirst();
        
        if (activeRecord.isEmpty()) {
            System.out.println("No active borrow record found for this member and book");
            return false;
        }
        
        return returnBook(activeRecord.get().getRecordId());
    }
    
    // Get all overdue records
    public List<BorrowRecord> getOverdueRecords() {
        return borrowRecords.values().stream()
                .filter(BorrowRecord::isOverdue)
                .collect(Collectors.toList());
    }
    
    // Get all borrow records for a member
    public List<BorrowRecord> getMemberBorrowRecords(int memberId) {
        return findMemberById(memberId)
                .map(Member::getBorrowRecords)
                .orElse(Collections.emptyList());
    }
    
    // Get active borrows for a member
    public List<BorrowRecord> getMemberActiveBorrows(int memberId) {
        return findMemberById(memberId)
                .map(member -> member.getBorrowRecords().stream()
                        .filter(r -> r.getStatus() == BorrowRecord.Status.BORROWED)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
    
    // Get all borrow records
    public List<BorrowRecord> getAllBorrowRecords() {
        return new ArrayList<>(borrowRecords.values());
    }
    
    // ==================== UTILITY METHODS ====================
    
    // Get library statistics
    public Map<String, Object> getLibraryStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("libraryName", name);
        stats.put("totalBooks", books.size());
        stats.put("totalMembers", members.size());
        stats.put("totalBorrowRecords", borrowRecords.size());
        
        long activeBorrows = borrowRecords.values().stream()
                .filter(r -> r.getStatus() == BorrowRecord.Status.BORROWED)
                .count();
        stats.put("activeBorrows", activeBorrows);
        
        long overdueBorrows = getOverdueRecords().size();
        stats.put("overdueBorrows", overdueBorrows);
        
        int totalAvailableCopies = books.values().stream()
                .mapToInt(Book::getAvailableCopies)
                .sum();
        stats.put("totalAvailableCopies", totalAvailableCopies);
        
        return stats;
    }
    
    // Display all books
    public void displayAllBooks() {
        System.out.println("\n=== All Books in " + name + " ===");
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            books.values().forEach(System.out::println);
        }
        System.out.println("Total: " + books.size() + " books");
    }
    
    // Display all members
    public void displayAllMembers() {
        System.out.println("\n=== All Members of " + name + " ===");
        if (members.isEmpty()) {
            System.out.println("No members registered.");
        } else {
            members.values().forEach(System.out::println);
        }
        System.out.println("Total: " + members.size() + " members");
    }
    
    // Display all active borrows
    public void displayActiveBorrows() {
        System.out.println("\n=== Active Borrows ===");
        List<BorrowRecord> activeBorrows = borrowRecords.values().stream()
                .filter(r -> r.getStatus() == BorrowRecord.Status.BORROWED)
                .collect(Collectors.toList());
        
        if (activeBorrows.isEmpty()) {
            System.out.println("No active borrows.");
        } else {
            activeBorrows.forEach(System.out::println);
        }
        System.out.println("Total: " + activeBorrows.size() + " active borrows");
    }
    
    // Display overdue records
    public void displayOverdueRecords() {
        System.out.println("\n=== Overdue Records ===");
        List<BorrowRecord> overdue = getOverdueRecords();
        
        if (overdue.isEmpty()) {
            System.out.println("No overdue records.");
        } else {
            overdue.forEach(record -> {
                System.out.println(record);
                System.out.println("  Member: " + record.getMember().getName());
                System.out.println("  Days overdue: " + 
                    java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(), LocalDate.now()));
            });
        }
        System.out.println("Total: " + overdue.size() + " overdue records");
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
}