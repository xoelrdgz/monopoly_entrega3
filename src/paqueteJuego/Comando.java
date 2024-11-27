package paqueteJuego;

import java.util.ArrayList;

public interface Comando {
    void jugador();
    void descJugador(String[] partes);
    void descAvatar(String ID);
    void descCasilla(String nombre);
    void lanzarDados();
    void lanzarTrucados();
    void comprar(String nombre);
    void salirCarcel();
    void listarJugadores();
    void listarAvatares();
    ArrayList<String> listarVenta();
    ArrayList<String> listarHipoteca();
    void listarEdificios();
    void listarEdificiosGrupo(String colorGrupo);
    void mostrarEstadisticasPartida();
    void acabarTurno();
    void mostrarAyuda();
    void verTablero();
    void mover(int casillas);
    void hipotecar(String nombre);
    void deshipotecar(String nombre);
    void venderEdificios(String tipoEdificio, String nombreCasilla, int cantidad);
    void bancarrotamenu(Jugador jugador, Jugador acreedor);
    
}
