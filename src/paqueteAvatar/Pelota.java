package paqueteAvatar;

import paqueteJuego.Juego;
import paqueteJuego.Jugador;
import paqueteCasilla.Casilla;
import paqueteJuego.Tablero;

import java.util.ArrayList;

import static paqueteJuego.Valor.SUMA_VUELTA;

public class Pelota extends Avatar {

    public Pelota(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(tipo,jugador, lugar, avCreados);
        }



    @Override
    public void moverenavanzado(Jugador jugador, int valorTirada,Avatar avatar,int posicionActual,int nuevaPosicion){
        if (valorTirada>4){nuevaPosicion = posicionActual + valorTirada;
            // Verificar si el jugador pasó por la casilla de salida (casilla 0)
            if (nuevaPosicion > 39 && posicionActual <= 39) {
                jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
                consola.imprimir("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
                jugador.sumarFortuna(SUMA_VUELTA); jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f; // Añadir el valor de la vuelta a la fortuna del jugador
                jugador.vecesvueltas++;
                // Verificar si todos los jugadores han completado al menos 4 vueltas
                if (Juego.todosHanCompletadoCuatroVueltas()) {
                    Juego.getTablero().incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                    consola.imprimir("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
                }
            }

            int numcasillasintermedias=0;
            for (int i=5; i<=valorTirada; i++ ) {
                if (i%2!=0 ){
                    numcasillasintermedias++;}}
            if(valorTirada%2==0){numcasillasintermedias++;}
            ArrayList<Integer> casillasintermedias=new ArrayList<>();

            if(valorTirada==5){
                casillasintermedias.add(5);}
            else if(valorTirada==6){
                casillasintermedias.add(5);
                casillasintermedias.add(1);
            }
            else if (valorTirada==7){
                casillasintermedias.add(5);
                casillasintermedias.add(2);
            }
            else if (valorTirada>=8){
                casillasintermedias.add(5);
                for (int j=1;j<=numcasillasintermedias-2;j++){
                    casillasintermedias.add(2);
                }if (valorTirada%2==0){casillasintermedias.add(1);}
            }
            else{consola.imprimir("Error raro en mov avanzado de pelota");}

            // Ajustar la nueva posición en el tablero si excede el número de casillas
            nuevaPosicion = nuevaPosicion % 40;


            for (int j:casillasintermedias) {
                // Actualizar la posición del avatar
                posicionActual=avatar.getLugar().getPosicion();
                avatar.moverAvatar(Tablero.getTodasCasillas(), (posicionActual+j) % 40);

                Casilla casillaActual = avatar.getLugar();

                // Evaluar la casilla en la que cayó
                casillaActual.evaluarCasilla(jugador, Tablero.banca);
                String[] partes=casillaActual.getNombre().split(" ");
                if(partes[0].equalsIgnoreCase("IrCárcel") || jugador.isEnCarcel()){
                    break;
                }

            }

        }
        if (valorTirada<=4){nuevaPosicion = posicionActual - valorTirada;
            // Verificar si el jugador pasó por la casilla de salida (casilla 0)
            if (nuevaPosicion<0 && posicionActual>0) {
                jugador.disminuirVueltas();  // Aumentar el contador de vueltas del jugador
                consola.imprimir("¡Has retrocdido por la casilla de salida! Pagas " + SUMA_VUELTA + " monedas.");
                if(jugador.getFortuna()>=SUMA_VUELTA){
                    jugador.sumarFortuna(-SUMA_VUELTA); }
                else{Juego.sinDinero(jugador,Tablero.banca);}// Añadir el valor de la vuelta a la fortuna del jugador
                jugador.vecesvueltas=jugador.vecesvueltas-1;jugador.pasarporcasillasalida=jugador.pasarporcasillasalida-SUMA_VUELTA;
                // Verificar si todos los jugadores han completado al menos 4 vueltas

            }

            if(nuevaPosicion<0){nuevaPosicion=40+nuevaPosicion;}


            // Ajustar la nueva posición en el tablero si excede el número de casillas
            nuevaPosicion = nuevaPosicion % 40;

            int numcasillasintermedias=0;
            for (int i=0; i<=valorTirada; i++ ) {
                if (i%2!=0 ){
                    numcasillasintermedias++;}}
            if(valorTirada%2==0){numcasillasintermedias++;}
            ArrayList<Integer> casillasintermedias=new ArrayList<>();

            if(valorTirada==2){
                casillasintermedias.add(1);
                casillasintermedias.add(1);}
            else if(valorTirada==3){
                casillasintermedias.add(1);
                casillasintermedias.add(2);
            }
            else if (valorTirada==4){
                casillasintermedias.add(1);
                casillasintermedias.add(2);
                casillasintermedias.add(1);
            }

            else{consola.imprimir("Error raro en mov avanzado de pelota");}




            for (int j:casillasintermedias) {
                // Actualizar la posición del avatar
                posicionActual=avatar.getLugar().getPosicion();
                int posicionAct=posicionActual-j;
                if(posicionAct<0){posicionAct=posicionAct+40;}
                avatar.moverAvatar(Tablero.getTodasCasillas(), (posicionAct) % 40);

                Casilla casillaActual = avatar.getLugar();

                // Evaluar la casilla en la que cayó
                casillaActual.evaluarCasilla(jugador, Tablero.banca);
                String[] partes=casillaActual.getNombre().split(" ");
                if(partes[0].equalsIgnoreCase("IrCárcel") || jugador.isEnCarcel()){
                    break;
                }

            }}
    }
}
