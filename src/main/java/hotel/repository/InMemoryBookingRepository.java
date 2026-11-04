package hotel.repository;

import hotel.model.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Простая реализация хранилища бронирований в памяти (список).
 * Легко заменяется на реализацию с БД без изменения HotelService,
 * так как сервис зависит от интерфейса BookingRepository, а не от этого класса.
 */
public class InMemoryBookingRepository implements BookingRepository {

    private final List<Booking> bookings = new ArrayList<>();

    @Override
    public Booking save(Booking booking) {
        bookings.add(booking);
        return booking;
    }

    @Override
    public List<Booking> findByRoomId(int roomId) {
        return bookings.stream()
                .filter(b -> b.getRoom().getId() == roomId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }
}
