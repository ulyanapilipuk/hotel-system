package hotel.model;

/**
 * Номер отеля. Хранит только данные о номере.
 */
public class Room {

    private final int id;
    private final String number;
    private final int capacity;
    private final double pricePerNight;

    public Room(int id, String number, int capacity, double pricePerNight) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return "Room{id=" + id + ", number='" + number + "'}";
    }
}
