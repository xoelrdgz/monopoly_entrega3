package paqueteJuego;

import paqueteConsola.ConsolaNormal;
import paqueteCasilla.Propiedad;
import paqueteExcepcion.Comando.ComandoInvalidoException;
import paqueteExcepcion.finanzas.FondosInsuficientesException;
import paqueteExcepcion.trato.TratoInvalidoException;

public class Tratos {

    private ConsolaNormal consola = new ConsolaNormal();

    // Atributos, regla general: 1 es lo ofrecido, 2 es lo recibido
    private Jugador jugador1; // Jugador que propone el trato
    private Jugador jugador2; // Jugador al que se le propone el trato
    Propiedad propiedad1; // Propiedad que se ofrece en el trato
    Propiedad propiedad2; // Propiedad que se puede recibir en el trato
    int cantidadDinero1; // Dinero que se ofrece en el trato
    int cantidadDinero2; // Dinero que se puede recibir en el trato
    public int Ids = 0;
    public String idTrato;
    public int tipoTrato;

    // Getters
    public Jugador getJugador1() {
        return jugador1;
    }
    public Jugador getJugador2() {
        return jugador2;
    }
    public Propiedad getPropiedad1() {
        return propiedad1;
    }
    public Propiedad getPropiedad2() {
        return propiedad2;
    }
    public int getCantidadDinero1() {
        return cantidadDinero1;
    }
    public int getCantidadDinero2() {
        return cantidadDinero2;
    }
    public int getIds() {
        return Ids;
    }
    public String getIdTrato() {
        return idTrato;
    }

    public int getTipoTrato() {
        return tipoTrato;
    }

