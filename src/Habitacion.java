public abstract class Habitacion {
    final private int maxHuespedes; //determinado por el tipo de habitación
    private final int codigo;
    private final Huesped[] huespedes;
    private float precioNoche; //determinado por el tipo de habitación
    private int huespedesActuales;
    private boolean disponible;

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
            return codigo + " - Ocupada";
        } else {
            return codigo + " - Disponible\nPrecio por noche: " + precioNoche + "\n";
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
        this.disponible = false;
    }

    public int getHuespedesActuales() {
        return huespedesActuales;
    }

    public float getPrecioNoche() {
        return precioNoche;
    }

    public Huesped[] getHuespedes() {
        return huespedes;
    }
}