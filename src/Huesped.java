public class Huesped extends Persona {
    private final String tipoDocumento;
    private final String numeroDocumento;
    private final String pais;
    private Reserva reserva;
    /**
     * Crea un huésped con datos básicos
     */
    public Huesped(String nombres, String apellidos, String tipoDocumento, String numeroDocumento, String pais) {
        super(nombres, apellidos);
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.pais = pais;
    }

    //métodos
    /**
     * Asigna una reserva a un huésped
     * */
    public void Reservar(Reserva reserva) {
        this.reserva = reserva;
    }

    /**
     * Devuelve los detalles completos del huésped
     * */
    public String Detalles() {
        String detalles = super.DetallesMinimos() + " - " + tipoDocumento + " " + numeroDocumento + " - " + pais;
        if (reserva != null) {
            detalles += "\n" + reserva.Detalles();
        }
        return detalles + '\n';
    }
}



