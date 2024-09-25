import java.time.LocalDate;

public class Huesped {
    //atributos
    private final String nombre;
    private final String tipoDocumento;
    private final int numeroDocumento;
    private final String pais;
    private final LocalDate fechaNacimiento;
    private Reserva reserva;
    //constructores

    /**
     * Crea un huésped con datos básicos
     */
    public Huesped(String nombre, String tipoDocumento, int numeroDocumento, String pais, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.pais = pais;
        this.fechaNacimiento = fechaNacimiento;
    }

    //métodos
    /**
     * Asigna una reserva a un huésped
     * */
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    /**
     * Devuelve el nombre, tipo y número de documento del huésped
     * */
    public String DetallesMinimos() {
        return "Huésped: " + nombre + " - " + tipoDocumento + ": " + numeroDocumento + "\n";
    }

    /**
     * Devuelve los detalles completos del huésped
     * */
    public String Detalles() {
        String detalles = "Huésped: " + nombre + " - " + tipoDocumento + " " + numeroDocumento + " - " + pais + " - " + fechaNacimiento;
        if (reserva != null) {
            detalles += " - Reserva: " + reserva;
        }
        return detalles;
    }
}