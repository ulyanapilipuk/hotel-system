package hotel.repository;

import hotel.model.Booking;

import java.util.List;

/**
 * Абстракция хранилища бронирований.
 * Сервис работает только с этим интерфейсом и не знает,
 * как именно данные хранятся (в памяти, в БД и т.д.).
 */
public interface BookingRepository {

    Booking save(Booking booking);

    List<Booking> findByRoomId(int roomId);

    List<Booking> findAll();
}
