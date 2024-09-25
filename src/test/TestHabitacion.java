import java.time.LocalDate;

class TestHabitacion {
    public static void main(String[] args) {
        boolean[] pruebas = new boolean[3];
        Habitacion habitacion = new Habitacion(101);
        Huesped[] huespedes = new Huesped[2];
        huespedes[0] = new Huesped("Juan", "DNI", 12345678, "Argentina", LocalDate.of(1990, 1, 1));
        huespedes[1] = new Huesped("Ana", "DNI", 87654321, "Argentina", LocalDate.of(1995, 2, 2));
        for (Huesped huesped : huespedes) {
            pruebas[0] = habitacion.AgregarHuesped(huesped);
        }
        pruebas[1] = habitacion.EliminarHuesped(huespedes[0]);
        habitacion.Deshabilitar();
        pruebas[2] = !habitacion.Disponible() && !habitacion.EliminarHuesped(huespedes[0]);
        System.out.println("Prueba 1 (añadir huésped): " + pruebas[0]);
        System.out.println("Prueba 2 (eliminar huésped): " + pruebas[1]);
        System.out.println("Prueba 3 (deshabilitar): " + pruebas[2]);
    }
}