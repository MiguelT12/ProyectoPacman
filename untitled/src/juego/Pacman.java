package juego;

import multimedia.*;

import java.awt.event.KeyEvent;

public class Pacman extends Actor {
    private EstadoJuego estado;
    private Teclado teclado;
    private Mapa mapa;

    private static final int DURACION_MODO_SUPER = 35;
    private boolean modoSuperAdmin;
    private int contadorModoSuperAdmin = 0;

    public Pacman(Nivel coordinador, Lienzo lienzo, Teclado teclado, Mapa mapa, EstadoJuego estado) {
        super("Pacman32.png", coordinador, lienzo, mapa);
        this.estado = estado;
        this.teclado = teclado;
        this.posicion = new Posicion(7, 8);
        this.mapa = mapa;
    }

    public void tick() throws SalirDelJuegoException {
        if (modoSuperAdmin) {
            contadorModoSuperAdmin--;
            if (contadorModoSuperAdmin <= 0) {
                modoSuperAdmin = false;
                mapa.setModoSuperAdmin(false);
            }
        }

        try {
            if (teclado.pulsada(KeyEvent.VK_UP) || teclado.pulsada(KeyEvent.VK_W)) mover(Direccion.ARR);
            if (teclado.pulsada(KeyEvent.VK_LEFT) || teclado.pulsada(KeyEvent.VK_A)) mover(Direccion.IZD);
            if (teclado.pulsada(KeyEvent.VK_DOWN) || teclado.pulsada(KeyEvent.VK_S)) mover(Direccion.ABA);
            if (teclado.pulsada(KeyEvent.VK_RIGHT) || teclado.pulsada(KeyEvent.VK_D)) mover(Direccion.DCH);
            if (teclado.pulsada(KeyEvent.VK_Q)) throw new SalirDelJuegoException("Saliendo del juego...");
        } catch (MovimientoInvalidoException e) {
            // No hacemos nada. Pierde el turno.
        }
    }

    public boolean modoSuperAdmin() {
        return modoSuperAdmin;
    }

    public void activarModoSuperAdmin() {
        modoSuperAdmin = true;
        contadorModoSuperAdmin = DURACION_MODO_SUPER;
        mapa.setModoSuperAdmin(true);
    }

    public int getContadorModoSuperAdmin(){
        return contadorModoSuperAdmin;
    }
}