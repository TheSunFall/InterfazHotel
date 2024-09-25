import java.time.LocalDate;

public class TestHuesped {
    public static void main(String[] args) {
        Huesped h1 = new Huesped("Juan", "DNI", 12345678, "Argentina", LocalDate.of(1990, 1, 1));
        System.out.println(h1.DetallesMinimos());
        System.out.println(h1.Detalles());
        Reserva reserva = new Reserva(101, 3, h1);
        System.out.println(h1.Detalles());
    }
}