public class HabitacionNoValida extends Exception {
    public HabitacionNoValida() {
        super("Error: la habitación no existe o está fuera de servicio");
    }
}
