package paqueteCarta;
import paqueteCasilla.Casilla;
import paqueteExcepcion.Comando.ComandoNoReconocidoException;
import paqueteExcepcion.carta.EleccionCartaInvalidaException;
import paqueteJuego.Jugador;
import paqueteJuego.Tablero;
import paqueteConsola.*;
import java.util.ArrayList;

import static paqueteJuego.Juego.*;
import static paqueteJuego.Valor.IMPUESTO_2;

public class CartaCajaComunidad extends Carta{
    Consola consola = new ConsolaNormal();

    @Override
    public void accion(Jugador jugador, ArrayList<ArrayList<Casilla>> casillas) {
        ArrayList<String> cartasComunidad = new ArrayList<>();
        cartasComunidad.add("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.");
        cartasComunidad.add("Te investigan por fraude de identidad. Ve a la Cárcel sin pasar por la Salida ni cobrar.");
        cartasComunidad.add("Colócate en la casilla de Salida y cobra la cantidad habitual.");
        cartasComunidad.add("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.");
        cartasComunidad.add("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.");
        cartasComunidad.add("Alquilas una villa en Solar7 y pagas 200000€ a cada jugador.");


        // Selección de carta por parte del jugador
        consola.imprimir("Elige un número de carta entre 1 y " + cartasComunidad.size() + ": ");
        int eleccion = consola.leerInt();


        if (eleccion < 1 || eleccion > cartasComunidad.size()) {
            try{
                throw new EleccionCartaInvalidaException("Número de carta inválido. Debe ser entre 1 y " + cartasComunidad.size() + ".");

            } catch (EleccionCartaInvalidaException e) {
                consola.imprimir(e.getMessage());
            }
        }
        Jugador jugadorActual = jugadores.get(turno);

        // Ejecutar la acción basada en el texto de la carta
        switch (eleccion) {
            case 1:
                consola.imprimir("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.");
                jugadorActual.sumarFortuna(-500000);
                Tablero.setBote(Tablero.getBote() + 500000);
                if (jugadorActual.getFortuna() < 0) {
                    sinDinero(jugadorActual, Tablero.banca);
                }

                break;
            case 2:
                consola.imprimir("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.setEnCarcel(true);
                jugadorActual.setTiradasCarcel(0);

                Casilla carcel = tablero.encontrar_casilla("Carcel");

                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().setLugar(carcel);
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                break;
            case 3:
                consola.imprimir("Colócate en la casilla de Salida y cobra la cantidad habitual.");
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 0);
                consola.imprimir("¡Has pasado por la casilla de salida cobras 1301328.584 ");
                jugadorActual.sumarFortuna(1301328.584f);

                break;
            case 4:
                consola.imprimir("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.");
                jugadorActual.sumarFortuna(2000000);
                break;
            case 5:
                consola.imprimir("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.");
                jugadorActual.sumarFortuna(-1000000);
                Tablero.setBote(Tablero.getBote() + 1000000);
                if (jugadorActual.getFortuna() < 0) {
                    sinDinero(jugadorActual, Tablero.banca);
                }
                break;
            case 6:
                consola.imprimir("Alquilas una villa en Solar7 y pagas 200000€ a cada jugador.");

                int cantidad = 200000;
                for (int i = 0; i < jugadores.size(); i++) {  // Itera sobre todos los jugadores
                    if (i != turno) {  // Omite el jugador en turno
                        jugadores.get(i).sumarFortuna(cantidad);  // Realiza el pago
                    }
                }

                jugadorActual.sumarFortuna(-cantidad * (jugadores.size() - 1));  // Resta el total pagado

                if (jugadorActual.getFortuna() < 0) {
                    sinDinero(jugadorActual, Tablero.banca);
                }

                break;

                default:
                try{
                    throw new ComandoNoReconocidoException("No se reconoce la carta seleccionada.");

                } catch (ComandoNoReconocidoException e) {
                    consola.imprimir(e.getMessage());
                }
    }

    }
}
