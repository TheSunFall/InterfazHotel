import java.time.LocalDate;

public class Reserva {
    //atributos
    private final int codigoHabitacion;
    private final LocalDate fechaInicio;
    private LocalDate fechaFin;
    private final Huesped[] huespedes;

    //constructores

    /**
     * Crea una reserva con la fecha de inicio de hoy y la fecha de fin en la cantidad de días indicada.
     * Asigna automáticamente la reserva a los huéspedes
     * @param codigoHabitacion el código de la habitación a reservar
     * @param dias             la cantidad de días que durará la reserva
     */
    public Reserva(int codigoHabitacion, int dias, Huesped[] huespedes) {
        this.codigoHabitacion = codigoHabitacion;
        this.huespedes = huespedes;
        this.fechaInicio = LocalDate.now();
        this.fechaFin = fechaInicio.plusDays(dias);
        for (Huesped huesped : huespedes) {
            huesped.Reservar(this);
        }
    }

    public Reserva(int codigoHabitacion, int dias, Huesped huesped) {
        this.codigoHabitacion = codigoHabitacion;
        this.huespedes = new Huesped[1];
        this.huespedes[0] = huesped;
        huesped.Reservar(this);
        this.fechaInicio = LocalDate.now();
        this.fechaFin = fechaInicio.plusDays(dias);
    }

    //métodos
    /**
     * Extiende la reserva en una cantidad de días
     * */
    public void Extender(int dias) {
        fechaFin = fechaFin.plusDays(dias);
    }

    /**
     * Devuelve los detalles de la reserva
     * */
    public String Detalles() {
        return "Reserva: " + codigoHabitacion + " - " + fechaInicio + " - " + fechaFin;
    }
}