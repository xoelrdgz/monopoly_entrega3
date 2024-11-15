package paqueteAvatar;

import paqueteJuego.Jugador;
import paqueteJuego.Tablero;
import paqueteCasilla.Casilla;
import paqueteJuego.Juego;

import static paqueteJuego.Valor.SUMA_VUELTA;

import java.util.ArrayList;
import java.util.Scanner;

public class Avatar {

    // Atributos
    private String id; // Identificador: una letra generada aleatoriamente
    private String tipo; // Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; // Jugador al que pertenece ese avatar
    private Casilla lugar; // Los avatares se sitúan en casillas del tablero
    private ArrayList<String> idL;// Lista con ids usados (solo se usa para generar el id)

    // Constructor vacío
    public Avatar(ArrayList<Avatar> avCreados) {
    }

    // Constructor principal
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        generarId(avCreados);// Genera un ID para el avatar
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Casilla getLugar() {
        return lugar;
    }

    // Setter
    public void setLugar(Casilla lugar) {
        this.lugar = lugar;
    }

    public void setId(String id) {
        this.id = id;
    }

    // A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*
     * Método que permite mover a un avatar a una casilla concreta. Parámetros:
     * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
     * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
     * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int posicion) {
        // Obtener el jugador actual en función del turno
        // Jugador jugadorActual = jugador.getTurno();

        // Obtener el avatar del jugador
        // Avatar avatar = jugadorActual.getAvatar();

        // Eliminar el avatar de la casilla actual
        Casilla casillaActual = this.getLugar();
        casillaActual.eliminarAvatar(this);
        casillaActual.setNombreEliminarID(this);

        // Determinar la nueva casilla
        Casilla nuevaCasilla = null;
        if (posicion >= 0 && posicion <= 10) {
            nuevaCasilla = casillas.get(0).get(posicion);
        } else if (posicion >= 11 && posicion <= 19) {
            nuevaCasilla = casillas.get(1).get(posicion - 11);
        } else if (posicion >= 20 && posicion <= 30) {
            nuevaCasilla = casillas.get(2).get(posicion - 20);
        } else if (posicion >= 31 && posicion <= 39) {
            nuevaCasilla = casillas.get(3).get(posicion - 31);
        }

        // Mover el avatar a la nueva casilla (sin crear uno nuevo)
        if (nuevaCasilla != null) {
            nuevaCasilla.anhadirAvatar(this); // Añadir el avatar a la nueva casilla
            this.setLugar(nuevaCasilla); // Actualizar la referencia de la casilla del avatar
            nuevaCasilla.setNombreAnhadirID(this); // Actualizar el ID del avatar en la nueva casilla
            nuevaCasilla.vecescasilla++;
        }
    }

    // Generar un ID único para el avatar
    private void generarId(ArrayList<Avatar> avCreados) {
        // Lógica para generar ID único:
        id = "";
        idL = new ArrayList<>();
        for (int i = 0; i < avCreados.size(); i++) {
            idL.add(avCreados.get(i).getId());// Lista con los ids ya puestos
        }
        do {
            // Cojo un número al azar entre 65 y 90
            int valor = 0;
            do {
                valor = (int) ((Math.random() * 91));
            } while (valor < 65);
            // Lo pongo en ASCII
            id = "&" + (char) valor;

        } while (idL.contains(id));// Se busca un id que no haya sido incluido aún

    }

    //Método que mueve al jugador por el tablero la cantidad de casillas indicada
    public void moverJugador(Jugador jugador, int valorTirada) {
        Avatar avatar = jugador.getAvatar();
        int posicionActual = avatar.getLugar().getPosicion();// Obtener la posición actual del avatar
        int nuevaPosicion=0;


        if (jugador.getModomovimiento().equalsIgnoreCase("basico")){
            nuevaPosicion = posicionActual + valorTirada;
            // Verificar si el jugador pasó por la casilla de salida (casilla 0)
            if (nuevaPosicion > 39 && posicionActual <= 39) {
                jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
                System.out.println("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
                jugador.sumarFortuna(SUMA_VUELTA); jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f; // Añadir el valor de la vuelta a la fortuna del jugador
                jugador.vecesvueltas++;
                // Verificar si todos los jugadores han completado al menos 4 vueltas
                if (Juego.todosHanCompletadoCuatroVueltas()) {
                    Juego.getTablero().incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                    System.out.println("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
                }
            }

            // Ajustar la nueva posición en el tablero si excede el número de casillas
            nuevaPosicion = nuevaPosicion % 40;

            // Actualizar la posición del avatar
            avatar.moverAvatar(Tablero.getTodasCasillas(), nuevaPosicion);

            Casilla casillaActual = avatar.getLugar();

            // Evaluar la casilla en la que cayó
            casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);

        }
        else if (jugador.getModomovimiento().equalsIgnoreCase("avanzado")){
            if(jugador.getAvatar().getTipo().equalsIgnoreCase("pelota")){

                if (valorTirada>4){nuevaPosicion = posicionActual + valorTirada;
                    // Verificar si el jugador pasó por la casilla de salida (casilla 0)
                    if (nuevaPosicion > 39 && posicionActual <= 39) {
                        jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
                        System.out.println("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
                        jugador.sumarFortuna(SUMA_VUELTA); jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f; // Añadir el valor de la vuelta a la fortuna del jugador
                        jugador.vecesvueltas++;
                        // Verificar si todos los jugadores han completado al menos 4 vueltas
                        if (Juego.todosHanCompletadoCuatroVueltas()) {
                            Juego.getTablero().incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                            System.out.println("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
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
                else{System.out.println("Error raro en mov avanzado de pelota");}

                    // Ajustar la nueva posición en el tablero si excede el número de casillas
                    nuevaPosicion = nuevaPosicion % 40;


                for (int j:casillasintermedias) {
                    // Actualizar la posición del avatar
                    posicionActual=avatar.getLugar().getPosicion();
                    avatar.moverAvatar(Tablero.getTodasCasillas(), (posicionActual+j) % 40);

                    Casilla casillaActual = avatar.getLugar();

                    // Evaluar la casilla en la que cayó
                    casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);
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
                        System.out.println("¡Has retrocdido por la casilla de salida! Pagas " + SUMA_VUELTA + " monedas.");
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

                    else{System.out.println("Error raro en mov avanzado de pelota");}




                    for (int j:casillasintermedias) {
                        // Actualizar la posición del avatar
                        posicionActual=avatar.getLugar().getPosicion();
                        int posicionAct=posicionActual-j;
                        if(posicionAct<0){posicionAct=posicionAct+40;}
                        avatar.moverAvatar(Tablero.getTodasCasillas(), (posicionAct) % 40);

                        Casilla casillaActual = avatar.getLugar();

                        // Evaluar la casilla en la que cayó
                        casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);
                        String[] partes=casillaActual.getNombre().split(" ");
                        if(partes[0].equalsIgnoreCase("IrCárcel") || jugador.isEnCarcel()){
                            break;
                        }

                    }}


            }
            else if(jugador.getAvatar().getTipo().equalsIgnoreCase("coche")){


                if (valorTirada>4){movercocheavanzadomayor4(jugador,valorTirada, posicionActual);
                    }
                if (valorTirada<=4){movercocheavanzadomenor4(jugador,valorTirada, posicionActual);
                }
            }
            else {System.out.println("No hay movimiento avanzado permitido para este tipo de avatar aún");
                nuevaPosicion = posicionActual + valorTirada;
                // Verificar si el jugador pasó por la casilla de salida (casilla 0)
                if (nuevaPosicion > 39 && posicionActual <= 39) {
                    jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
                    System.out.println("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
                    jugador.sumarFortuna(SUMA_VUELTA); jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f; // Añadir el valor de la vuelta a la fortuna del jugador
                    jugador.vecesvueltas++;
                    // Verificar si todos los jugadores han completado al menos 4 vueltas
                    if (Juego.todosHanCompletadoCuatroVueltas()) {
                        Juego.getTablero().incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                        System.out.println("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
                    }
                }

                // Ajustar la nueva posición en el tablero si excede el número de casillas
                nuevaPosicion = nuevaPosicion % 40;

                // Actualizar la posición del avatar
                avatar.moverAvatar(Tablero.getTodasCasillas(), nuevaPosicion);

                Casilla casillaActual = avatar.getLugar();

                // Evaluar la casilla en la que cayó
                casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);}
        }
        else {System.out.println("Hay problemas detectando si el movimiento es básico o avanzado, no se ha podido mover al jugador");}

    }

    //Método para realizar el movimiento avanzado de un avatar coche en caso de que la tirada sea mayor a 4
    public void movercocheavanzadomayor4(Jugador jugador, int valorTirada, int posicionActual) {
        Avatar avatar= jugador.getAvatar();Scanner scanner=new Scanner(System.in);
        int nuevaPosicion = posicionActual + valorTirada;
        // Verificar si el jugador pasó por la casilla de salida (casilla 0)
        if (nuevaPosicion > 39 && posicionActual <= 39) {
            jugador.incrementarVueltas();  // Aumentar el contador de vueltas del jugador
            System.out.println("¡Has pasado por la casilla de salida! Recibes " + SUMA_VUELTA + " monedas.");
            jugador.sumarFortuna(SUMA_VUELTA);jugador.pasarporcasillasalida=jugador.pasarporcasillasalida+1301328.584f;  // Añadir el valor de la vuelta a la fortuna del jugador
            jugador.vecesvueltas++;
            // Verificar si todos los jugadores han completado al menos 4 vueltas
            if (Juego.todosHanCompletadoCuatroVueltas()) {
                Juego.getTablero().incrementarPrecioSolares();  // Incrementar el precio de los solares en un 5%
                System.out.println("Todos los jugadores han dado 4 vueltas. El precio de los solares ha aumentado un 5%.");
            }
        }
        // Ajustar la nueva posición en el tablero si excede el número de casillas
        if(nuevaPosicion<0){nuevaPosicion=nuevaPosicion+40;}
        nuevaPosicion = nuevaPosicion % 40;

        // Actualizar la posición del avatar
        avatar.moverAvatar(Tablero.getTodasCasillas(), nuevaPosicion);

        Casilla casillaActual = avatar.getLugar();


        // Evaluar la casilla en la que cayó
        casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);
        if (!jugador.getCocheExtra() && !jugador.casollegaralcuartoturnosextracocheavanzado){
        System.out.println("Coche modo avanzado: sacaste más de 4, así que tienes máximo 3 turnos");
        System.out.println("Atención!!!: solo se puede realizar una compra de propiedades, servicio o transporte a lo largo de estos 4 turnos, pero se puede edificar libremente");
        System.out.println("Atención!!!: si se llega al último turno extra (hay 3 como máximo), en ese turno sí cuentan los dados dobles, pero si en ese turno sale doble, si la siguiente tirada también sale doble NO se vuelve a tirar");
        jugador.setCocheExtra(true);}

    }
    //Método para realizar el movimiento avanzado de un avatar coche en caso de que la tirada sea menor a 4
    public void movercocheavanzadomenor4(Jugador jugador, int valorTirada, int posicionActual) {
        Avatar avatar= jugador.getAvatar();
        int nuevaPosicion = posicionActual - valorTirada;
        // Verificar si el jugador pasó por la casilla de salida (casilla 0)
        if (nuevaPosicion<0 && posicionActual>0) {
            jugador.disminuirVueltas();  // Aumentar el contador de vueltas del jugador
            System.out.println("¡Has retrocedido por la casilla de salida! Pagas " + SUMA_VUELTA + " monedas.");
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
        casillaActual.evaluarCasilla(jugador, Tablero.banca, valorTirada);
        if(!jugador.getCocheExtra()){
        System.out.println("Coche modo avanzado: sacaste menos de 4, así que pierdes los siguientes dos turnos");
        jugador.setCocheProhibido(true);jugador.setCocheExtra(false);jugador.turnoscocheavanzado=0;}
        else if(jugador.getCocheExtra()){
            System.out.println("Sacaste menos de 4, perdiste los turnos extra");
            jugador.setCasosacarmenosdecuatroenunturnoextracocheavanzado(true);
            jugador.setCocheExtra(false);jugador.turnoscocheavanzado=0;
        }

    }

}
