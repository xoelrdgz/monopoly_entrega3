package paqueteExcepcion.edificio;


public class ValorSolarInvalidoException extends EdificioException {
    public ValorSolarInvalidoException() {
        super("El valor del solar no es válido para realizar esta acción."); // Mensaje predeterminado
    }

    public ValorSolarInvalidoException(String message) {
        super(message); // Mensaje personalizado
    }
}
