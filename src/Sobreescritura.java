public class Sobreescritura extends Exception {
    public Sobreescritura(String message) {
        super("Error: " + message + " ya existe");
    }
}
