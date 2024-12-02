package paqueteExcepcion.propiedad;


public class NoTienesPropiedadesException extends PropiedadException {
    public NoTienesPropiedadesException() {
        super("No tienes propiedades: "); // Mensaje predeterminado
    }

    public NoTienesPropiedadesException(String message) {
        super(message); // Mensaje personalizado
    }
}
