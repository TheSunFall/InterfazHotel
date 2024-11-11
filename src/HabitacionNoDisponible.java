public class HabitacionNoDisponible extends Exception {
    public HabitacionNoDisponible() {
        super("Error: la habitación no existe o está fuera de servicio");
    }
}
