import java.io.Serializable;

public abstract class Habitacion implements Serializable {
    final private int maxHuespedes; // determinado por el tipo de habitación
    private final int codigo;
    private Huesped[] huespedes;
    private final float precioNoche; // determinado por el tipo de habitación
    private int huespedesActuales;
    private boolean disponible;
    private Reserva reserva; // Campo para almacenar la reserva asociada a esta habitación
    private HotelChangeListener changeListener;


    public void setChangeListener(HotelChangeListener listener) {
        this.changeListener = listener;
    }
    /**
     * Crear una habitación con un código y con un máximo de huéspedes y precio por noche predeterminados
     */
    public Habitacion(int codigo, int maxHuespedes, float precioNoche) {
        this.maxHuespedes = maxHuespedes;
        this.codigo = codigo;
        this.precioNoche = precioNoche;
        this.huespedesActuales = 0;
        this.disponible = true;
        this.huespedes = new Huesped[maxHuespedes];
    }

    /**
     * Devuelve los detalles de la habitación
     */
    public String Detalles() {
        if (!disponible) {
            return codigo + " - No disponible";
        } else {
            return codigo + " - Disponible, Precio por noche: " + precioNoche + "\n";
        }
    }

    /**
     * Busca un huésped y devuelve su posición en la lista
     *
     * @param huesped el huésped a buscar
     */
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
     *
     * @param huesped el huésped a agregar
     */
    public void AgregarHuesped(Huesped huesped) {
        if (huespedesActuales < maxHuespedes) {
            huespedes[huespedesActuales] = huesped;
            huespedesActuales++;
        } else {
            throw new ArrayIndexOutOfBoundsException("No hay espacio para más huéspedes");
        }
    }

    /**
     * Elimina al huésped indicado, si está en la habitación
     *
     * @param huesped el huésped a eliminar
     */
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
     * Devuelve la disponibilidad de la habitación
     */
    public boolean Disponible() {
        return disponible;
    }

    public void Disponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Deshabilita la habitación y elimina a los huéspedes
     * Elimina la reserva de los huéspedes, el hotel tiene que eliminar la reserva de su lista después
     */
    public void Deshabilitar() {
        for (int i = 0; i < huespedesActuales; i++) {
            huespedes[i].Reservar(null);
            huespedes[i] = null;
        }
        huespedesActuales = 0;
        this.disponible = true;
        // Notificar el cambio
        if (changeListener != null) {
            changeListener.onHotelChanged();
        }
    }

    public int getMaxHuespedes() {
        return maxHuespedes;
    }

    /**
     * Crea una nueva reserva para esta habitación y asocia a los huéspedes con ella.
     * La habitación se marca como no disponible.
     *
     * @param huespedes los huéspedes que se asociarán a la reserva
     * @param dias la cantidad de días de la reserva
     */
    public void CrearReserva(Huesped[] huespedes, int dias)  {
        // Crear la reserva y asignarla al campo `reserva` de la habitación
        this.reserva = new Reserva(codigo, dias, huespedes); // Asignar la reserva al campo de la habitación
        this.disponible = false; // Marcar la habitación como no disponible
        this.huespedes = huespedes; // Asignar los huéspedes a la habitación
        // Notificar el cambio
        if (changeListener != null) {
            changeListener.onHotelChanged();
        }
    }

    /**
     * Obtiene la reserva asociada a esta habitación.
     *
     * @return la reserva asociada
     */
    public float getPrecio() {
        return precioNoche;
    }

    public boolean isDisponible() {
        return reserva == null ;
    }

    public int getCodigo() {
        return codigo;
    }

    public Reserva getReserva() {
        return reserva;
    }
}
