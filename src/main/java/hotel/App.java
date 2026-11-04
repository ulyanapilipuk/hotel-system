package hotel;

import hotel.model.Guest;
import hotel.model.Room;
import hotel.repository.BookingRepository;
import hotel.repository.InMemoryBookingRepository;
import hotel.service.BookingService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) {

        Properties config = loadConfig();
        System.out.println("Отель: " + config.getProperty("hotel.name"));
        System.out.println("Заезд с " + config.getProperty("hotel.checkin.time")
                + ", выезд до " + config.getProperty("hotel.checkout.time"));
        // Dependency Injection: репозиторий создаётся здесь и передаётся в сервис.
        // BookingService ничего не знает про конкретную реализацию хранилища.
        BookingRepository repository = new InMemoryBookingRepository();
        BookingService bookingService = new BookingService(repository);

        Guest guest1 = new Guest(1, "Иван Иванов", "+79990000001");
        Guest guest2 = new Guest(2, "Петр Петров", "+79990000002");
        Room room101 = new Room(1, "101", 2, 3000.0);

        bookingService.createBooking(guest1, room101, LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 5));
        System.out.println("Бронирование гостя " + guest1.getFullName() + " создано.");

        // Индивидуальное задание: попытка забронировать тот же номер
        // на пересекающиеся даты -> должно быть отклонено
        try {
            bookingService.createBooking(guest2, room101, LocalDate.of(2026, 10, 3), LocalDate.of(2026, 10, 7));
        } catch (IllegalStateException e) {
            System.out.println("Ошибка при бронировании: " + e.getMessage());
        }

        System.out.println("\nВсе бронирования:");
        bookingService.getAllBookings().forEach(System.out::println);
    }

    private static Properties loadConfig() {
        Properties props = new Properties();
        try (InputStream in = App.class.getResourceAsStream("/application.properties")) {
            if (in == null) {
                throw new IllegalStateException("Файл application.properties не найден в classpath");
            }
            props.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать конфигурацию", e);
        }
        return props;
    }
}
