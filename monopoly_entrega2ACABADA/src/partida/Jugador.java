package partida;

import java.util.ArrayList;
import monopoly.*;

public class Jugador {

    // Atributos:
    private String nombre; // Nombre del jugador
    private Avatar avatar; // Avatar que tiene en la partida
    private float fortuna; // Dinero que posee
    private float gastos; // Gastos realizados a lo largo del juego
    private boolean enCarcel; // True si el jugador está en la cárcel
    private int tiradasCarcel; // Intentos en la cárcel para salir
    private int vueltas; // Vueltas al tablero
    private ArrayList<Casilla> propiedades; // Propiedades que posee el jugador
    public float valorpropiedades;
    public float dineroinvertido;// Hecho
    public float pagotasasimpuestos;// Hecho
    public float pagoalquileres;// Hecho
    public float cobroalquileres;// Hecho
    public float pasarporcasillasalida;// Hecho
    public float premiosinversionesbote;// Hecho
    public int vecesencarcel;// Hecho
    public int vecesdados;
    public int vecesvueltas;
    public int turnosEnCarcel;
    public boolean modomovin;
    public int turnoscocheavanzado;
    public int turnossinjugarcocheavanzado;
    public boolean cocheprohibido;
    public boolean cocheextra;
    public boolean casosacarmenosdecuatroenunturnoextracocheavanzado;
    public boolean casollegaralcuartoturnosextracocheavanzado;
    // Constructor vacío (para la banca)
    public Jugador() {
    }

    // Constructor principal
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;
        this.fortuna = Valor.FORTUNA_INICIAL;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.turnosEnCarcel=0;
        this.propiedades = new ArrayList<>();
        this.vecesdados = 0;
        this.valorpropiedades = 0;
        this.dineroinvertido = 0;
        this.pagotasasimpuestos = 0;
        this.pagoalquileres = 0;
        this.cobroalquileres = 0;
        this.pasarporcasillasalida = 0;
        this.premiosinversionesbote = 0;
        this.vecesencarcel = 0;
        this.vecesvueltas = 0;
        this.modomovin = false;// Empieza con modo de movimiento básico
        // Dudas sobre el avatar
        this.turnoscocheavanzado = 0;
        this.turnossinjugarcocheavanzado = 0;
        this.cocheprohibido = false;
        this.cocheextra = false;
        this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
        this.casosacarmenosdecuatroenunturnoextracocheavanzado=false;
        this.casollegaralcuartoturnosextracocheavanzado=false;
    }

    // Getters y Setters para recuperar los valores de los atributos
    // Getters
    public String getNombre() {
        return nombre;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public float getFortuna() {
        return fortuna;
    }

    public float getGastos() {
        return gastos;
    }

    public boolean isEnCarcel() {
        return enCarcel;
    }

    public int getTiradasCarcel() {
        return tiradasCarcel;
    }

    public int getVueltas() {
        return vueltas;
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    public float getValorpropiedades() {
        return valorpropiedades;
    }

    public String getModomovimiento() {
        if (modomovin == true) {
            return "avanzado";
        } else {
            return "basico";
        }
    }
    public void setCasosacarmenosdecuatroenunturnoextracocheavanzado(boolean casosacarmenosdecuatroenunturnoextracocheavanzado){
        this.casosacarmenosdecuatroenunturnoextracocheavanzado=casosacarmenosdecuatroenunturnoextracocheavanzado;
    }
    public void setCasollegaralcuartoturnosextracocheavanzado(boolean casollegaralcuartoturnosextracocheavanzado){
        this.casollegaralcuartoturnosextracocheavanzado=casollegaralcuartoturnosextracocheavanzado;
    }

    public void setModomovimiento() {

        if (this.modomovin == true) {

            this.modomovin = false;
        } else {
            this.modomovin = true;
        }

    }

    // Setters
    public void setFortuna(float fortuna) {
        this.fortuna = fortuna;
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public void setTiradasCarcel(int tiradasCarcel) {
        this.tiradasCarcel = tiradasCarcel;
    }

    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }

    // Otros métodos:

    // Método para añadir una propiedad al jugador. Como parámetro, la casilla a
    // añadir.
    public void anhadirPropiedad(Casilla casilla) {
        this.propiedades.add(casilla);
    }

    // Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        this.propiedades.remove(casilla);
    }

    // Método para añadir fortuna a un jugador
    // Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se
    // pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    // Método para sumar gastos a un jugador.
    // Parámetro: valor a añadir a los gastos del jugador (será el precio de un
    // solar, impuestos pagados...).

    public void sumarGastos(float valor) {
        this.gastos += valor;
    }
    // Método para establecer al jugador en la cárcel.
    // Se requiere disponer de las casillas del tablero para ello (por eso se pasan
    // como parámetro).

    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        this.enCarcel = true;

        // Lógica adicional para mover al jugador a la cárcel
    }

    // Método para mostrar estadísticas de un jugador
    public void mostrarEstadisticasJugador() {
        System.out.println("{\n Dinero Invertido: " + this.dineroinvertido + "\n" +
                "pagoTasaseImpuestos: " + this.pagotasasimpuestos + "\n" +
                "pagodeAlquileres: " + this.pagoalquileres + "\n" +
                "cobrodeAlquileres: " + this.cobroalquileres + "\n" +
                "pasarporCasillaSalida: " + this.pasarporcasillasalida + "\n" +
                "premiosInversionesoBote: " + this.premiosinversionesbote + "\n" +
                "vecesenCarcel: " + this.vecesencarcel + "\n}");
    }

    // Método para incrementar las vueltas al pasar por la salida
    public void incrementarVueltas() {
        if (!isEnCarcel()) { // Solo incrementar si no está en la cárcel
            this.vueltas += 1;
        }
    }
    // Método para disminuir las vueltas al retroceder por la salida
    public void disminuirVueltas() {
        if (!isEnCarcel()) { // Solo incrementar si no está en la cárcel
            this.vueltas=this.vueltas- 1;
        }
    }
    // Método para resetear las vueltas si es necesario
    public void resetearVueltas() {
        this.vueltas = 0;
    }

    public void setCocheExtra(boolean cocheextra) {
        this.cocheextra = cocheextra;
    }

    public boolean getCocheExtra() {
        return cocheextra;
    }

    public void setCocheProhibido(boolean cocheprohibido) {
        this.cocheprohibido = cocheprohibido;
    }

    public boolean getCocheProhibido() {
        return cocheprohibido;
    }

}