package paqueteExcepcion.avatar;

public class CambioMovimientoException extends AvatarException {
    public CambioMovimientoException() {
        super("Erorr: "); // Mensaje predeterminado
    }

    public CambioMovimientoException(String message) {
        super(message); // Mensaje personalizado
    }
}
