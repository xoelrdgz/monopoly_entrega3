package paqueteExcepcion.casilla;

public class CasillaNoEncontradaException extends CasillaException {
    public CasillaNoEncontradaException() {
        super("Casilla No encontrada"); // Mensaje predeterminado
    }

    public CasillaNoEncontradaException(String message) {
        super(message); // Mensaje personalizado
    }
}
