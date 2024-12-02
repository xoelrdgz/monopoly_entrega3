package paqueteExcepcion.edificio;


public class LimiteEdificiosException extends EdificioException {
    public LimiteEdificiosException() {
        super("Se ha alcanzado el límite máximo de edificios permitidos en esta casilla."); // Mensaje predeterminado
    }

    public LimiteEdificiosException(String message) {
        super(message); // Mensaje personalizado
    }
}
