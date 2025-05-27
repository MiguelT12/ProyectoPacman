package temporal;

import juego.Nivel;
import juego.PacmanComidoException;
import juego.SalirDelJuegoException;
import multimedia.*;

public class GestorNiveles {
    private Lienzo lienzo;
    private Teclado teclado;
    private int nivelActual = 1;
    private Nivel nivel;
    private int puntuacionPorNivel = 110;

    public GestorNiveles(Lienzo lienzo, Teclado teclado) {
        this.lienzo = lienzo;
        this.teclado = teclado;
        cargarNivel(nivelActual);
    }

    public void cargarNivel(int numero) {
        nivel = new Nivel(lienzo, teclado, numero);
    }

    public void tick() throws SalirDelJuegoException, PacmanComidoException {
        nivel.tick();

        if (nivel.getPuntuacion() >= nivelActual * puntuacionPorNivel){
            nivelActual++;
            cargarNivel(nivelActual);
        }
    }

    public void dibujar(){
        nivel.dibujar();
    }
}
