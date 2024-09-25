import java.time.LocalDate;

public class TestReserva {
    public static void main(String[] args) {
        Huesped[] huespedes = new Huesped[2];
        huespedes[0] = new Huesped("Juan", "DNI", 12345678, "Argentina", LocalDate.of(1990, 1, 1));
        huespedes[1] = new Huesped("Ana", "DNI", 87654321, "Argentina", LocalDate.of(1995, 2, 2));
        Reserva reserva = new Reserva(101, 3, huespedes);
        System.out.println(huespedes[0].Detalles());
        reserva.Extender(30);
        System.out.println(huespedes[0].Detalles());
    }
}