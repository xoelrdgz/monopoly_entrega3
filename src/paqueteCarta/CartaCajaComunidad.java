package paqueteCarta;
import paqueteCasilla.Casilla;
import paqueteJuego.Juego;
import paqueteJuego.Jugador;
import paqueteJuego.Grupo;
import paqueteJuego.Tablero;

import java.util.ArrayList;

import static paqueteJuego.Juego.*;

public class CartaCajaComunidad extends Carta{
    @Override
    public void accion() { ArrayList<String> cartasComunidad = new ArrayList<>();
        cartasComunidad.add("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.");
        cartasComunidad.add("Te investigan por fraude de identidad. Ve a la Cárcel sin pasar por la Salida ni cobrar.");
        cartasComunidad.add("Colócate en la casilla de Salida y cobra la cantidad habitual.");
        cartasComunidad.add("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.");
        cartasComunidad.add("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.");
        cartasComunidad.add("Alquilas una villa en Solar7 y pagas 200000€ a cada jugador.");

        // Barajar manualmente las cartas
        ArrayList<String> cartasBarajadas = barajarCartas(cartasComunidad);

        // Selección de carta por parte del jugador
        System.out.print("Elige un número de carta entre 1 y " + cartasComunidad.size() + ": ");
        int eleccion =


        if (eleccion < 1 || eleccion > cartasComunidad.size()) {
            System.out.println("Número de carta inválido. Debe ser entre 1 y " + cartasComunidad.size() + ".");
            return;
        }
        Jugador jugadorActual = jugadores.get(turno);

        // Ejecutar la acción basada en el texto de la carta
        switch (eleccion) {
            case 1:
                System.out.println("Paga 500000€ por un fin de semana en un balneario de 5 estrellas.");
                jugadorActual.sumarFortuna(-500000);

                if (jugadorActual.getFortuna() < 0) {
                    sinDinero(jugadorActual, Tablero.banca);
                }

                break;
            case 2:
                System.out.println("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.");
                jugadorActual.setEnCarcel(true);
                jugadorActual.setTiradasCarcel(0);

                Casilla carcel = tablero.encontrar_casilla("Carcel");

                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().setLugar(carcel);
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 10);
                System.out.println(tablero);
                break;
            case 3:
                System.out.println("Colócate en la casilla de Salida y cobra la cantidad habitual.");
                jugadorActual.getAvatar().getLugar().setNombreEliminarID(jugadorActual.getAvatar());
                jugadorActual.getAvatar().moverAvatar(Tablero.getTodasCasillas(), 0);
                System.out.println("¡Has pasado por la casilla de salida cobras 1301328.584 ");
                jugadorActual.sumarFortuna(1301328.584f);

                break;
            case 4:
                System.out.println("Tu compañía de Internet obtiene beneficios. Recibe 2000000€.");
                jugadorActual.sumarFortuna(2000000);
                break;
            case 5:
                System.out.println("Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.");
                jugadorActual.sumarFortuna(-1000000);
                if (jugadorActual.getFortuna() < 0) {
                    sinDinero(jugadorActual, Tablero.banca);
                }
                break;
            case 6:
                System.out.println("Alquilas una villa en Solar7 y pagas 200000€ a cada jugador.");

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
                System.out.println("No se reconoce la carta seleccionada.");
                break;
        }

    }
    
}
