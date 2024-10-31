public class HEjecutiva extends Habitacion {
    /**
     * Crear una habitación con un código y con un máximo de huéspedes y precio por noche predeterminados
     */
    public HEjecutiva(int codigo) {
        super(codigo, 1, 250);
    }

    /**
     * Devuelve los detalles de la habitación
     */
    public String Detalles() {
        return "Habitación ejecutiva " + super.Detalles();
    }
}