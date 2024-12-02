package paqueteExcepcion.avatar;

import paqueteExcepcion.avatar.AvatarException;

public class MovimientoCocheException extends AvatarException {
  public MovimientoCocheException() {
    super("Error al mover el avatar tipo Coche. Movimiento no permitido."); // Mensaje predeterminado
  }

  public MovimientoCocheException(String message) {
    super(message); // Mensaje personalizado
  }
}
