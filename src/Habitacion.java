import java.time.LocalDate;

public class Habitacion {
    //atributos
    final private int maxHuespedes = 4; //determinado por el tipo de habitación
    final private float precioNoche = 100; //determinado por el tipo de habitación
    private final int codigo;
    private int huespedesActuales;
    private boolean disponible = true;
    private final Huesped[] huespedes = new Huesped[maxHuespedes];

    //constructores
    /**
     * Crear una habitación con un código y con un máximo de huéspedes y precio por noche predeterminados
     * */
    public Habitacion(int codigo) {
        this.codigo = codigo;
    }

    //métodos
    /**
     * Devuelve los detalles de la habitación
     * */
    public String Detalles() {
        String detalles = "Habitación " + codigo + " - ";
        if (!disponible) {
            detalles += "Ocupada";
        } else {
            detalles += "Disponible\nPrecio por noche: " + precioNoche + "\n";
        }
        return detalles;
    }

    /**
     * Busca un huésped y devuelve su posición en la lista
     * @param huesped el huésped a buscar
     * */
    private int BuscarHuesped(Huesped huesped) {
        for (int i = 0; i < huespedesActuales; i++) {
            if (huespedes[i] == huesped) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Agrega a un huésped, siempre que haya espacio disponible
     * @param huesped el huésped a agregar
     * */
    public boolean AgregarHuesped(Huesped huesped) {
        if (huespedesActuales < maxHuespedes) {
            huespedes[huespedesActuales] = huesped;
            huespedesActuales++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Elimina al huésped indicado, si está en la habitación
     * @param huesped el huésped a eliminar
     * */
    public boolean EliminarHuesped(Huesped huesped) {
        if (BuscarHuesped(huesped) != -1) {
            for (int i = BuscarHuesped(huesped); i < huespedesActuales - 1; i++) {
                huespedes[i] = huespedes[i + 1];
            }
            huespedesActuales--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Elimina al huésped en la posición de la lista indicada
     * @param pos la posición del huésped a eliminar
     * */
    private void EliminarHuesped(int pos) {
        for (int i = pos; i < huespedesActuales - 1; i++) {
            huespedes[i] = huespedes[i + 1];
        }
        huespedesActuales--;
    }

    /**
     * Devuelve la disponibilidad de la habitación
     * */
    public boolean Disponible() {
        return disponible;
    }

    /**
     * Deshabilita la habitación y elimina a los huéspedes
     * Elimina la reserva de los huéspedes, el hotel tiene que eliminar la reserva de su lista después
     * */
    public void Deshabilitar() {
        for (int i = 0; i < huespedesActuales; i++) {
            huespedes[i].setReserva(null);
            huespedes[i] = null;
        }
        huespedesActuales = 0;
        this.disponible = false;
    }
}

class TestHabitacion {
    public static void main(String[] args) {
        Habitacion habitacion = new Habitacion(101);
        Huesped huesped1 = new Huesped("Juan", "DNI", 12345678, "Argentina", LocalDate.of(1990, 1, 1));
        habitacion.AgregarHuesped(huesped1);
        System.out.println(habitacion.Detalles());
    }
}