    // Setters
    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }
    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }
    public void setPropiedad1(Propiedad propiedad1) {
        this.propiedad1 = propiedad1;
    }
    public void setPropiedad2(Propiedad propiedad2) {
        this.propiedad2 = propiedad2;
    }
    public void setCantidadDinero1(int cantidadDinero1) {
        this.cantidadDinero1 = cantidadDinero1;
    }
    public void setCantidadDinero2(int cantidadDinero2) {
        this.cantidadDinero2 = cantidadDinero2;
    }
    public void setIds(int ids) {
        Ids = ids;
    }
    public void setIdTrato(String idTrato) {
        this.idTrato = idTrato;
    }

    public void setTipoTrato(int tipoTrato) {
        this.tipoTrato = tipoTrato;
    }

    // Constructor
    public Tratos(Jugador jugador1, Jugador jugador2) {
        setJugador1(jugador1);
        setJugador2(jugador2);
        setIdTrato("trato-" + getIds());
        setIds(getIds() + 1);
        setTipoTrato(0);
    }

    // Método para proponer un trato
    public void proponerTrato(Jugador jugador1, Jugador jugador2, int cantidadDinero1, int cantidadDinero2, Propiedad propiedad1, Propiedad propiedad2) {

        // Comprobar si los jugadores son distintos
        if (jugador1 == jugador2){
             try {
                throw new TratoInvalidoException("No se puede proponer el trato: los jugadores son iguales.");

        } catch (TratoInvalidoException e) {
                 consola.imprimir(e.getMessage());
             }
        }

        if (propiedad1 != null && propiedad2 != null && cantidadDinero1 == 0 && cantidadDinero2 == 0){
            this.setTipoTrato(1);
        } else if (propiedad1 != null && propiedad2 == null && cantidadDinero1 != 0 && cantidadDinero2 == 0){
            this.setTipoTrato(2);
        } else if (propiedad1 == null && propiedad2 != null && cantidadDinero1 != 0 && cantidadDinero2 == 0){
            this.setTipoTrato(3);
        } else if (propiedad1 != null && propiedad2 != null && cantidadDinero1 == 0 && cantidadDinero2 != 0){
            this.setTipoTrato(4);
        } else if (propiedad1 != null && propiedad2 != null && cantidadDinero1 != 0 && cantidadDinero2 == 0){
            this.setTipoTrato(5);
        } else {
            try {
                throw new TratoInvalidoException("No se puede proponer el trato.");


        }catch (TratoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }



        if (getTipoTrato() == 0){
            try {
                throw new TratoInvalidoException("No se puede proponer el trato.");

        }catch (TratoInvalidoException e) {
            consola.imprimir(e.getMessage());
        }
        }

        switch (this.getTipoTrato()) {
            case 1:
                this.trato1(jugador1, jugador2, propiedad1, propiedad2);
                break;
            case 2:
                this.trato2(jugador1, jugador2, propiedad1, cantidadDinero2);
                break;
            case 3:
                this.trato3(jugador1, jugador2, cantidadDinero1, propiedad2);
                break;
            case 4:
                this.trato4(jugador1, jugador2, propiedad1, propiedad2, cantidadDinero2);
                break;
            case 5:
                this.trato5(jugador1, jugador2, propiedad1, cantidadDinero1, propiedad2);
                break;
            default:
                try {
                    throw new TratoInvalidoException("No se puede proponer el trato.");


                }catch (TratoInvalidoException e) {
                    consola.imprimir(e.getMessage());
                }
        }
    }

    // Los tratos se proponen y se añaden a la lista de tratos del jugador (en Jugador.java)

    // Trato de tipo 1, cambiar propiedad por propiedad, si hay hipoteca, se pasa la hipoteca
    public void trato1(Jugador jugador1, Jugador jugador2, Propiedad propiedad1, Propiedad propiedad2) {

        // Comprobar si los jugadores tienen las propiedades
        try {
            if (!jugador1.getPropiedades().contains(propiedad1)){
                throw new TratoInvalidoException("No se puede proponer el trato: " + propiedad1.getNombre() + " no pertenece a " + jugador1.getNombre() + ".");

            }

            if (!jugador2.getPropiedades().contains(propiedad2)){
                throw new TratoInvalidoException("No se puede proponer el trato: " + propiedad2.getNombre() + " no pertenece a " + jugador2.getNombre() + ".");

            }
        }catch (TratoInvalidoException e) {
            consola.imprimir(e.getMessage());
        }

        // Añadir trato a la lista de tratos de los jugadores
        jugador1.getListaTratos().add(this);
        jugador2.getListaTratos().add(this);

        // Marcar que jugador2 tiene un trato pendiente
        jugador2.setTratosPendientes(true);

        // Mensaje de trato propuesto
        consola.imprimir(jugador2.getNombre() + ", te doy " + propiedad1.getNombre() + " y tú me das " + propiedad2.getNombre() + ".");
    }

    // Aceptar trato de tipo 1, realiza el intercambio de propiedades (y de hipotecas si las hubiera)
    public void aceptarTrato1(Jugador jugador1, Jugador jugador2, Propiedad propiedad1, Propiedad propiedad2) {

        // Realizar el intercambio de propiedades
        jugador1.anhadirPropiedad(propiedad2);
        jugador1.eliminarPropiedad(propiedad1);
        jugador2.anhadirPropiedad(propiedad1);
        jugador2.eliminarPropiedad(propiedad2);

        // Marcar que jugador2 no tiene tratos pendientes
        if (jugador2.getListaTratos().isEmpty()) {
            jugador2.setTratosPendientes(false);
        }

        // Mensaje de trato aceptado
        consola.imprimir("Se ha aceptado el trato con " + jugador1.getNombre() + " le doy " + propiedad2.getNombre() + " y " + jugador1.getNombre() + " me da " + propiedad1.getNombre() + ".");
    
        // Borrar trato de la lista de tratos de los jugadores
        jugador1.getListaTratos().remove(this);
        jugador2.getListaTratos().remove(this);
    }

    // Trato de tipo 2, cambiar propiedad por dinero
    public void trato2(Jugador jugador1, Jugador jugador2, Propiedad propiedad1, int cantidadDinero2) {

        // Comprobar si los jugadores tienen las propiedades
        if (!jugador1.getPropiedades().contains(propiedad1)){
            try{
                throw new TratoInvalidoException("No se puede proponer el trato: " + propiedad1.getNombre() + " no pertenece a " + jugador1.getNombre() + ".");

        } catch (TratoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }

        // Añadir trato a la lista de tratos de los jugadores
        jugador1.getListaTratos().add(this);
        jugador2.getListaTratos().add(this);

        // Marcar que jugador2 tiene un trato pendiente
        jugador2.setTratosPendientes(true);

        // Mensaje de trato propuesto
        consola.imprimir(jugador2.getNombre() + ", te doy " + propiedad1.getNombre() + " y tú me das " + cantidadDinero2 + ".");
    }

    // Aceptar trato de tipo 2, realiza el intercambio de propiedad por dinero
    public void aceptarTrato2(Jugador jugador1, Jugador jugador2, Propiedad propiedad1, int cantidadDinero2) {

        // Comprobar si el jugador2 tiene el dinero suficiente
        if (cantidadDinero2 > jugador2.getFortuna()){
            try {
                throw new FondosInsuficientesException("No se puede aceptar el trato: " + jugador2.getNombre() + " no tiene suficiente dinero.");

        } catch (FondosInsuficientesException e) {
                consola.imprimir(e.getMessage());
            }
        }

        // Realizar el intercambio de propiedades
        jugador1.eliminarPropiedad(propiedad1);
        jugador2.anhadirPropiedad(propiedad1);

        // Se realiza el intercambio de dinero
        jugador1.setFortuna(jugador1.getFortuna() + cantidadDinero2);
        jugador2.setFortuna(jugador2.getFortuna() - cantidadDinero2);
        jugador2.dineroinvertido += cantidadDinero2;

        // Marcar que jugador2 no tiene tratos pendientes
        if (jugador2.getListaTratos().isEmpty()) {
            jugador2.setTratosPendientes(false);
        }

        // Mensaje de trato aceptado
        consola.imprimir("Se ha aceptado el trato con " + jugador1.getNombre() + " le doy " + cantidadDinero2 + " y " + jugador1.getNombre() + " me da " + propiedad1.getNombre() + ".");
    
        // Borrar trato de la lista de tratos de los jugadores
        jugador1.getListaTratos().remove(this);
        jugador2.getListaTratos().remove(this);
    }

    // Trato de tipo 3, cambiar dinero por propiedad
    public void trato3(Jugador jugador1, Jugador jugador2, int cantidadDinero1, Propiedad propiedad2) {

        // Comprobar si los jugadores tienen las propiedades
        if (!jugador2.getPropiedades().contains(propiedad2)){
            try{
                throw new TratoInvalidoException("No se puede proponer el trato: " + propiedad2.getNombre() + " no pertenece a " + jugador2.getNombre() + ".");

        } catch (TratoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }

        // Comprobar si jugador1 tiene el dinero suficiente
        if (cantidadDinero1 > jugador1.getFortuna()){
            try {
                throw new FondosInsuficientesException("No se puede aceptar el trato: " + jugador1.getNombre() + " no tiene suficiente dinero.");

            } catch (FondosInsuficientesException e) {
                consola.imprimir(e.getMessage());
            }
        }
        // Añadir trato a la lista de tratos de los jugadores
        jugador1.getListaTratos().add(this);
        jugador2.getListaTratos().add(this);

        // Marcar que jugador2 tiene un trato pendiente
        jugador2.setTratosPendientes(true);

        // Mensaje de trato propuesto
        consola.imprimir(jugador2.getNombre() + ", te doy " + cantidadDinero1 + " y tú me das " + propiedad2.getNombre() + ".");
    }

    // Aceptar trato de tipo 3, realiza el intercambio de dinero por propiedad
    public void aceptarTrato3(Jugador jugador1, Jugador jugador2, int cantidadDinero1, Propiedad propiedad2) {

        // Realizar el intercambio de propiedades
        jugador2.eliminarPropiedad(propiedad2);
        jugador1.anhadirPropiedad(propiedad2);

        // Se realiza el intercambio de dinero
        jugador1.setFortuna(jugador1.getFortuna() - cantidadDinero1);
        jugador1.dineroinvertido += cantidadDinero1;
        jugador2.setFortuna(jugador2.getFortuna() + cantidadDinero1);

        // Marcar que jugador2 no tiene tratos pendientes
        if (jugador2.getListaTratos().isEmpty()) {
            jugador2.setTratosPendientes(false);
        }

        // Mensaje de trato aceptado
        consola.imprimir("Se ha aceptado el trato con " + jugador1.getNombre() + " le doy " + propiedad2.getNombre() + " y " + jugador1.getNombre() + " me da " + cantidadDinero1 + ".");
    
        // Borrar trato de la lista de tratos de los jugadores
        jugador1.getListaTratos().remove(this);
        jugador2.getListaTratos().remove(this);
    }

    // Trato de tipo 4, cambiar propiedad por propiedad y dinero
    public void trato4 (Jugador jugador1, Jugador jugador2, Propiedad propiedad1, Propiedad propiedad2, int cantidadDinero2) {

        // Comprobar si los jugadores tienen las propiedades
        if (!jugador1.getPropiedades().contains(propiedad1)){
            try{
                throw new TratoInvalidoException("No se puede proponer el trato: " + propiedad1.getNombre() + " no pertenece a " + jugador1.getNombre() + ".");

            } catch (TratoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }

        if (!jugador2.getPropiedades().contains(propiedad2)){
            try{
                throw new TratoInvalidoException("No se puede proponer el trato: " + propiedad2.getNombre() + " no pertenece a " + jugador2.getNombre() + ".");

            } catch (TratoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }
        // Añadir trato a la lista de tratos de los jugadores
        jugador1.getListaTratos().add(this);
        jugador2.getListaTratos().add(this);

        // Marcar que jugador2 tiene un trato pendiente
        jugador2.setTratosPendientes(true);

        // Mensaje de trato propuesto
        consola.imprimir(jugador2.getNombre() + ", te doy " + propiedad1.getNombre() + " y " + cantidadDinero2 + " y tú me das " + propiedad2.getNombre() + ".");
    } 

    // Aceptar trato de tipo 4, realiza el intercambio de propiedad por propiedad y dinero
    public void aceptarTrato4 (Jugador jugador1, Jugador jugador2, Propiedad propiedad1, Propiedad propiedad2, int cantidadDinero2) {

        // Comprobar si el jugador2 tiene el dinero suficiente
        if (cantidadDinero2 > jugador2.getFortuna()){
            try {
                throw new FondosInsuficientesException("No se puede aceptar el trato: " + jugador2.getNombre() + " no tiene suficiente dinero.");

            } catch (FondosInsuficientesException e) {
                consola.imprimir(e.getMessage());
            }
        }

        // Realizar el intercambio de propiedades
        jugador1.anhadirPropiedad(propiedad2);
        jugador1.eliminarPropiedad(propiedad1);
        jugador2.anhadirPropiedad(propiedad1);
        jugador2.eliminarPropiedad(propiedad2);

        // Se realiza el intercambio de dinero
        jugador1.setFortuna(jugador1.getFortuna() + cantidadDinero2);
        jugador2.setFortuna(jugador2.getFortuna() - cantidadDinero2);
        jugador2.dineroinvertido += cantidadDinero2;

        // Marcar que jugador2 no tiene tratos pendientes
        if (jugador2.getListaTratos().isEmpty()) {
            jugador2.setTratosPendientes(false);
        }

        // Mensaje de trato aceptado
        consola.imprimir("Se ha aceptado el trato con " + jugador1.getNombre() + " le doy " + propiedad2.getNombre() + " y " + cantidadDinero2 + " y " + jugador1.getNombre() + " me da " + propiedad1.getNombre() + ".");
    
        // Borrar trato de la lista de tratos de los jugadores
        jugador1.getListaTratos().remove(this);
        jugador2.getListaTratos().remove(this);
    }

    // Trato de tipo 5, cambiar propiedad y dinero por propiedad
    public void trato5 (Jugador jugador1, Jugador jugador2, Propiedad propiedad1, int cantidadDinero1, Propiedad propiedad2) {

        // Comprobar si los jugadores tienen las propiedades
        if (!jugador1.getPropiedades().contains(propiedad1)){
            try{
                throw new TratoInvalidoException("No se puede proponer el trato: " + propiedad1.getNombre() + " no pertenece a " + jugador1.getNombre() + ".");

            } catch (TratoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }
        if (!jugador2.getPropiedades().contains(propiedad2)){
            try{
                throw new TratoInvalidoException("No se puede proponer el trato: " + propiedad2.getNombre() + " no pertenece a " + jugador2.getNombre() + ".");

            } catch (TratoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }
        // Comprobar si jugador1 tiene el dinero suficiente
        if (cantidadDinero1 > jugador1.getFortuna()){
            try {
                throw new FondosInsuficientesException("No se puede aceptar el trato: " + jugador1.getNombre() + " no tiene suficiente dinero.");

            } catch (FondosInsuficientesException e) {
                consola.imprimir(e.getMessage());
            }
        }

        // Añadir trato a la lista de tratos de los jugadores
        jugador1.getListaTratos().add(this);
        jugador2.getListaTratos().add(this);

        // Marcar que jugador2 tiene un trato pendiente
        jugador2.setTratosPendientes(true);

        // Mensaje de trato propuesto
        consola.imprimir(jugador2.getNombre() + ", te doy " + propiedad1.getNombre() + " y " + cantidadDinero1 + " y tú me das " + propiedad2.getNombre() + ".");
    }

    // Aceptar trato de tipo 5, realiza el intercambio de propiedad y dinero por propiedad
    public void aceptarTrato5 (Jugador jugador1, Jugador jugador2, Propiedad propiedad1, int cantidadDinero1, Propiedad propiedad2) {

        // Comprobar si el jugador2 tiene el dinero suficiente
        if (cantidadDinero1 > jugador2.getFortuna()){
            try {
                throw new FondosInsuficientesException("No se puede aceptar el trato: " + jugador2.getNombre() + " no tiene suficiente dinero.");

            } catch (FondosInsuficientesException e) {
                consola.imprimir(e.getMessage());
            }
        }
        // Realizar el intercambio de propiedades
        jugador1.anhadirPropiedad(propiedad2);
        jugador1.eliminarPropiedad(propiedad1);
        jugador2.anhadirPropiedad(propiedad1);
        jugador2.eliminarPropiedad(propiedad2);

        // Se realiza el intercambio de dinero
        jugador1.setFortuna(jugador1.getFortuna() - cantidadDinero1);
        jugador1.dineroinvertido += cantidadDinero1;
        jugador2.setFortuna(jugador2.getFortuna() + cantidadDinero1);

        // Marcar que jugador2 no tiene tratos pendientes si su lista de tratos está vacía
        if (jugador2.getListaTratos().isEmpty()) {
            jugador2.setTratosPendientes(false);
        }

        // Mensaje de trato aceptado
        consola.imprimir("Se ha aceptado el trato con " + jugador1.getNombre() + " le doy " + propiedad2.getNombre() + " y " + cantidadDinero1 + " y " + jugador1.getNombre() + " me da " + propiedad1.getNombre() + ".");

        // Borrar trato de la lista de tratos de los jugadores
        jugador1.getListaTratos().remove(this);
        jugador2.getListaTratos().remove(this);
    }

    // Método para aceptar un trato
    public void aceptar(String idTrato) {
            
            // Buscar el trato en la lista de tratos del jugador
            for (Tratos trato : jugador2.getListaTratos()) {
                if (trato.getIdTrato().equals(idTrato)) {
                    // Aceptar el trato según el tipo de trato
                    switch (trato.getTipoTrato()) {
                        case 1:
                            trato.aceptarTrato1(jugador1, jugador2, propiedad1, propiedad2);
                            break;
                        case 2:
                            trato.aceptarTrato2(jugador1, jugador2, propiedad1, cantidadDinero2);
                            break;
                        case 3:
                            trato.aceptarTrato3(jugador1, jugador2, cantidadDinero1, propiedad2);
                            break;
                        case 4:
                            trato.aceptarTrato4(jugador1, jugador2, propiedad1, propiedad2, cantidadDinero2);
                            break;
                        case 5:
                            trato.aceptarTrato5(jugador1, jugador2, propiedad1, cantidadDinero1, propiedad2);
                            break;
                        default:
                             try   {
                                throw new TratoInvalidoException("No se puede aceptar el trato.");

                    } catch (TratoInvalidoException e) {
                                 consola.imprimir(e.getMessage());
                             }
                    }
                }
            }
    }


    public void eliminar(String idTrato, Jugador jugador) {
        Tratos tratoAEliminar = null;
        for (Tratos trato : jugador.getListaTratos()) {
            if (trato.getIdTrato().equals(idTrato)) {
                tratoAEliminar = trato;
                break;
            }
        }
        if (tratoAEliminar != null) {
            jugador.getListaTratos().remove(tratoAEliminar);
            if (jugador.getListaTratos().isEmpty()) {
                jugador.setTratosPendientes(false);
            }
            if (jugador != jugador1) {
                jugador1.getListaTratos().remove(tratoAEliminar);
            } else {
                jugador2.getListaTratos().remove(tratoAEliminar);
            }
            consola.imprimir("Trato eliminado correctamente.");
        } else {
            try   {
                throw new TratoInvalidoException("No se puede eliminar el trato. Puede que ya haya sido aceptado o no exista.");

            } catch (TratoInvalidoException e) {
                consola.imprimir(e.getMessage());
            }
        }
    }

}