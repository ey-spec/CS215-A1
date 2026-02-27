import java.time.LocalDate;

public class BorrowRecord {
    public enum Status {
        BORROWED, RETURNED
    }

    private final int recordId;
    private final Member member;
    private final Book book;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private Status status;

    private static int nextId = 1;

    public BorrowRecord(Member member, Book book) {
        this.recordId = getNextId();
        this.member = member;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusWeeks(2);
        this.returnDate = null;

        // Attempt to borrow a copy from the book; throw if none available
        if(book.borrowOne()) {
            this.status = Status.BORROWED;
        } else {
            throw new IllegalStateException("No copies available for book: " + book.getTitle());
        }

        // register with member
        member.addBorrowRecord(this);
    }

    private static synchronized int getNextId() {
        return nextId++;
    }

    public int getRecordId() {
        return recordId;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Status getStatus() {
        return status;
    }

    public void markReturned() {
        if (this.status == Status.RETURNED)
            return;
        this.returnDate = LocalDate.now();
        book.returnOne();
        this.status = Status.RETURNED;
    }

    public boolean isOverdue() {
        return status == Status.BORROWED && LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        return "BorrowRecord[id=" + recordId + ", memberId=" + member.getMemberId() + ", bookId=" + book.getId()
                + ", borrowDate=" + borrowDate + ", dueDate=" + dueDate + ", status=" + status + "]";
    }

}
