package paqueteExcepcion.avatar;

import paqueteExcepcion.finanzas.AvatarException;

public class GeneracionIdException extends AvatarException {
    public GeneracionIdException() {
        super("Error al generar el ID para el avatar."); // Mensaje predeterminado
    }

    public GeneracionIdException(String message) {
        super(message); // Mensaje personalizado
    }
}
