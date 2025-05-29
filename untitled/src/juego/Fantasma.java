package juego;

import multimedia.*;

import java.util.Random;

public class Fantasma extends Actor {
    private Random random = new Random();
    private final Pacman pacman;

    private int contadorTicks = 0;
    private int velocidadNormal;
    private int velocidadModoSuper = 3;
    private int velocidadActual;

    public Fantasma(Nivel coordinador, Lienzo lienzo, Pacman pacman, Mapa mapa, int velocidad) {
        super("FantasmaNaranja32.png", coordinador, lienzo, mapa);
        this.pacman = pacman;
        this.velocidadNormal = velocidad;
        this.velocidadActual = velocidad;
        posicion = coordinador.obtenerPosicionVaciaAleatoria();
    }

    public void tick() throws PacmanComidoException {
        if (pacman.modoSuperAdmin()) velocidadActual = velocidadModoSuper;
        else velocidadActual = velocidadNormal;
        contadorTicks++;

        if (contadorTicks >= velocidadActual) {
            boolean movido = false;

            while (!movido) {
                try {
                    Direccion dir = Direccion.values()[random.nextInt(4)];
                    mover(dir);
                    movido = true;
                } catch (MovimientoInvalidoException ignored) {}
            }

            contadorTicks = 0;
        }

        if (this.posicion.equals(pacman.getPosicion())) throw new PacmanComidoException("¡Pacman ha sido comido!");
    }

    public void dibujar() {
        super.dibujar(pacman.modoSuperAdmin());
    }
}