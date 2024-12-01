package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import paqueteJuego.Tablero;
import paqueteConsola.*;

public class IrCarcel extends Especial{

    Consola consola = new ConsolaNormal();

    public IrCarcel(String nombre, String tipo, int posicion, Jugador duenho, Grupo grupo){
        super(nombre,tipo,posicion,duenho,grupo);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca){
        String[] partes = this.nombre.split(" ");
        if (partes[0].equals("IrCárcel")) {
            consola.imprimir("A la Cárcel!!!");
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
     public float getAlquiler(){return 0;}
}
