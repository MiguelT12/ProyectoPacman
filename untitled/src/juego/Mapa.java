package juego;

import multimedia.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Mapa implements Dibujable {
    private static final Color COLOR_SUELO = Color.BLACK;
    private Image imagenMuro;
    private Image imagenMoneda;
    private char[][] mapa;

    private Lienzo lienzo;

    public Mapa(Lienzo lienzo) {
        setLienzo(lienzo);

        try {
            this.imagenMuro = ImageIO.read(new File("src/assets/Muro32.png"));
            this.imagenMoneda = ImageIO.read(new File("src/assets/Moneda32.png"));
        } catch (IOException e){
            throw new RuntimeException("No se puede cargar la imagen: " + e);
        }
    }

    public boolean mapaCompletado(){
        for(int i = 0; i < mapa.length; i++){
            for(int j = 0; j < mapa[i].length; j++){
                if (mapa[i][j] == '.') return false;
            }
        }
        return true;
    }

    public void setMapa(char[][] mapa) {
        this.mapa = mapa;
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public int getAncho() {
        return mapa[1].length;
    }

    public int getAlto() {
        return mapa.length;
    }

    // Estos get y set son para "traducir" el sistema de coordenadas x, y que se utiliza
    // a lo largo de todo el programa, al sistema de coordenadas de filas y columnas
    // que se utiliza en la matriz de chars.

    private char getContenidoMapa(int x, int y) {
        return mapa[y][x];
    }

    private char getContenidoMapa(Posicion posicion) {
        return mapa[posicion.getY()][posicion.getX()];
    }

    private void setContenidoMapa(int x, int y, char c) {
        mapa[y][x] = c;
    }

    private void setContenidoMapa(Posicion posicion, char c) {
        mapa[posicion.getY()][posicion.getX()] = c;
    }

    public boolean esTransitable(Posicion posicion) {
        int x = posicion.getX();
        int y = posicion.getY();

        return x >= 0 && x < mapa.length && y >= 0 && y < mapa[0].length && getContenidoMapa(x, y) != '#';
    }

    // TODO Esto del destino está hecho raro... Lo suyo sería que el actor se moviera hacia donde sea, o que, si no se puede, se lanzase una excepción y gestionarla. Pero esto de estar usando Posiciones de usar y tirar no mola.
    public Posicion calcularDestino(Posicion actual, Direccion dir) {
        Posicion nueva = actual.desplazar(dir);

        int x = nueva.getX();
        int y = nueva.getY();

        if (x < 0) x = getAncho() - 1;
        else if (x >= getAncho()) x = 0;

        if (y < 0) y = getAlto() - 1;
        else if (y >= getAlto()) y = 0;

        nueva = new Posicion(x, y);

        if (esTransitable(nueva)) return nueva;
        return actual;
    }

    public void generarPuntos() {
        for (int x = 0; x < mapa.length; x++) {
            for (int y = 0; y < mapa[0].length; y++) {
                if (getContenidoMapa(x, y) == ' ') setContenidoMapa(x, y, '·');
            }
        }
    }

    public boolean hayPunto(Posicion posicion) {
        return getContenidoMapa(posicion) == '·';
    }

    public void retirarPunto(Posicion posicion) {
        setContenidoMapa(posicion, ' ');
    }

    public void dibujar() {
        for (int x = 0; x < getAncho(); x++) {
            for (int y = 0; y < getAlto(); y++) {
                lienzo.marcarPixel(x, y, COLOR_SUELO);

                if (getContenidoMapa(x, y) == '#') lienzo.dibujarImagen(x, y, this.imagenMuro);
                else if (getContenidoMapa(x, y) == '·') lienzo.dibujarImagen(x, y, this.imagenMoneda);
            }
        }
    }
}