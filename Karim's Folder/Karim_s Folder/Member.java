import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Member {
    private String name;
    private int age;
    private int memberId;
    private String email;
    private String phoneNumber;
    private String address;
    private Status status;
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    public Member(String name, int age, int memberId, String email, String phoneNumber, String address, Status status) {
        this.name = name;
        this.age = age;
        this.memberId = memberId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
    }

    enum Status {
        SUBSCRIBED, UNSUBSCRIBED, BANNED
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        } else {
            System.out.println("Age must be positive.");
        }
    }

    public int getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addBorrowRecord(BorrowRecord record) {
        if (record != null) {
            borrowRecords.add(record);
        }
    }

    public List<BorrowRecord> getBorrowRecords() {
        return Collections.unmodifiableList(borrowRecords);
    }

    @Override
    public String toString() {
        return "Member[id=" + memberId + ", name=" + name + ", age=" + age + ", status=" + status + "]";
    }

}
