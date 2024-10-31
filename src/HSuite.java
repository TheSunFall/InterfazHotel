public class HSuite extends Habitacion {
    /**
     * Crear una habitación con un código y con un máximo de huéspedes y precio por noche predeterminados
     */
    public HSuite(int codigo) {
        super(codigo, 4, 250);
    }

    /**
     * Devuelve los detalles de la habitación
     */
    public String Detalles() {
        return "Suite " + super.Detalles();
    }
}
