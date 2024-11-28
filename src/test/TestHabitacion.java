class TestHabitacion {
    public static void main(String[] args) {
        boolean[] pruebas = new boolean[3];
        Habitacion habitacion = new HSuite(101);
        Huesped[] huespedes = new Huesped[3];
        huespedes[0] = new Huesped("Juan", "Aguilar","DNI", "12345678", "Argentina");
        huespedes[1] = new Huesped("Ana","María", "DNI", "87654321", "Argentina");
        huespedes[2] = new Huesped("Pedro","Gómez", "DNI", "87654321", "Argentina");
        for (Huesped huesped : huespedes) {
            try {
                habitacion.AgregarHuesped(huesped);
                pruebas[0] = true;
            } catch (ArrayIndexOutOfBoundsException e) {
                pruebas[0] = false;
            }
        }
        pruebas[1] = habitacion.EliminarHuesped(huespedes[0]);
        habitacion.Deshabilitar();
        pruebas[2] = !habitacion.Disponible() && !habitacion.EliminarHuesped(huespedes[0]);
        System.out.println("Prueba 1 (añadir huésped): " + pruebas[0]);
        System.out.println("Prueba 2 (eliminar huésped): " + pruebas[1]);
        System.out.println("Prueba 3 (deshabilitar): " + pruebas[2]);
    }
}