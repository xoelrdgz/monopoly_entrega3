package paqueteCasilla;

import paqueteJuego.Jugador;
import paqueteAvatar.Avatar;
import paqueteEdificio.Edificio;
import paqueteJuego.Grupo;
import paqueteJuego.Juego;
import paqueteJuego.Valor;
import paqueteJuego.Tablero;
import paqueteJuego.Dado;

import java.util.ArrayList;

import static paqueteJuego.Valor.IMPUESTO_1;
import static paqueteJuego.Valor.IMPUESTO_2;

public abstract class Casilla {


    //Variables a definir
    public String nombre;
    public String tipo;
    public int posicion;
    public float valor;
    public Jugador duenho;
    public Grupo grupo;
    // private float hipoteca;
    public ArrayList<Avatar> avatares; // Avatares que están en esta casilla
    // private Tablero tablero;
    public boolean hipotecada;
    public ArrayList<String> ids;
    public int vecescasilla;
    public float casillahacostado;
    public float casillahagenerado;


    // Constructor general clase Casilla
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho, Grupo grupo,float valor) {
        String[] partesnombre = nombre.split(" ");
        if (partesnombre.length >= 2) {
            this.nombre = partesnombre[0];
        } else {
            this.nombre = nombre;
        }
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.grupo = grupo;
        this.valor = valor;
        this.avatares = new ArrayList<>();
        this.vecescasilla = 0;
        this.casillahacostado = 0;
        this.casillahagenerado = 0;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public float getCasillahacostado() {
        return casillahacostado;
    }

    public float getCasillahagenerado() {
        return casillahagenerado;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPosicion() {
        return posicion;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public float getValor(){return valor;}

    public Grupo getGrupo() {
        return grupo;
    }

    public boolean getHipotecada() {
        return hipotecada;
    }

    public ArrayList<String> getIds() {
        return ids;
    }


    //Setters

    // Setter para añadir el ID al nombre de una Casilla (y así actualizar el
    // tablero a medida que se muevan los avatares)
    public void setNombreAnhadirID(Avatar avatar) {
        this.nombre = this.nombre + " " + avatar.getId();
    }

    // Setter para eliminar el ID al nombre de una Casilla
    public void setNombreEliminarID(Avatar avatar) {
        this.nombre = this.nombre.replaceAll(avatar.getId(), "");
        // this.nombre = this.nombre.replaceAll(" ","");
    }

    // Setter para cambiar el dueño de una casilla
    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public void setCasillahacostado(float casillahacostado) {
        this.casillahacostado = casillahacostado;
    }

    public void setCasillahagenerado(float casillahagenerado) {
        this.casillahagenerado = casillahagenerado;
    }


    //Método abstracto de evaluar casilla
    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca);


    // Método para ver los jugadores en casilla
    public ArrayList<Jugador> JugadoresenCasilla() {
        ArrayList<Jugador> Jugadoresencasilla = new ArrayList<>();
        for (int i = 0; i == avatares.size() - 1; i++) {
            Jugadoresencasilla.add(avatares.get(i).getJugador());
        }
        return Jugadoresencasilla;
    }

    // Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    // Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }


    /*
     * Método para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de
     * todos los jugadores.
     * Este método toma como argumento la cantidad a añadir del valor de la casilla.
     */
    /*
     * public void sumarValor(float suma) {
     * this.valor += suma; // Suma el valor adicional a la propiedad actual
     * }
     */

    /*
     * Método para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.
     */
    public String infoCasilla() {
        String info = "Casilla: " + this.nombre + ", Tipo: " + this.tipo + ", Posición: " + this.posicion + ", Valor: "
                + this.valor;

        if (this.duenho != null) {
            info += ", Dueño: " + this.duenho.getNombre(); // Si hay dueño, muestra el nombre del dueño
        } else {
            info += ", Dueño: Ninguno"; // Si no hay dueño
        }

        return info; // Devuelve la cadena con la información

    }

    // Método para obtener los jugadores que están en la cárcel
    public ArrayList<Jugador> getJugadoresEnCarcel() {
        ArrayList<Jugador> jugadorescarcel = new ArrayList<>();
        for (int i = 0; i <= avatares.size() - 1; i++) {
            if (avatares.get(i).getJugador().isEnCarcel()) {
                jugadorescarcel.add(avatares.get(i).getJugador());
            }
        }
        return jugadorescarcel;
    }

    // Método para obtener casilla a partir de su posicion
    public static ArrayList<Casilla> casillasporposiciones() {
        ArrayList<ArrayList<Casilla>> casillas = Tablero.getTodasCasillas();
        ArrayList<Casilla> ppp = new ArrayList<>();

        for (int posicion = 0; posicion < 40; posicion++) {

            if (posicion >= 0 && posicion <= 10) {
                ppp.add(casillas.get(0).get(posicion));
            } else if (posicion >= 11 && posicion <= 19) {
                ppp.add(casillas.get(1).get(posicion - 11));
            } else if (posicion >= 20 && posicion <= 30) {
                ppp.add(casillas.get(2).get(posicion - 20));
            } else if (posicion >= 31 && posicion <= 39) {
                ppp.add(casillas.get(3).get(posicion - 31));
            }
        }
        return ppp;
    }

    public float rentabilidadcasilla() {
        int posicioncasilla = this.getPosicion();
        ArrayList<ArrayList<Casilla>> casillas = Tablero.getTodasCasillas();
        ArrayList<Casilla> ppp = new ArrayList<>();

        for (int posicion = 0; posicion < 40; posicion++) {

            if (posicion >= 0 && posicion <= 10) {
                ppp.add(casillas.get(0).get(posicion));
            } else if (posicion >= 11 && posicion <= 19) {
                ppp.add(casillas.get(1).get(posicion - 11));
            } else if (posicion >= 20 && posicion <= 30) {
                ppp.add(casillas.get(2).get(posicion - 20));
            } else if (posicion >= 31 && posicion <= 39) {
                ppp.add(casillas.get(3).get(posicion - 31));
            }
        }
        return ppp.get(posicioncasilla).casillahagenerado - ppp.get(posicioncasilla).casillahacostado;
    }
}
