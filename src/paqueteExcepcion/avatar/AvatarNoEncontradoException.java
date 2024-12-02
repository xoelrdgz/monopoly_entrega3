package paqueteExcepcion.avatar;

public class AvatarNoEncontradoException extends AvatarException {
    public AvatarNoEncontradoException() {
        super("Avatar no encontrado"); // Mensaje predeterminado
    }

    public AvatarNoEncontradoException(String message) {
        super(message); // Mensaje personalizado
    }
}
