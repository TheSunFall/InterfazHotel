import java.time.LocalDate;

public class TestHuesped {
    public static void main(String[] args) {
        Huesped h1 = new Huesped("Juan", "Aguilar","DNI", "12345678", "Argentina");
        System.out.println(h1.DetallesMinimos());
        System.out.println(h1.Detalles());
        Reserva reserva = new Reserva(101, 3, h1);
        System.out.println(h1.Detalles());
    }
}