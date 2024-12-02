package paqueteCarta;

import paqueteCasilla.Casilla;
import paqueteJuego.Jugador;
import paqueteJuego.Tablero;
import paqueteConsola.*;
import java.util.ArrayList;

import static paqueteJuego.Juego.*;

public  class CartaSuerte extends Carta{

    Consola consola = new ConsolaNormal();
    @Override
    public void accion(Jugador jugador, ArrayList<ArrayList<Casilla>> casillas) {
        ArrayList<String> cartasSuerte = new ArrayList<>();
        cartasSuerte.add("Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        cartasSuerte.add("Decides hacer un viaje de placer hasta Solar15, sin pasar por la Salida ni cobrar.");
        cartasSuerte.add("Vendes tu billete de avión y cobras 500000€.");
        cartasSuerte.add("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
        cartasSuerte.add("Los acreedores te persiguen. Ve a la Cárcel sin pasar por la Salida ni cobrar.");
        cartasSuerte.add("¡Has ganado el bote de la lotería! Recibe 1000000€.");


        // Selección de carta por parte del jugador
        consola.imprimir("Elige un número de carta entre 1 y " + cartasSuerte.size() + ": ");
        int eleccion = consola.leerInt();

        if (eleccion < 1 || eleccion > cartasSuerte.size()) {
            consola.imprimir("Número de carta inválido. Debe ser entre 1 y " + cartasSuerte.size() + ".");
            return;
        }

        // Obtener la carta seleccionada
        String cartaSeleccionada = cartasSuerte.get(eleccion - 1);
        consola.imprimir("Acción: " + cartaSeleccionada);
        // Ejecutar la acción basada en el texto de la carta
        Jugador jugadorActual = jugadores.get(turno);

        switch (eleccion) {
            case 1:
                consola.imprimir("Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.");

                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 5);
                break;

            case 2:
                consola.imprimir("Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 26);

                break;

            case 3:
                consola.imprimir("Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.");
                jugadorActual.sumarFortuna(500000);
                break;

            case 4:
                consola.imprimir("Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.");
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 6);
                consola.imprimir("¡Has pasado por la casilla de salida cobras 1301328.584 ");
                jugadorActual.sumarFortuna(1301328.584f);

                break;

            case 5:
                consola.imprimir("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.setEnCarcel(true);
                jugadorActual.setTiradasCarcel(0);

                Casilla carcel = tablero.encontrar_casilla("Carcel");

                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().setLugar(carcel);
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                break;

            case 6:
                consola.imprimir("¡Has ganado el bote de la lotería! Recibe 1000000€.");
                jugadorActual.sumarFortuna(1000000);
                break;

            default:
                consola.imprimir("No se reconoce la carta seleccionada.");
                break;
        }

    }




}
