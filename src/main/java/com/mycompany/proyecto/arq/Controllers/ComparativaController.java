package com.mycompany.proyecto.arq.Controllers;

public class ComparativaController {
    public static boolean imprimirGrafica = true;
    public static int tiempoJSF = 0;
    public static int tiempoJSFD = 0;

    public static void mostrarComparativa() {
        RutinasController.ejecutarProcesos(false);
        System.out.println("Tiempo con JSF: " + (tiempoJSF - 1));
        limpiarMemoria();
        RutinasController.ejecutarProcesos(true);
        System.out.println("Tiempo con JSFD: " + (tiempoJSFD - 1));
    }

    public static void limpiarMemoria() {
        ProcesoController.procesosPorEjecutar.clear();
        GraficoController.grafico = new String[7][1000];
        Tiempo.tiempo = 0;
    }
}
