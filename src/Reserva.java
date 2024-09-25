import java.time.LocalDate;

public class Reserva {
    //atributos
    private final int codigoHabitacion;
    private final LocalDate fechaInicio;
    private LocalDate fechaFin;
    private final Huesped[] huespedes;

    //constructores

    /**
     * Crea una reserva con la fecha de inicio de hoy y la fecha de fin en la cantidad de días indicada
     *
     * @param codigoHabitacion el código de la habitación a reservar
     * @param dias             la cantidad de días que durará la reserva
     */
    public Reserva(int codigoHabitacion, int dias, Huesped[] huespedes) {
        this.codigoHabitacion = codigoHabitacion;
        this.huespedes = huespedes;
        this.fechaInicio = LocalDate.now();
        this.fechaFin = fechaInicio.plusDays(dias);
    }

    /**
     * Crea una reserva con las fechas de inicio y fin indicadas
     *
     * @param codigoHabitacion el código de la habitación a reservar
     * @param fechaInicio      la fecha de inicio de la reserva
     * @param fechaFin         la fecha de fin de la reserva
     */
    public Reserva(int codigoHabitacion, LocalDate fechaInicio, LocalDate fechaFin, Huesped[] huespedes) {
        this.codigoHabitacion = codigoHabitacion;
        this.huespedes = huespedes;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    //métodos
    /**
     * Extiende la reserva en una cantidad de días
     * */
    public void Extender(int dias) {
        fechaFin = fechaFin.plusDays(dias);
    }

}