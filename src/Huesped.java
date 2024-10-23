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
    public void Reservar(Reserva reserva) {
        this.reserva = reserva;
    }

    /**
     * Devuelve el nombre, tipo y número de documento del huésped
     * */
    public String DetallesMinimos() {
        return "Huésped: " + nombre + " - " + tipoDocumento + ": " + numeroDocumento;
    }

    /**
     * Devuelve los detalles completos del huésped
     * */
    public String Detalles() {
        String detalles = "Huésped: " + nombre + " - " + tipoDocumento + " " + numeroDocumento + " - " + pais + " - " + fechaNacimiento;
        if (reserva != null) {
            detalles += " - Reserva: " + reserva.Detalles();
        }
        return detalles;
    }
}


public class Huesped extends Persona {
    private Reserva reserva;

    public Huesped(String nombre, String tipoDocumento, int numeroDocumento, String pais) {
        super(nombre, tipoDocumento, numeroDocumento, pais); 
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public String Detalles() {
        String detalles = super.Detalles();  
        if (reserva != null) {
            detalles += " - Reserva: " + reserva;
        }
        return detalles;
    }
}


public class Persona {
    private final String nombre;
    private final String tipoDocumento;
    private final int numeroDocumento;
    private final String pais;

    public Persona(String nombre, String tipoDocumento, int numeroDocumento, String pais) {
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getPais() {
        return pais;
    }

    public String DetallesMinimos() {
        return "Nombre: " + nombre + " - " + tipoDocumento + ": " + numeroDocumento + "\n";
    }

    public String Detalles() {
        return "Nombre: " + nombre + " - " + tipoDocumento + " " + numeroDocumento + " - " + pais;
    }
}
