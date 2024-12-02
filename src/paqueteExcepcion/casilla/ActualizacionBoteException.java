package paqueteExcepcion.casilla;

public class ActualizacionBoteException extends CasillaException {
    public ActualizacionBoteException() {
        super("Error al actualizar el bote de la partida."); // Mensaje predeterminado
    }

    public ActualizacionBoteException(String message) {
        super(message); // Mensaje personalizado
    }
}
