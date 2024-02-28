package BuscaMinas;

import java.util.Random;

public class BuscaMinas {
    private int[][] tablero;
    private boolean[][] casillasDescubiertas;
    private int minas;
    boolean[][] banderas;

    /**
     * Constructor de la clase BuscaMinas
     * @param filas
     * @param columnas
     * @param minas
     */
    public BuscaMinas(int filas, int columnas, int minas) {
        this.tablero = new int[filas][columnas];
        this.casillasDescubiertas = new boolean[filas][columnas];
        this.minas = minas;
        banderas = new boolean[tablero.length][tablero[0].length];
        colocarMinasAleatorias();
    }

    // Método que coloca las minas aleatoriamente en el tablero
    private void colocarMinasAleatorias() {
        Random random = new Random();
        int minasColocadas = 0;

        // Mientras no se hayan colocado todas las minas
        while (minasColocadas < minas) {
            // Elegir una casilla aleatoria
            int fila = random.nextInt(tablero.length);
            int columna = random.nextInt(tablero[0].length);

            // Si la casilla no tiene una mina, colocar una mina
            if (tablero[fila][columna] != -1) {
                tablero[fila][columna] = -1;
                minasColocadas++;
            }
        }
    }
    
    /**
     * Método que marca o desmarca una bandera en una casilla
     * @param i
     * @param j
     */
    public void marcarBanderas(int i, int j) {
        banderas[i][j] = !banderas[i][j];
    }

    /**
     * Método que descubre una casilla
     * @param fila
     * @param columna
     * @return
     */
    public boolean descubrirCasilla(int fila, int columna) {

        // Si la casilla está marcada con una bandera o ya ha sido descubierta, return false
        if (banderas[fila][columna]) {
            return false;
        }
        if (casillasDescubiertas[fila][columna]) {
            return false;
        }
    
        casillasDescubiertas[fila][columna] = true;
    
        // Si la casilla tiene una mina, devolver true
        if (tablero[fila][columna] == -1) {
            return true;
        }
    
        // Calcular el número de minas en las casillas vecinas
        int numMines = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (fila + i >= 0 && fila + i < tablero.length && columna + j >= 0 && columna + j < tablero[0].length) {
                    if (tablero[fila + i][columna + j] == -1) {
                        numMines++;
                    }
                }
            }
        }
    
        // Actualizar la casilla con el número de minas vecinas
        tablero[fila][columna] = numMines;
    
        // Si no hay minas vecinas, descubrir las casillas 
        if (numMines == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (fila + i >= 0 && fila + i < tablero.length && columna + j >= 0 && columna + j < tablero[0].length) {
                        descubrirCasilla(fila + i, columna + j);
                    }
                }
            }
        }
    
        return false;
    }

    /**
     * Método que comprueba si el jugador ha ganado
     * @return
     */
    public boolean hasGanado() {

        // Si todas las casillas que no tienen una mina han sido descubiertas, el jugador ha ganado
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] != -1 && !casillasDescubiertas[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Método que devuelve el estado actual del tablero
     * @return
     */
    public String obtenerEstadoTablero() {
        StringBuilder sb = new StringBuilder();
    
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (banderas[i][j]) {
                    sb.append("B ");
                } else if (casillasDescubiertas[i][j]) {
                    switch (tablero[i][j]) {
                        case 0:
                            sb.append(tablero[i][j] + " ");
                            break;
                        case 1:
                            sb.append("\033[0;34m" + tablero[i][j] + "\033[0m ");  // Azul (1 mina vecina)
                            break;
                        case 2:
                            sb.append("\033[0;32m" + tablero[i][j] + "\033[0m ");  // Verde (2 minas vecinas)
                            break;
                        default:
                            sb.append("\033[0;31m" + tablero[i][j] + "\033[0m ");  // Rojo (más de 2 minas vecinas)
                            break;
                    }
                } else {
                    sb.append("\033[0;33mX\033[0m ");  // Amarillo (casilla no descubierta)
                }
            }
            sb.append("\n");
        }
    
        return sb.toString();
    }

    /**
     * Método que comprueba si el juego ha terminado
     * @return
     */
    public boolean juegoTerminado() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == 'M' && casillasDescubiertas[i][j]) {
                    return true;
                }
                if (tablero[i][j] != 'M' && !casillasDescubiertas[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
