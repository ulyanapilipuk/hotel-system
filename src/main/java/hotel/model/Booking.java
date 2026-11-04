package hotel.model;

import java.time.LocalDate;

/**
 * Бронирование номера гостем на определённый период.
 * Период задаётся полуинтервалом [checkIn, checkOut] —
 * дата выезда не включается, поэтому заезд в день выезда предыдущего
 * бронирования допустим.
 */
public class Booking {

    private final int id;
    private final Guest guest;
    private final Room room;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public Booking(int id, Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        if (!checkIn.isBefore(checkOut)) {
            throw new IllegalArgumentException("Дата заезда должна быть раньше даты выезда");
        }
        this.id = id;
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    /**
     * Проверяет, пересекается ли период этого бронирования
     * с указанным периодом [otherCheckIn, otherCheckOut].
     */
    public boolean overlaps(LocalDate otherCheckIn, LocalDate otherCheckOut) {
        return checkIn.isBefore(otherCheckOut) && otherCheckIn.isBefore(checkOut);
    }

    @Override
    public String toString() {
        return "Booking{id=" + id + ", room=" + room.getNumber()
                + ", guest=" + guest.getFullName()
                + ", " + checkIn + " -> " + checkOut + "}";
    }
}
