public class HBasica extends Habitacion {
    /**
     * Crear una habitación con un código y con un máximo de huéspedes y precio por noche predeterminados
     */
    public HBasica(int codigo) {
        super(codigo, 2, 100);
    }

    /**
     * Devuelve los detalles de la habitación
     */
    public String Detalles() {
        return "Habitación " + super.Detalles();
    }
}