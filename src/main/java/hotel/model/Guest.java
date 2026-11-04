package hotel.model;

/**
 * Гость отеля. Хранит только данные о госте — никакой бизнес-логики.
 */
public class Guest {

    private final int id;
    private final String fullName;
    private final String phone;

    public Guest(int id, String fullName, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Guest{id=" + id + ", fullName='" + fullName + "'}";
    }
}
