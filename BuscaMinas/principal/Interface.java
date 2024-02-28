package principal;

import BuscaMinas.BuscaMinas;
import java.util.Scanner;

public class Interface {
    public static void main(String[] args) {
        BuscaMinas juego = new BuscaMinas(10, 10, 15);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Estado actual del tablero:");
            
            //Este es el sistema de coordenadas del tablero
            String tablero = juego.obtenerTablero();
            String[] filas = tablero.split("\n");
            System.out.print("\033[35m  ");
            for (int i = 0; i < filas[0].split(" ").length; i++) {
                System.out.print(i + " ");
            }
            System.out.println();

            //Aquí se imprime el tablero
            for (int i = 0; i < filas.length; i++) {
                System.out.println("\033[35m" + i + "\033[0m " + filas[i]);
            }

            //Aquí se pide al usuario que introduzca las coordenadas de la casilla que quiere descubrir o marcar
            System.out.println("¿Quieres descubrir una casilla o marcar una bandera? descubrir(1)/ [marcar/desmarcar](2):");
            String accion = scanner.nextLine().trim().toLowerCase();

            //Aquí se pide al usuario que introduzca las coordenadas de la casilla que quiere descubrir o marcar
            System.out.println("Introduce las coordenadas de la casilla (fila columna):");
            int fila = scanner.nextInt();
            int columna = scanner.nextInt();
            scanner.nextLine();

            //Se ejecuta la acción que el usuario ha introducido
            if (accion.equals("1")) {
                boolean resultado = juego.descubrirCasilla(fila, columna);
                if (resultado == false) {
                    System.out.println("Esta casilla ha sido marcada con una bandera. No puedes descubrirla.");
                }else if (resultado) {
                    System.out.println("¡Boom! Has encontrado una mina.");
                    break;
                }
            } else if (accion.equals("2")) {
                juego.marcarBanderas(fila, columna);
            } else {
                System.out.println("Acción no reconocida. Por favor, introduce '1' o '2'.");
                continue;
            }

            if (juego.hasGanado()) {
                System.out.println("¡Felicidades! Has desbloqueado todo el tablero. Fin del juego.");
                break;
            }
        }

        scanner.close();
    }
}