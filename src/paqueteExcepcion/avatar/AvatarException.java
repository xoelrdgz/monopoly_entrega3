package paqueteExcepcion.avatar;
import paqueteConsola.*;

import paqueteExcepcion.JuegoExcepcion;

public abstract class AvatarException extends JuegoExcepcion {
    public AvatarException(String message) {
        super(message);
    }
}
