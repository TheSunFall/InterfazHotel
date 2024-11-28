public class TestReserva {
    public static void main(String[] args) {
        Huesped[] huespedes = new Huesped[2];
        huespedes[0] = new Huesped("Juan","Aguilar", "DNI", "12345678", "Argentina");
        huespedes[1] = new Huesped("Ana","María", "DNI", "87654321", "Argentina");
        Reserva reserva = new Reserva(101, 3, huespedes);
        System.out.println(huespedes[0].Detalles());
        reserva.Extender(30);
        System.out.println(huespedes[0].Detalles());
    }
}