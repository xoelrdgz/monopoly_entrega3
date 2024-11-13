package partida;

import monopoly.*;

import java.util.ArrayList;

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

}
