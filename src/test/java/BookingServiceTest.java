import hotel.model.Booking;
import hotel.model.Guest;
import hotel.model.Room;
import hotel.repository.BookingRepository;
import hotel.repository.InMemoryBookingRepository;
import hotel.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    private BookingRepository repository;
    private BookingService bookingService;
    private Guest guest1;
    private Guest guest2;
    private Room room;

    @BeforeEach
    void setUp() {
        // Тестовая реализация зависимости через тот же интерфейс —
        // слабая связанность позволяет легко подставить нужную реализацию.
        repository = new InMemoryBookingRepository();
        bookingService = new BookingService(repository);
        guest1 = new Guest(1, "Иван Иванов", "+79990000001");
        guest2 = new Guest(2, "Петр Петров", "+79990000002");
        room = new Room(1, "101", 2, 3000.0);
    }

    @Test
    void test1_successfulBooking() {
        // Успешный сценарий: свободный номер бронируется без ошибок
        Booking booking = bookingService.createBooking(
                guest1, room, LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 5));

        assertNotNull(booking);
        assertEquals(1, bookingService.getAllBookings().size());
        assertEquals(guest1, booking.getGuest());
        assertEquals(room, booking.getRoom());
    }

    @Test
    void test2_errorScenario_sameDatesTwice() {
        // Ошибочный сценарий: тот же номер на те же даты бронируется повторно
        bookingService.createBooking(guest1, room, LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 5));

        assertThrows(IllegalStateException.class, () ->
                bookingService.createBooking(guest2, room, LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 5)));

        // Второе бронирование не должно было сохраниться
        assertEquals(1, bookingService.getAllBookings().size());
    }

    @Test
    void test3_individualTask_overlappingDatesRejected() {
        // Индивидуальное задание: частичное пересечение периодов
        // тоже должно быть запрещено, а не только полное совпадение дат
        bookingService.createBooking(guest1, room, LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 10));

        // Новый период начинается внутри уже занятого диапазона
        assertThrows(IllegalStateException.class, () ->
                bookingService.createBooking(guest2, room, LocalDate.of(2026, 10, 5), LocalDate.of(2026, 10, 15)));

        // А вот бронирование, начинающееся ровно в день выезда, — разрешено
        assertDoesNotThrow(() ->
                bookingService.createBooking(guest2, room, LocalDate.of(2026, 10, 10), LocalDate.of(2026, 10, 12)));

        assertEquals(2, bookingService.getAllBookings().size());
    }

    @Test
    void successfulRoomBooking() {
        // Подготовить сервис и входные данные
        // Вызвать реальный метод бронирования из проекта
        // Проверить результат с помощью assertNotNull,
        // assertEquals, assertTrue или других подходящих assertions
    }
}
