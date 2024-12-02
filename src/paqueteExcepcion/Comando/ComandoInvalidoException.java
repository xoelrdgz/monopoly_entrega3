package paqueteExcepcion.Comando;

public class ComandoInvalidoException extends ComandoException {
    public ComandoInvalidoException() {
        super("Comando Invalido: "); // Mensaje predeterminado
    }

    public ComandoInvalidoException(String message) {
        super(message); // Mensaje personalizado
    }
}
