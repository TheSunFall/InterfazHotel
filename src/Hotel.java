public class Hotel {
    //atributos
    private final int pisos;
    private final int numeros;
    private final Habitacion[][] habitaciones;
    private Reserva[] reservas;
    //constructores

    /**
     * Crea el hotel con la cantidad de pisos y habitaciones por piso indicados
     */
    public Hotel(int pisos, int numeros) {
        this.pisos = pisos;
        this.numeros = numeros;
        habitaciones = new Habitacion[pisos][numeros];
    }

    //métodos

    /**
     * Crea una habitación en el piso y número indicados
     */
    public boolean CrearHabitacion(int piso, int numero) {
        if (habitaciones[piso][numero] == null) {
            int codigo = (piso + 1) * 100 + numero + 1;
            habitaciones[piso][numero] = new Habitacion(codigo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Muestra las habitaciones existentes
     * indicando si están ocupadas o disponibles
     */
    public void VerHabitacionesDisponibles() {
        System.out.print("-------------------------------\n");
        System.out.println("Habitaciones Disponibles:");
        System.out.print("-------------------------------\n");
        for (int i = 0; i < pisos; i++) {
            int c = 0;
            for (int j = 0; j < numeros; j++) {
                if (habitaciones[i][j] != null) {
                    c++;
                    System.out.println(habitaciones[i][j].Detalles());
                }
            }
            if (c == 0) {
                System.out.println("No hay habitaciones en el piso " + i);
            }
        }
    }

    /**
     * Crea una reserva, la asigna a los huéspedes y asigna los huéspedes a las habitaciones
     *
     * @param huespedes arreglo de los huéspedes que realizarán la reserva
     * @param piso      el piso de la habitación a reservar
     * @param numero    el número de la habitación a reservar
     * @param dias      la cantidad de días que durará la reserva
     */
    public boolean CrearReserva(Huesped[] huespedes, int piso, int numero, int dias) {
        if (habitaciones[piso][numero] != null && habitaciones[piso][numero].Disponible()) {
            int codigo = piso * 100 + numero;
            Reserva reserva = new Reserva(codigo, dias, huespedes);
            for (Huesped huesped : huespedes) {
                habitaciones[piso][numero].AgregarHuesped(huesped);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean CrearReserva(Huesped huesped, int piso, int numero, int dias) {
        if (habitaciones[piso][numero] != null && habitaciones[piso][numero].Disponible()) {
            int codigo = (piso + 1) * 100 + numero + 1;
            Reserva reserva = new Reserva(codigo, dias, huesped);
            habitaciones[piso][numero].AgregarHuesped(huesped);
            return true;
        } else {
            return false;
        }
    }
}