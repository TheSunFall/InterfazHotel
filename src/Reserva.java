import java.time.LocalDate;

public class Reserva {
    //atributos
    private final int codigoHabitacion;
    private final LocalDate fechaInicio;
    private LocalDate fechaFin;

    //constructores
    public Reserva(int codigoHabitacion) {
        this.codigoHabitacion = codigoHabitacion;
        this.fechaInicio = LocalDate.now();
    }

    public Reserva(int codigoHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        this.codigoHabitacion = codigoHabitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }


    //métodos


}