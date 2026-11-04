package hotel.service;

import hotel.model.Booking;
import hotel.model.Guest;
import hotel.model.Room;
import hotel.repository.BookingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Основная бизнес-логика отеля.
 * Не создаёт свои зависимости самостоятельно — BookingRepository
 * передаётся извне через конструктор (Constructor Injection).
 */
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Создаёт бронирование номера, если на указанные даты номер свободен.
     *
     * Индивидуальное задание варианта 7:
     * запрещаем бронирование, если период пересекается
     * с уже существующим бронированием этого же номера.
     */
    public Booking createBooking(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        if (isRoomBooked(room.getId(), checkIn, checkOut)) {
            throw new IllegalStateException(
                    "Номер " + room.getNumber() + " уже забронирован на пересекающиеся даты");
        }

        Booking booking = new Booking(idGenerator.getAndIncrement(), guest, room, checkIn, checkOut);
        return bookingRepository.save(booking);
    }

    /**
     * Проверяет, пересекается ли запрашиваемый период с существующими
     * бронированиями данного номера.
     */
    public boolean isRoomBooked(int roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> existing = bookingRepository.findByRoomId(roomId);
        return existing.stream().anyMatch(b -> b.overlaps(checkIn, checkOut));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
