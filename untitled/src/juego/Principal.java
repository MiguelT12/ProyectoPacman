package juego;

import multimedia.*;
import java.awt.*;

public class Principal {
    private static final int MILLIS = 150;
    private static final int LIMITE_PUNTUACION = 110;
    private static final int MAX_NIVELES = 3;
    private static int nivelActual = 1;

    public static void espera(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {}
    }

    public static void main(String[] args) {
        int anchoVentana = 15;
        int altoVentana = 15;
        int tamPixel = 30;
        Color colorFondo = Color.BLACK;

        VentanaMultimedia ventana = new VentanaMultimedia("PacMan", anchoVentana, altoVentana, tamPixel, colorFondo);
        Nivel juego = new Nivel(ventana, ventana.getTeclado(), nivelActual);

        juego.iniciarIntro();

        try {
            while (true) {
                juego.dibujar();
                juego.tick();

                if (juego.getPuntuacion() >= LIMITE_PUNTUACION) {
                    if (nivelActual < MAX_NIVELES) {
                        juego = new Nivel(ventana, ventana.getTeclado(), nivelActual++);
                    } else {
                        juego.finalJuego();
                        System.out.println("¡HAS GANADO!");
                        System.out.println("Completaste los 3 niveles");
                        break;
                    }
                }
                espera(MILLIS);
            }
        } catch (PacmanComidoException e) {
            System.out.println("¡Game Over! Te han comido.");
        } catch (SalirDelJuegoException e) {
            System.out.println("Has elegido salir del juego.");
        } finally {
            juego.dibujar();
        }
    }
}