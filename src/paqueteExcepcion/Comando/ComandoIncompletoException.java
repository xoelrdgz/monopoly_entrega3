package paqueteExcepcion.Comando;

public class ComandoIncompletoException extends ComandoException {
    public ComandoIncompletoException() {
        super("El comando introducido no completo. Prueba introduciendo ayuda"); // Mensaje predeterminado
    }

    public ComandoIncompletoException(String message) {
        super(message); // Mensaje personalizado
    }
}
