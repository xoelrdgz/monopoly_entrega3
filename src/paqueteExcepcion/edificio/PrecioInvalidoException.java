package paqueteExcepcion.edificio;


public class PrecioInvalidoException extends EdificioException {
    public PrecioInvalidoException() {
        super("El precio del edificio es inválido o no permitido."); // Mensaje predeterminado
    }

    public PrecioInvalidoException(String message) {
        super(message); // Mensaje personalizado
    }
}
