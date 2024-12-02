package paqueteExcepcion.casilla;

public class CasillaNoComprable extends CasillaException {
    public CasillaNoComprable() {
        super("La casilla no se puede comprar"); // Mensaje predeterminado
    }

    public CasillaNoComprable(String message) {
        super(message); // Mensaje personalizado
    }
}
