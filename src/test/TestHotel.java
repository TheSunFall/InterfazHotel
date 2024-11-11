import java.time.LocalDate;

public class TestHotel {
    public static void main(String[] args) {
        Huesped h1 = new Huesped("Juan", "Aguilar","DNI", "12345678", "Argentina");
        Hotel hotel = new Hotel(3, 5);
        try {
            hotel.CrearHabitacion(0, 0,'e');
        } catch (Sobreescritura e) {
            System.out.println(e.getMessage());
        }
        try {
            hotel.CrearHabitacion(0, 0,'e');
        } catch (Sobreescritura e) {
            System.out.println(e.getMessage());
        }
        try {
            hotel.CrearReserva(h1, 1, 0, 3);
        } catch (HabitacionNoDisponible e) {
            System.out.println(e.getMessage());;
        }
        try {
            hotel.CrearReserva(h1, 0, 0, 3);
            System.out.println(h1.Detalles());
        } catch (HabitacionNoDisponible e) {
            System.out.println(e.getMessage());;
        }

    }
}