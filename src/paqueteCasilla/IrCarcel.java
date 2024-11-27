package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import paqueteJuego.Tablero;

public class IrCarcel extends Especial{
    public IrCarcel(String nombre, String tipo, int posicion, Jugador duenho, float valor,Grupo grupo){
        super(nombre,tipo,posicion,duenho,valor,grupo);
    }

    public boolean evaluarCasillaIrCarcel(Jugador actual){
        String[] partes = this.nombre.split(" ");
        if (partes[0].equals("IrCárcel")) {
            System.out.println("A la Cárcel!!!");
            Casilla carcel = Tablero.getTodasCasillas().get(0).get(10);
            actual.setEnCarcel(true);
            actual.vecesencarcel++;
            actual.setTiradasCarcel(0);

            actual.getAvatar().getLugar().setNombreEliminarID(actual.getAvatar());
            actual.getAvatar().setLugar(carcel);
            actual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
        }
        return true;
    }
    
}
