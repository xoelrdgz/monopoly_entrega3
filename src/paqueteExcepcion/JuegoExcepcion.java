package paqueteExcepcion;

public class JuegoExcepcion extends Exception {
    public JuegoExcepcion(String message) {
        super(message); // Llama al constructor de la clase Exception (clase padre)
    }
}
