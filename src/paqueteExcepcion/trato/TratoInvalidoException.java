package paqueteExcepcion.trato;


public class TratoInvalidoException extends TratoException {
    public TratoInvalidoException() {
        super("El trato propuesto no es válido."); // Mensaje predeterminado
    }

    public TratoInvalidoException(String message) {
        super(message); // Mensaje personalizado
    }
}