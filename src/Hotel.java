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
        if (habitaciones[piso-1][numero-1] != null) {
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
     * Elimina la habitación en el piso y número indicados
     */
    public void EliminarHabitacion(int i, int j) throws HabitacionNoValida {
        if (habitaciones[i][j] == null) {
            throw new HabitacionNoValida();
        }
        habitaciones[i][j] = null;
    }

    public void CambiarTipoHabitacion(int piso, int numero, char tipo) throws HabitacionNoValida {
        getHabitacion(piso, numero);

        // Cambiar el tipo de la habitación
        switch (tipo) {
            case 'b':
                habitaciones[piso][numero] = new HBasica((piso+1)*100 + (numero+1));
                break;
            case 'e':
                habitaciones[piso][numero] = new HEjecutiva((piso+1)*100 + (numero+1));
                break;
            case 's':
                habitaciones[piso][numero] = new HSuite((piso+1)*100 + (numero+1));
                break;
            default:
                throw new IllegalArgumentException("Tipo de habitación no válido");
        }
    }
}

