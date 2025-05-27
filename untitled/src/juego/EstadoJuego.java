package juego;

import multimedia.Dibujable;
import multimedia.Lienzo;

import java.awt.*;

public class EstadoJuego implements Dibujable {
    private int puntuacion;
    private Lienzo lienzo;
    private int contadorSuperAdmin = 0;

    public EstadoJuego(Lienzo lienzo) {
        setLienzo(lienzo);
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void incrementarPuntuacion() {
        puntuacion++;
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public void setContadorSuperAdmin(int contador){
        this.contadorSuperAdmin = contador;
    }

    public void dibujar() {
        lienzo.escribirTexto(1, 0, "Puntuación: " + puntuacion, Color.RED);
        if (contadorSuperAdmin > 0) lienzo.escribirTexto(1, 14, "Tiempo: " + contadorSuperAdmin, Color.BLACK);
    }
}