package paqueteCasilla;

import paqueteJuego.Grupo;
import paqueteJuego.Jugador;

public class Propiedad extends Casilla {

    public Propiedad(String nombre, String tipo, int posicion, float valor, Jugador duenho, Grupo grupo) {
        super(nombre, tipo, posicion, duenho,grupo,valor);

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
            System.out.println(comprador.getNombre() + " ha comprado la casilla " + partes[0]);
        } else {
            String[] partes = this.nombre.split(" ");
            System.out.println("No se puede comprar la casilla " + partes[0]);
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
                String[] partes = this.nombre.split(" ");
                System.out.println("La casilla " + partes[0] + " ya está hipotecada.");
            } else {
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
                System.out.println(hipotecador.getNombre() + " ha hipotecado la casilla " + partes[0]
                        + " y ha recibido " + cantidadHipoteca + " monedas.");
            }
        } else {
            // Mensaje de error si no puede hipotecar
            String[] partes = this.nombre.split(" ");
            System.out.println("No se puede hipotecar la casilla " + partes[0]);
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
