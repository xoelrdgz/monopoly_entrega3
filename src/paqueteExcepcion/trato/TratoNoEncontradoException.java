package paqueteExcepcion.trato;


public class TratoNoEncontradoException extends TratoException {
    public TratoNoEncontradoException() {
        super("No se encontró un trato con el ID especificado."); // Mensaje predeterminado
    }

    public TratoNoEncontradoException(String message) {
        super(message); // Mensaje personalizado
    }
}