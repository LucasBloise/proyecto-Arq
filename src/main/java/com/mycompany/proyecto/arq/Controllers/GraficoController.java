package com.mycompany.proyecto.arq.Controllers;

import com.mycompany.proyecto.arq.Data.Tiempo;

public class GraficoController {
    public static String[][] grafico = new String[7][1000];

    public static void imprimirTabla() {

        String posicion;
        for (int i = 0; i < Tiempo.tiempo; i++)
            System.out.print("------");
        System.out.println();
        for (int i = 0; i < 7; i++) {

            for (int j = 0; j < Tiempo.tiempo; j++) {
                posicion = grafico[i][j];
                if (i == 0 && j == 0)
                    System.out.print("| Listo     |");
                if (i == 1 && j == 0)
                    System.out.print("| Bloqueado |");
                if (i == 2 && j == 0)
                    System.out.print("| P1        |");
                if (i == 3 && j == 0)
                    System.out.print("| P2        |");
                if (i == 4 && j == 0)
                    System.out.print("| P3        |");
                if (i == 5 && j == 0)
                    System.out.print("| SO        |");
                if (i == 6 && j == 0)
                    System.out.print("| Tiempo    |");

                if (posicion == null) {
                    posicion = "   ";
                }
                if (i == 6) {
                    posicion = "" + j;
                    System.out.print("");
                }
                while (posicion.length() < 3) {
                    posicion += " ";
                }
                System.out.print(posicion + " |");

            }
            System.out.println();

            for (int setState = 0; setState < Tiempo.tiempo; setState++)
                System.out.print("------");
            System.out.println();

        }
        GraficoController.grafico = new String[7][1000];
    }
}
