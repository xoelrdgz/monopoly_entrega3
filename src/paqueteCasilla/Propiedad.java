package paqueteCasilla;

import paqueteExcepcion.Comando.ComandoIncompletoException;
import paqueteExcepcion.edificio.SolarHipotecadoException;
import paqueteExcepcion.finanzas.FondosInsuficientesException;
import paqueteJuego.Grupo;
import paqueteJuego.Jugador;
import paqueteConsola.*;

public abstract class Propiedad extends Casilla {

    Consola consola = new ConsolaNormal();

    float valor;
    public Propiedad(String nombre, String tipo, int posicion, float valor, Jugador duenho, Grupo grupo) {
        super(nombre, tipo, posicion, duenho,grupo);
        this.valor = valor;

    }


    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca);
    public abstract float calcularAlquiler();
    public abstract float getAlquiler();

    public float getValor() {return valor;}

    //Método para ver si la casilla pertenece a un jugador o no
    public boolean perteneceAJugador(Jugador jugador) {
        if(this.duenho.equals(jugador)) {return true;}
        else{return false;}
    }

    /*
     * Método usado para comprar una casilla determinada. Parámetros:
     * Jugador que solicita la compra de la casilla.
     * Banca del monopoly (es el dueño de las casillas no compradas aún).
     */
    public void comprarCasilla(Jugador comprador, Jugador banca) {
        if (this.duenho == banca && comprador.getFortuna() >= this.valor) {
            comprador.sumarFortuna(-this.valor);
            comprador.sumarGastos(this.valor);
            comprador.dineroinvertido = comprador.dineroinvertido + this.valor;
            this.casillahacostado = this.casillahacostado + this.valor;
            comprador.valorpropiedades = comprador.valorpropiedades + this.valor;
            comprador.anhadirPropiedad(this);
            this.setDuenho(comprador);
            String[] partes = this.nombre.split(" ");
            consola.imprimir(comprador.getNombre() + " ha comprado la casilla " + partes[0]);
        } else {
            try{
                String[] partes = this.nombre.split(" ");
                throw new FondosInsuficientesException("No se puede comprar la casilla " + partes[0]);
        } catch (Exception e) {
                consola.imprimir(e.getMessage());
            }
        }
    }

    /*
     * Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        if (this.duenho == null) { // Si no hay dueño, la casilla está en venta
            return "La casilla " + this.nombre + " está en venta por " + this.valor;
        } else {

            return "La casilla " + this.nombre + " ya tiene dueño.";
        }
    }

    // Metodo usado para hipotecar una casilla
    // Método usado para hipotecar una casilla determinada. Parámetros:
    // Jugador que solicita la hipoteca de la casilla.
    public void hipotecarCasilla(Jugador hipotecador, Jugador banca) {
        // Verifica que el jugador sea el dueño de la casilla
        if (this.duenho == hipotecador) {
            // Verifica si la casilla ya está hipotecada
            if (this.estaHipotecada()) {
                try{
                    String[] partes = this.nombre.split(" ");
                    throw new SolarHipotecadoException("La casilla " + partes[0] + " ya está hipotecada.");
            } catch (SolarHipotecadoException e) {
                    consola.imprimir(e.getMessage());
                }
            }else {
                // Calcular la cantidad a obtener por la hipoteca
                float cantidadHipoteca = calcularValorHipoteca();

                // Actualizar la fortuna del jugador
                hipotecador.sumarFortuna(cantidadHipoteca);
                hipotecador.premiosinversionesbote = hipotecador.premiosinversionesbote + cantidadHipoteca;
                hipotecador.sumarGastos(-cantidadHipoteca); // Se suma a la fortuna y se restan gastos
                this.casillahagenerado = this.casillahagenerado + cantidadHipoteca;
                // Marcar la casilla como hipotecada
                this.setHipotecada(true);

                // Informar al jugador sobre la hipoteca
                String[] partes = this.nombre.split(" ");
                consola.imprimir(hipotecador.getNombre() + " ha hipotecado la casilla " + partes[0]
                        + " y ha recibido " + cantidadHipoteca + " monedas.");
            }
        } else {
                try{
            // Mensaje de error si no puede hipotecar
                String[] partes = this.nombre.split(" ");
                throw new ComandoIncompletoException("No se puede hipotecar la casilla " + partes[0]);
        } catch (ComandoIncompletoException e) {
                    consola.imprimir(e.getMessage());

                }
        }
    }

    // Método que calcula el valor de la hipoteca de la casilla
    public float calcularValorHipoteca() {
        // Suponiendo que el valor de la hipoteca es la mitad del valor original
        return this.valor / 2;
    }

    // Método que verifica si la casilla está hipotecada
    public boolean estaHipotecada() {
        return this.hipotecada; // Suponiendo que 'hipotecada' es una variable booleana en la clase
    }

    // Método para establecer el estado de hipoteca de la casilla
    public void setHipotecada(boolean estado) {
        this.hipotecada = estado; // Cambia el estado de la variable booleana
    }

}
