package paqueteExcepcion.propiedad;


public class PropiedadNoPerteneceAJugadorException extends PropiedadException {
    public PropiedadNoPerteneceAJugadorException() {
        super("El jugador no es el propietario de esta propiedad."); // Mensaje predeterminado
    }

    public PropiedadNoPerteneceAJugadorException(String message) {
        super(message); // Mensaje personalizado
    }
}
