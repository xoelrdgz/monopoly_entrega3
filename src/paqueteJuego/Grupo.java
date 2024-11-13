package paqueteJuego;


import java.util.ArrayList;

public class Grupo {

   // Atributos
   private ArrayList<Casilla> miembros; // Casillas miembros del grupo.
   private String colorGrupo; // Color del grupo
   private int numCasillas; // Número de casillas del grupo.

   // Constructor vacío.
   public Grupo(String colorGrupo) {
      this.miembros = new ArrayList<>();
      this.colorGrupo = colorGrupo;
   }

   // Getters
   public ArrayList<Casilla> getMiembros() {
      return miembros;
   }

   public String getColorGrupo() {
      return colorGrupo;
   }

   public int getNumCasillas() {
      return numCasillas;
   }

   /*
    * Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
    */
   public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
   }

   /*
    * Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
    */
   public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
   }

   /*
    * Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
    */
   public void anhadirCasilla(Casilla miembro) {
      this.miembros.add(miembro);
   }

   /*
    * Método que comprueba si el jugador pasado tiene en su haber todas las
    * casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en
    * otro caso.
    */
   public boolean esDuenhoGrupo(Jugador jugador) {
      for (Casilla casilla : miembros) {
         if (casilla.getDuenho() != jugador) {
            return false;
         }
      }
      return true;
   }

   public float rentabilidadgrupo() {
      float rentabilidad = 0;
      for (Casilla casilla : miembros) {
         rentabilidad = rentabilidad + casilla.rentabilidadcasilla();
      }
      return rentabilidad;
   }

}
