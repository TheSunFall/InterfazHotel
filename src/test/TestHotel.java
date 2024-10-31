import java.time.LocalDate;

public class TestHotel {
    public static void main(String[] args) {
        boolean[] pruebas = new boolean[2];
        Huesped h1 = new Huesped("Juan", "Aguilar","DNI", "12345678", "Argentina");
        Hotel hotel = new Hotel(3, 5);
        pruebas[0] = hotel.CrearHabitacion(0, 0,'e');
        pruebas[1] = hotel.CrearReserva(h1, 0, 0, 3);
        System.out.println("Prueba 1 (crear habitación): " + pruebas[0]);
        System.out.println("Prueba 2 (crear reserva): " + pruebas[1]);
    }
}