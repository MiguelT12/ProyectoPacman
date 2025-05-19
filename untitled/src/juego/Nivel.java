package juego;

import multimedia.*;

import java.util.ArrayList;

public class Nivel implements Dibujable {
    private EstadoJuego estado;
    private Mapa mapa;
    private Pacman pacman;
    private ArrayList<Fantasma> fantasmas = new ArrayList<>();
    private Lienzo lienzo;
    private Teclado teclado;
    FabricaNiveles niveles = new FabricaNiveles();

    public Nivel(Lienzo lienzo, Teclado teclado, int numNivel) {
        this.lienzo = lienzo;
        this.teclado = teclado;

        estado = new EstadoJuego(lienzo);
        mapa = new Mapa(lienzo);

        mapa.setMapa(niveles.getMapa(numNivel));

        situarActores();
        mapa.generarPuntos();
    }

    public boolean mapaCompletado(){
        return mapa.mapaCompletado();
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    private void situarActores() {
        pacman = new Pacman(this, lienzo, teclado, mapa, estado);

        for (int i = 0; i < 3; i++) {
            fantasmas.add(new Fantasma(this, lienzo, pacman, mapa));
        }
    }

    private boolean estaLibre(Posicion posicion) {
        if (!mapa.esTransitable(posicion)) return false;

        if (posicion.equals(pacman.getPosicion())) return false;

        for (Fantasma fantasma : fantasmas) {
            if (posicion.equals(fantasma.getPosicion())) return false;
        }

        return true;
    }

    public Posicion obtenerPosicionVaciaAleatoria() {
        Posicion posicion;

        do {
            posicion = new Posicion(mapa);
        } while (!estaLibre(posicion));

        return posicion;
    }

    public void tick() throws PacmanComidoException, SalirDelJuegoException {
        pacman.tick();

        if (mapa.hayPunto(pacman.getPosicion())) {
            estado.incrementarPuntuacion();
            mapa.retirarPunto(pacman.getPosicion());
        }

        for (Fantasma fantasma : fantasmas) {
            fantasma.tick();
        }
    }

    public void dibujar() {
        lienzo.limpiar();

        mapa.dibujar();
        pacman.dibujar();

        for (Fantasma fantasma : fantasmas) {
            fantasma.dibujar();
        }

        estado.dibujar();

        lienzo.volcar();
    }
}