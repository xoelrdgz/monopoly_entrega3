package paqueteExcepcion.avatar;

import paqueteExcepcion.avatar.AvatarException;

public class MovimientoPelotaException extends AvatarException {
    public MovimientoPelotaException() {
        super("Error al mover el avatar tipo Pelota. Movimiento no permitido."); // Mensaje predeterminado
    }

    public MovimientoPelotaException(String message) {
        super(message); // Mensaje personalizado
    }
}
