package paqueteAvatar;

import paqueteJuego.Juego;
import paqueteJuego.Jugador;
import paqueteCasilla.Casilla;
import paqueteJuego.Tablero;

import java.util.ArrayList;
import java.util.Scanner;

import static paqueteJuego.Valor.SUMA_VUELTA;

public class Coche extends Avatar {

    public Coche(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(tipo,jugador, lugar, avCreados);


    }

    // Métodos específicos para Coche


    @Override
    public void moverenavanzado(Jugador jugador, int valorTirada,Avatar avatar,int posicionActual,int nuevaPosicion){
        if (valorTirada>4){movercocheavanzadomayor4(jugador,valorTirada, posicionActual);
        }
        if (valorTirada<=4){movercocheavanzadomenor4(jugador,valorTirada, posicionActual);
        }
    }

    //Método para realizar el movimiento avanzado de un avatar coche en caso de que la tirada sea mayor a 4
    public void movercocheavanzadomayor4(Jugador jugador, int valorTirada, int posicionActual) {
        Avatar avatar= jugador.getAvatar();
        Scanner scanner=new Scanner(System.in);
        int nuevaPosicion = posicionActual + valorTirada;
        // Verificar si el jugador pasó por la casilla de salida (casilla 0)
        if (nuevaPosicion > 39 && posicionActual <= 39) {
            jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
            consola.imprimir("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
            jugador.sumarFortuna(SUMA_VUELTA);jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f;  // Añadir el valor de la vuelta a la fortuna del jugador
            jugador.vecesvueltas++;
            // Verificar si todos los jugadores han completado al menos 4 vueltas
            if (Juego.todosHanCompletadoCuatroVueltas()) {
                Juego.getTablero().incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                consola.imprimir("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
            }
        }
        // Ajustar la nueva posición en el tablero si excede el número de casillas
        if(nuevaPosicion<0){nuevaPosicion=nuevaPosicion+40;}
        nuevaPosicion = nuevaPosicion % 40;

        // Actualizar la posición del avatar
        avatar.moverAvatar(Tablero.getTodasCasillas(), nuevaPosicion);

        Casilla casillaActual = avatar.getLugar();


        // Evaluar la casilla en la que cayó
        casillaActual.evaluarCasilla(jugador, Tablero.banca);
        if (!jugador.getCocheExtra() && !jugador.casollegaralcuartoturnosextracocheavanzado){
            consola.imprimir("Coche modo avanzado: sacaste más de 4, así que tienes máximo 3 turnos");
            consola.imprimir("Atención!!!: solo se puede realizar una compra de propiedades, servicio o transporte a lo largo de estos 4 turnos, pero se puede edificar libremente");
            consola.imprimir("Atención!!!: si se llega al último turno extra (hay 3 como máximo), en ese turno sí cuentan los dados dobles, pero si en ese turno sale doble, si la siguiente tirada también sale doble NO se vuelve a tirar");
            jugador.setCocheExtra(true);}

    }
    //Método para realizar el movimiento avanzado de un avatar coche en caso de que la tirada sea menor a 4
    public void movercocheavanzadomenor4(Jugador jugador, int valorTirada, int posicionActual) {
        Avatar avatar= jugador.getAvatar();
        int nuevaPosicion = posicionActual - valorTirada;
        // Verificar si el jugador pasó por la casilla de salida (casilla 0)
        if (nuevaPosicion<0 && posicionActual>0) {
            jugador.disminuirVueltas();  // Aumentar el contador de vueltas del jugador
            consola.imprimir("¡Has retrocedido por la casilla de salida! Pagas " + SUMA_VUELTA + " monedas.");
            if(jugador.getFortuna()>=SUMA_VUELTA){
                jugador.sumarFortuna(-SUMA_VUELTA); }
            else{Juego.sinDinero(jugador,Tablero.banca);}// Añadir el valor de la vuelta a la fortuna del jugador
            jugador.vecesvueltas=jugador.vecesvueltas-1;jugador.pasarporcasillasalida=jugador.pasarporcasillasalida-SUMA_VUELTA;
            // Verificar si todos los jugadores han completado al menos 4 vueltas

        }
        // Ajustar la nueva posición en el tablero si excede el número de casillas
        if(nuevaPosicion<0){nuevaPosicion=nuevaPosicion+40;}
        nuevaPosicion = nuevaPosicion % 40;

        // Actualizar la posición del avatar
        avatar.moverAvatar(Tablero.getTodasCasillas(), nuevaPosicion);

        Casilla casillaActual = avatar.getLugar();

        // Evaluar la casilla en la que cayó
        casillaActual.evaluarCasilla(jugador, Tablero.banca);
        if(!jugador.getCocheExtra()){
            consola.imprimir("Coche modo avanzado: sacaste menos de 4, así que pierdes los siguientes dos turnos");
            jugador.setCocheProhibido(true);jugador.setCocheExtra(false);jugador.turnoscocheavanzado=0;}
        else if(jugador.getCocheExtra()){
            consola.imprimir("Sacaste menos de 4, perdiste los turnos extra");
            jugador.setCasosacarmenosdecuatroenunturnoextracocheavanzado(true);
            jugador.setCocheExtra(false);jugador.turnoscocheavanzado=0;
        }

    }
}
