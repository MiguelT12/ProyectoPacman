package juego;

import multimedia.*;

import java.awt.*;
import java.util.ArrayList;

public class Nivel implements Dibujable {
    private EstadoJuego estado;
    private Mapa mapa;
    private Pacman pacman;
    private ArrayList<Fantasma> fantasmas = new ArrayList<>();
    private Lienzo lienzo;
    private Teclado teclado;

    private static final Sonido SONIDO_COMER = new Sonido();
    private static final Sonido SONIDO_INTRO = new Sonido();
    private static final Sonido SONIDO_ADMIN = new Sonido();
    private static final Sonido SONIDO_FINAL = new Sonido();

    private int numeroNivel;
    FabricaNiveles niveles = new FabricaNiveles();

    public Nivel(Lienzo lienzo, Teclado teclado, int numNivel) {
        this.lienzo = lienzo;
        this.teclado = teclado;
        this.numeroNivel = numNivel;

        estado = new EstadoJuego(lienzo);
        mapa = new Mapa(lienzo);

        mapa.setMapa(niveles.getMapa(numNivel));

        situarActores();
        mapa.generarPuntos();
        SONIDO_COMER.cargarSonido("src/assets/comerMoneda.wav");
        SONIDO_INTRO.cargarSonido("src/assets/inicio.wav");
        SONIDO_ADMIN.cargarSonido("src/assets/modoSuperAdmin.wav");
        SONIDO_FINAL.cargarSonido("src/assets/final.wav");
    }

    public int getPuntuacion() {
        return estado.getPuntuacion();
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    private void situarActores() {
        pacman = new Pacman(this, lienzo, teclado, mapa, estado);

        int velocidad = (numeroNivel == 1) ? 4 : 2;

        for (int i = 0; i < 3; i++) {
            fantasmas.add(new Fantasma(this, lienzo, pacman, mapa, velocidad));
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
        estado.setContadorSuperAdmin(pacman.getContadorModoSuperAdmin());

        if (mapa.hayPunto(pacman.getPosicion())) {
            estado.incrementarPuntuacion();
            mapa.retirarPunto(pacman.getPosicion());
            SONIDO_COMER.reproducir();
        }

        if (mapa.hayFruta(pacman.getPosicion())) {
            mapa.retirarFruta(pacman.getPosicion());
            pacman.activarModoSuperAdmin();
            musicaModoAdmin();
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

    public void iniciarIntro() {
        SONIDO_INTRO.reproducir();
    }

    public void musicaModoAdmin(){
        SONIDO_ADMIN.reproducir();
    }

    public void finalJuego(){
        SONIDO_COMER.detener();
        SONIDO_ADMIN.detener();
        SONIDO_INTRO.detener();
        SONIDO_FINAL.reproducir();
    }
}