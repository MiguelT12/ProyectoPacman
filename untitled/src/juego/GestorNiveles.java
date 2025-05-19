package juego;

import multimedia.*;

public class GestorNiveles {
    private Lienzo lienzo;
    private Teclado teclado;
    private int nivelActual = 1;
    private Nivel nivel;

    public GestorNiveles(Lienzo lienzo, Teclado teclado) {
        this.lienzo = lienzo;
        this.teclado = teclado;
        cargarNivel(nivelActual);
    }

    private void cargarNivel(int numero) {
        nivel = new Nivel(lienzo, teclado, numero);
    }

    public void tick() throws SalirDelJuegoException, PacmanComidoException {
        nivel.tick();

        if (nivel.mapaCompletado()){
            nivelActual++;
            cargarNivel(nivelActual);
        }
    }

    public void dibujar(){
        nivel.dibujar();
    }
}
