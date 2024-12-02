package paqueteExcepcion.edificio;


public class SolarHipotecadoException extends EdificioException {
    public SolarHipotecadoException() {
        super("No se puede construir en un solar hipotecado."); // Mensaje predeterminado
    }

    public SolarHipotecadoException(String message) {
        super(message); // Mensaje personalizado
    }
}
