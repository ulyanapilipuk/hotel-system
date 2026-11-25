
package hotel.service;

import hotel.model.Booking;
import hotel.model.Guest;
import hotel.model.Room;
import hotel.repository.BookingRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
//import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Основная бизнес-логика отеля.
 * BookingRepository передаётся извне через конструктор.
 */
public class BookingService {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Создаёт бронирование, если номер свободен на указанные даты.
     */
    public Booking createBooking(
            Guest guest,
            Room room,
            LocalDate checkIn,
            LocalDate checkOut) {

        if (Boolean.getBoolean("test.booking.error")) {
            throw new RuntimeException("Test booking error");
        }

        logger.info(
                "Booking request received: room={}, checkIn={}, checkOut={}",
                room.getNumber(),
                checkIn,
                checkOut
        );

        if (isRoomBooked(room.getId(), checkIn, checkOut)) {
            logger.warn(
                    "Booking rejected: room {} is already booked for these dates",
                    room.getNumber()
            );

            throw new IllegalStateException(
                    "Номер " + room.getNumber()
                            + " уже забронирован на пересекающиеся даты"
            );
        }

        Booking booking = new Booking(
                idGenerator.getAndIncrement(),
                guest,
                room,
                checkIn,
                checkOut
        );

        try {
            Booking savedBooking = bookingRepository.save(booking);

            logger.info(
                    "Booking created successfully for room {}",
                    room.getNumber()
            );
//            logger.error("TEST ERROR: rejected operation");

            return savedBooking;
        } catch (RuntimeException exception) {
            logger.error(
                    "Failed to save booking for room {}",
                    room.getNumber(),
                    exception
            );

            throw exception;
        }
    }

    /**
     * Проверяет пересечение дат с существующими бронированиями номера.
     */
    public boolean isRoomBooked(
            int roomId,
            LocalDate checkIn,
            LocalDate checkOut) {

        List<Booking> existing =
                bookingRepository.findByRoomId(roomId);

        return existing.stream()
                .anyMatch(booking ->
                        booking.overlaps(checkIn, checkOut));
    }

    /**
     * Возвращает список всех бронирований.
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
