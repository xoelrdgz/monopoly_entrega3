package paqueteExcepcion.edificio;


public class RequisitosConstruccionNoCumplidosException extends EdificioException {
    public RequisitosConstruccionNoCumplidosException() {
        super("No se cumplen los requisitos necesarios para construir este edificio."); // Mensaje predeterminado
    }

    public RequisitosConstruccionNoCumplidosException(String message) {
        super(message); // Mensaje personalizado
    }
}
