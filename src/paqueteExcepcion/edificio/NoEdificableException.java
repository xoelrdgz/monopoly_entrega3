package paqueteExcepcion.edificio;


public class NoEdificableException extends EdificioException {
    public NoEdificableException() {
        super("No se puede edificar en esta casilla, no es de tipo solar" +
                ""); // Mensaje predeterminado
    }

    public NoEdificableException(String message) {
        super(message); // Mensaje personalizado
    }
}
