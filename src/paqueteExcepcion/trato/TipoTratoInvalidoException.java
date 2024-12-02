package paqueteExcepcion.trato;


public class TipoTratoInvalidoException extends TratoException {
    public TipoTratoInvalidoException() {
        super("El tipo de trato especificado no es válido o no está implementado."); // Mensaje predeterminado
    }

    public TipoTratoInvalidoException(String message) {
        super(message); // Mensaje personalizado
    }
}