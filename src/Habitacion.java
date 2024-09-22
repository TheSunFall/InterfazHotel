public class Habitacion {
    //atributos
    final private int maxHuespedes = 4; //determinado por el tipo de habitación
    final private float precioNoche = 100; //determinado por el tipo de habitación
    private int codigo;
    private int huespedesActuales;
    private boolean ocupada = false;
    private Huesped[] huespedes = new Huesped[maxHuespedes];

    //constructores

    public Habitacion(int codigo) {
        this.codigo = codigo;
    }

    //métodos
}