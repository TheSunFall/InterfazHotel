import java.io.Serializable;

public class Hotel implements Serializable {
    //atributos
    private final int pisos;
    private final int numeros;
    private final Habitacion[][] habitaciones;
    //constructores

    /**
     * Crea el hotel con la cantidad de pisos y habitaciones por piso indicados
     */
    public Hotel(int pisos, int numeros) {
        this.pisos = pisos;
        this.numeros = numeros;
        this.habitaciones = new Habitacion[pisos][numeros];
    }

    //métodos
    public Habitacion getHabitacion(int piso, int numero) throws HabitacionNoValida {
        if (habitaciones[piso][numero] == null || piso >= pisos || numero >= numeros) {
            throw new HabitacionNoValida();
        }
        return habitaciones[piso][numero];
    }

    public int getPisos() {
        return pisos;
    }

    public int getNumeros() {
        return numeros;
    }

    /**
     * Crea una habitación en el piso y número indicados
     */
    public void CrearHabitacion(int piso, int numero, char tipo) throws Sobreescritura {
        if (habitaciones[piso][numero] != null) {
            piso = piso + 1;
            numero = numero + 1;
            throw new Sobreescritura("Habitación en el piso " + piso + " y número " + numero);
        }
        int codigo = (piso) * 100 + numero;
        switch (tipo) {
            case 's':
                habitaciones[piso-1][numero-1] = new HSuite(codigo);
                break;
            case 'e':
                habitaciones[piso-1][numero-1] = new HEjecutiva(codigo);
                break;
            case 'b':
                habitaciones[piso-1][numero-1] = new HBasica(codigo);
                break;
            default:
                throw new IllegalArgumentException("Tipo de habitación no válido");
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
                System.out.println("No hay habitaciones en el piso " + (i+1));
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

}

